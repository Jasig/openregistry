package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.representation.Form;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.web.resources.representations.LinkRepresentation;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * Root RESTful resource representing <i>system of record</i> view of people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @since 1.0
 */
@Named
@Singleton
@Path("/sor/{sorSourceId}/people")
public final class SystemOfRecordPeopleResource {

    private static final String FORCE_ADD_FLAG = "y";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    private final PersonService personService;

    private final ReferenceRepository referenceRepository;

    @Resource(name = "reconciliationCriteriaFactory")
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaObjectFactory;

    //JSR-250 injection which is more appropriate here for 'autowiring by name' in the case of multiple types
    //are defined in the app ctx (Strings). The looked up bean name defaults to the property name which
    //needs an injection.
    @Resource
    private String preferredPersonIdentifierType;

    @Inject
    public SystemOfRecordPeopleResource(final PersonService personService, final ReferenceRepository referenceRepository) {
        this.personService = personService;
        this.referenceRepository = referenceRepository;
    }


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingPerson(PersonRequestRepresentation personRequestRepresentation,
                                          @PathParam("sorSourceId") String sorSourceId,
                                          @QueryParam("force") String forceAdd) {

        personRequestRepresentation.systemOfRecordId = sorSourceId;
        Response response = PeopleResourceUtils.validate(personRequestRepresentation);
        if (response != null) {
            return response;
        }
        final ReconciliationCriteria reconciliationCriteria = PeopleResourceUtils.buildReconciliationCriteriaFrom(personRequestRepresentation,
                this.reconciliationCriteriaObjectFactory, this.referenceRepository);
        logger.info("Trying to add incoming person...");

        if (FORCE_ADD_FLAG.equals(forceAdd)) {
            logger.warn("Multiple people found, but doing a 'force add'");
            try {
                final ServiceExecutionResult<Person> result = this.personService.forceAddPerson(reconciliationCriteria);
                final Person forcefullyAddedPerson = result.getTargetObject();
                final URI uri = buildPersonResourceUri(forcefullyAddedPerson);
                response = Response.created(uri).entity(buildPersonActivationKeyRepresentation(forcefullyAddedPerson)).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).build();
                logger.info(String.format("Person successfully created (with 'force add' option). The person resource URI is %s", uri.toString()));
            } catch (final IllegalStateException e) {
                response = Response.status(409).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
            }
            return response;
        }

        try {
            final ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);

            if (!result.succeeded()) {
                logger.info("The incoming person payload did not pass validation. Validation errors: " + result.getValidationErrors());
                return Response.status(Response.Status.BAD_REQUEST).entity("The incoming request is malformed.").build();
            }

            final Person person = result.getTargetObject();
            final URI uri = buildPersonResourceUri(person);
            response = Response.created(uri).entity(buildPersonActivationKeyRepresentation(person)).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).build();
            logger.info(String.format("Person successfully created. The person resource URI is %s", uri.toString()));
        }
        catch (final ReconciliationException ex) {
            switch (ex.getReconciliationType()) {
                case MAYBE:
                    final List<PersonMatch> conflictingPeopleFound = ex.getMatches();
                    response = Response.status(409).entity(buildLinksToConflictingPeopleFound(conflictingPeopleFound)).type(MediaType.APPLICATION_XHTML_XML).build();
                    logger.info("Multiple people found: " + response.getEntity());
                    break;

                case EXACT:
                    final URI uri = buildPersonResourceUri(ex.getMatches().get(0).getPerson());
                    //HTTP 303 ("See other with GET")
                    response = Response.seeOther(uri).build();
                    logger.info(String.format("Person already exists. The existing person resource URI is %s.", uri.toString()));
                    break;
            }
        } catch (final IllegalStateException e) {
                response = Response.status(409).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return response;
    }

    @DELETE
    @Path("{sorPersonId}")
    public Response deletePerson(@PathParam("sorSourceId") final String sorSourceId,
                                 @PathParam("sorPersonId") final String sorPersonId,
                                 @QueryParam("mistake") @DefaultValue("false") final boolean mistake,
                                 @QueryParam("terminationType") @DefaultValue("UNSPECIFIED") final String terminationType) {
        try {
            if (!this.personService.deleteSystemOfRecordPerson(sorSourceId, sorPersonId, mistake, terminationType)) {
                throw new WebApplicationException(
                        new RuntimeException(String.format("Unable to Delete SorPerson for SoR [ %s ] with ID [ %s ]", sorSourceId, sorPersonId)), 500);
            }
            //HTTP 204
            logger.debug("The SOR Person resource has been successfully DELETEd");
            return null;
        }
        catch (final PersonNotFoundException e) {
            throw new NotFoundException(String.format("The system of record person resource identified by /sor/%s/people/%s URI does not exist",
                    sorSourceId, sorPersonId));
        }
    }

    private URI buildPersonResourceUri(final Person person) {
        for (final Identifier id : person.getIdentifiers()) {
            if (this.preferredPersonIdentifierType.equals(id.getType().getName())) {
                return this.uriInfo.getAbsolutePathBuilder().path(this.preferredPersonIdentifierType)
                        .path(id.getValue()).build();
            }
        }
        //Person MUST have at least one id of the preferred configured type. Results in HTTP 500
        throw new IllegalStateException("The person must have at least one id of the preferred configured type " +
                "which is <" + this.preferredPersonIdentifierType + ">");
    }

    private LinkRepresentation buildLinksToConflictingPeopleFound(List<PersonMatch> matches) {
        //A little defensive stuff. Will result in HTTP 500
        if (matches.isEmpty()) {
            throw new IllegalStateException("Person matches cannot be empty if reconciliation result is <MAYBE>");
        }
        final List<LinkRepresentation.Link> links = new ArrayList<LinkRepresentation.Link>();
        for (final PersonMatch match : matches) {
            links.add(new LinkRepresentation.Link("person", buildPersonResourceUri(match.getPerson()).toString()));
        }
        return new LinkRepresentation(links);
    }

    //Content-Type: application/x-www-form-urlencoded
    private Form buildPersonActivationKeyRepresentation(final Person person) {
        final Form f = new Form();
        f.putSingle("activationKey", person.getCurrentActivationKey().asString());
        return f;
    }
}
