package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response findEmail(@QueryParam("sor") String sor,
                              @QueryParam("emailType") String emailType,
                              @QueryParam("identifierType") String identifierType,
                              @QueryParam("identifier") String identifier,
                              @QueryParam("affiliation") String affiliation) {

        LocalData localData = extractProcessingDataFrom(emailType, identifierType, identifier, affiliation, sor);
        if (localData.response != null) {
            return localData.response;
        }
        String emailAddress = this.emailService.findEmailForSorPersonWithRoleIdentifiedByAffiliation(localData.sorPerson,
                localData.emailAddressType, localData.affiliationType);
        if (emailAddress == null) {
            //HTTP 204
            return Response.noContent().build();
        }
        //HTTP 200
        return Response.ok(emailAddress).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addOrUpdateEmail(String emailAddress,
                                     @QueryParam("sor") String sor,
                                     @QueryParam("emailType") String emailType,
                                     @QueryParam("identifierType") String identifierType,
                                     @QueryParam("identifier") String identifier,
                                     @QueryParam("affiliation") String affiliation) {

        LocalData localData = extractProcessingDataFrom(emailType, identifierType, identifier, affiliation, sor);
        if (localData.response != null) {
            return localData.response;
        }

        ServiceExecutionResult<SorPerson> result =
                this.emailService.saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation(localData.sorPerson, emailAddress,
                        localData.emailAddressType,
                        localData.affiliationType);

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


    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/SoRs")
    public Response addOrUpdateEmailForAllSoRs(
                                     @FormParam ("emailAddress") String emailAddress,
                                     @QueryParam("emailType") String emailType,
                                     @QueryParam("identifierType") String identifierType,
                                     @QueryParam("identifier") String identifier
    ) {

        LocalData2 localData = extractProcessingDataForAllEmails(emailType,identifierType, identifier);
        if (localData.response != null) {
            return localData.response;
        }

        for(SorPerson sorPerson:localData.sorPeople){
            ServiceExecutionResult<SorPerson> result = this.emailService.saveOrCreateEmailForSorPersonForAllRoles(sorPerson,
                    emailAddress,
                    localData.emailAddressType);

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
        }

        //HTTP 200
        return Response.ok("The provided email has been processed successfully.").build();
    }

    private Response validateRequiredRequestQueryParams(String emailType, String identifierType, String identifier, String affiliation,
                                                        String sor) {
        if (emailType == null || identifierType == null || identifier == null || affiliation == null || sor == null) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList
                            ("The request URI is malformed. Please see the documentation and construct the correct request URI.")))
                    .type(MediaType.APPLICATION_XML).build();
        }
        return null;
    }

    private LocalData extractProcessingDataFrom(String emailType, String identifierType, String identifier, String affiliation,
                                                String sor) {

        LocalData d = new LocalData();
        Response r = validateRequiredRequestQueryParams(emailType, identifierType, identifier, affiliation, sor);
        if (r != null) {
            d.response = r;
            return d;
        }

        Type validatedType = this.referenceRepository.findValidType(Type.DataTypes.ADDRESS, emailType);
        if (validatedType == null) {
            //HTTP 400
            d.response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided email type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
            return d;
        }
        d.emailAddressType = validatedType;

        validatedType = this.referenceRepository.findValidType(Type.DataTypes.AFFILIATION, affiliation);
        if (validatedType == null) {
            //HTTP 400
            d.response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided affiliation type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
            return d;
        }
        d.affiliationType = validatedType;

        SorPerson sorPerson = this.personService.findByIdentifierAndSource(identifierType, identifier, sor);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException("The person cannot be found in the registry or is not attached to the provided SOR");
        }
        d.sorPerson = sorPerson;
        return d;
    }

    private Response validateRequiredRequestQueryParams(String emailType, String identifierType, String identifier) {
        if (emailType == null || identifierType == null || identifier == null ) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList
                            ("The request URI is malformed. Please see the documentation and construct the correct request URI.")))
                    .type(MediaType.APPLICATION_XML).build();
        }
        return null;
    }

    private LocalData2 extractProcessingDataForAllEmails(String emailType,String identifierType, String identifier) {

        LocalData2 d = new LocalData2();
        Response r = validateRequiredRequestQueryParams(emailType, identifierType, identifier);
        if (r != null) {
            d.response = r;
            return d;
        }

        Type validatedType = this.referenceRepository.findValidType(Type.DataTypes.ADDRESS, emailType);
        if (validatedType == null) {
            //HTTP 400
            d.response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided email type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
            return d;
        }
        d.emailAddressType = validatedType;

        //No SoR for this request
        //SorPerson sorPerson = this.personService.findByIdentifierAndSource(identifierType, identifier, null);
        List<SorPerson> sorPeople = this.personService.findByIdentifier(identifierType, identifier);
        if (sorPeople == null) {
            //HTTP 404
            throw new NotFoundException("The person cannot be found in the registry or is not attached to the provided SOR");
        }
        d.sorPeople = sorPeople;
        return d;
    }

    //Structure that holds common data to be reused in different HTTP action methods
    //Since Java doesn't have Tuples (yet), this is a good convention for local re-use

    private static class LocalData {
        Response response;
        Type emailAddressType;
        Type affiliationType;
        SorPerson sorPerson;
    }

    private static class LocalData2 {
        Response response;
        Type emailAddressType;
        List<SorPerson> sorPeople;
    }
}
