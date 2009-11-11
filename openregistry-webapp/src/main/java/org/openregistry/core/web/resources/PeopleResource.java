/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.web.resources;

import org.openregistry.core.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.ObjectFactory;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.IdentifierChangeService;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.web.resources.representations.LinkRepresentation;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.openregistry.core.web.resources.representations.PersonResponseRepresentation;
import org.openregistry.core.web.resources.representations.RoleRepresentation;
import org.openregistry.core.repository.ReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.representation.Form;
import org.springframework.util.Assert;

/**
 * Root RESTful resource representing <i>canonical</i> view of people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Scope("singleton")
@Path("/people")
public final class PeopleResource {

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    @Autowired(required = true)
    private PersonService personService;

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    @Resource(name = "reconciliationCriteriaFactory")
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaObjectFactory;

	//JSR-250 injection which is more appropriate here for 'autowiring by name' in the case of multiple types
    //are defined in the app ctx (Strings). The looked up bean name defaults to the property name which
    //needs an injection.
    @Resource
    private String preferredPersonIdentifierType;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FORCE_ADD_FLAG = "y";

    @GET
    @Path("{personIdType}/{personId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //auto content negotiation!
    public PersonResponseRepresentation showPerson(@PathParam("personId") String personId,
                                                   @PathParam("personIdType") String personIdType) {

        final Person person = findPersonOrThrowNotFoundException(personIdType, personId);
        logger.info("Person is found. Building a suitable representation...");
        return new PersonResponseRepresentation(buildPersonIdentifierRepresentations(person.getIdentifiers()));
    }

    @POST
    @Path("{personIdType}/{personId}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response linkSorPersonWithCalculatedPerson(@PathParam("personId") String personId,
                                                      @PathParam("personIdType") String personIdType,
                                                      PersonRequestRepresentation personRequestRepresentation) {

        Response response = validate(personRequestRepresentation);
        if (response != null) {
            return response;
        }
        final Person person = findPersonOrThrowNotFoundException(personIdType, personId);
        final ReconciliationCriteria reconciliationCriteria = buildReconciliationCriteriaFrom(personRequestRepresentation);
        logger.info("Trying to link incoming SOR person with calculated person...");
        try {
            this.personService.addPersonAndLink(reconciliationCriteria, person);
        }
        catch (IllegalStateException ex) {
            return Response.status(409).entity(ex.getMessage()).build();
        }
        //HTTP 204
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response processIncomingPerson(PersonRequestRepresentation personRequestRepresentation, @QueryParam("force") String forceAdd) {
        Response response = validate(personRequestRepresentation);
        if (response != null) {
            return response;
        }
        final ReconciliationCriteria reconciliationCriteria = buildReconciliationCriteriaFrom(personRequestRepresentation);
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
        }
        return response;
    }

    private ReconciliationCriteria buildReconciliationCriteriaFrom(final PersonRequestRepresentation request) {
        final ReconciliationCriteria ps = this.reconciliationCriteriaObjectFactory.getObject();
        ps.getSorPerson().setSourceSor(request.systemOfRecordId);
        ps.getSorPerson().setSorId(request.systemOfRecordPersonId);
        final Name name = ps.getSorPerson().addName(referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setGiven(request.firstName);
        name.setFamily(request.lastName);
        ps.setEmailAddress(request.email);
        ps.setPhoneNumber(request.phoneNumber);
        ps.getSorPerson().setDateOfBirth(request.dateOfBirth);
        ps.getSorPerson().setSsn(request.ssn);
        ps.getSorPerson().setGender(request.gender);
        ps.setAddressLine1(request.addressLine1);
        ps.setAddressLine2(request.addressLine2);
        ps.setCity(request.city);
        ps.setRegion(request.region);
        ps.setPostalCode(request.postalCode);

        for (final PersonRequestRepresentation.Identifier identifier : request.identifiers) {
            final IdentifierType identifierType = this.referenceRepository.findIdentifierType(identifier.identifierType);
            Assert.notNull(identifierType);

            ps.getIdentifiersByType().put(identifierType, identifier.identifierValue);
        }
        return ps;
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

    private List<PersonResponseRepresentation.PersonIdentifierRepresentation> buildPersonIdentifierRepresentations(final Set<Identifier> identifiers) {

        final List<PersonResponseRepresentation.PersonIdentifierRepresentation> idsRep = new ArrayList<PersonResponseRepresentation.PersonIdentifierRepresentation>();

        for (final Identifier id : identifiers) {
            idsRep.add(new PersonResponseRepresentation.PersonIdentifierRepresentation(id.getType().getName(), id.getValue()));
        }

        if (idsRep.isEmpty()) {
            throw new IllegalStateException("Person identifiers cannot be empty");
        }
        return idsRep;
    }

    //Content-Type: application/x-www-form-urlencoded
    private Form buildPersonActivationKeyRepresentation(final Person person) {
        final Form f = new Form();
        f.putSingle("activationKey", person.getCurrentActivationKey().asString());
        return f;
    }

    private Response validate(PersonRequestRepresentation personRequestRepresentation) {
        if (!personRequestRepresentation.checkRequiredData()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).entity("The person entity payload is incomplete.").build();
        }
        //Returns null response indicating that the representation is valid
        return null;
    }



    private Person findPersonOrThrowNotFoundException(final String personIdType, final String personId) {
        logger.info(String.format("Searching for a person with  {personIdType:%s, personId:%s} ...", personIdType, personId));
        final Person person = this.personService.findPersonByIdentifier(personIdType, personId);
        if (person == null) {
            //HTTP 404
            logger.info("Person is not found.");
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }
        return person;
    }    
}

