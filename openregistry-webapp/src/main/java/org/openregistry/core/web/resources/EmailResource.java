package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.EmailService;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.utils.ValidationUtils;
import org.openregistry.core.web.resources.representations.ErrorsResponseRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * RESTful <i>controller</i> resource used to expose functionality of updating or adding, or retrieving emails
 * of SorPersons matching the specific
 * implicit criteria i.e. an SorPerson with at least one SorRole that contain affiliation type matching
 * the provided affiliation type.
 *
 * @since 1.0
 */
@Named
@Singleton
@Path("/email")
public class EmailResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonService personService;

    private final ReferenceRepository referenceRepository;

    private final EmailService emailService;

    @Inject
    public EmailResource(EmailService emailService,
                         PersonService personService,
                         ReferenceRepository referenceRepository) {

        this.personService = personService;
        this.referenceRepository = referenceRepository;
        this.emailService = emailService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addOrUpdateEmail(String emailAddress,
                                     @QueryParam("sor") String sor,
                                     @QueryParam("emailType") String emailType,
                                     @QueryParam("identifierType") String identifierType,
                                     @QueryParam("identifier") String identifier,
                                     @QueryParam("affiliation") String affiliation) {

        if (emailType == null || identifierType == null || identifier == null || affiliation == null || sor == null) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList
                            ("The request URI is malformed. Please see the documentation and construct the correct request URI.")))
                    .type(MediaType.APPLICATION_XML).build();
        }
        Type emailAddressType = this.referenceRepository.findValidType(Type.DataTypes.ADDRESS, emailType);
        if (emailAddressType == null) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided email type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
        }
        Type affiliationType = this.referenceRepository.findValidType(Type.DataTypes.AFFILIATION, affiliation);
        if (affiliationType == null) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided affiliation type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
        }

        SorPerson sorPerson = this.personService.findByIdentifierAndSource(identifierType, identifier, sor);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException("The person cannot be found in the registry or is not attached to the provided SOR");
        }
        ServiceExecutionResult<SorPerson> result =
                this.emailService.saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation(sorPerson, emailAddress, emailAddressType,
                        affiliationType);

        if (!result.succeeded()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new ErrorsResponseRepresentation(ValidationUtils.buildValidationErrorsResponseAsList(result.getValidationErrors())))
                    .type(MediaType.APPLICATION_XML).build();
        }
        if (result.getTargetObject() == null) {
            //HTTP 409
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList("The provided email could not be processed due to internal state conflict for this person")))
                    .type(MediaType.APPLICATION_XML).build();
        }

        //HTTP 200
        return Response.ok("The provided email has been processed successfully.").build();
    }
}
