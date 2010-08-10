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

    public Response findEmail(@QueryParam("sor") String sor,
                              @QueryParam("emailType") String emailType,
                              @QueryParam("identifierType") String identifierType,
                              @QueryParam("identifier") String identifier,
                              @QueryParam("affiliation") String affiliation) {

        //TODO: IMPLEMENT ME!
        return null;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addOrUpdateEmail(String emailAddress,
                                     @QueryParam("sor") String sor,
                                     @QueryParam("emailType") String emailType,
                                     @QueryParam("identifierType") String identifierType,
                                     @QueryParam("identifier") String identifier,
                                     @QueryParam("affiliation") String affiliation) {

        Data data = getProcessingData(emailType,identifierType,identifier, affiliation, sor);
        if(data.response != null) {
            return data.response;
        }

        ServiceExecutionResult<SorPerson> result =
                this.emailService.saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation(data.sorPerson, emailAddress,
                        data.emailAddressType,
                        data.affiliationType);

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

    private Object[] validateType(Type.DataTypes type, String typeValue, String errorMessage) {
        Object[] retVal = new Object[2];
        Type validatedType = this.referenceRepository.findValidType(type, typeValue);
        if (validatedType == null) {
            //HTTP 400        
            retVal[1] = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList("The provided affiliation type is invalid.")))
                    .type(MediaType.APPLICATION_XML).build();
            return retVal;
        }
        retVal[0] = validatedType;
        return retVal;
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

    private Data getProcessingData(String emailType, String identifierType, String identifier, String affiliation,
                                                        String sor) {

        Data d = new Data();
        Response r = validateRequiredRequestQueryParams(emailType,identifierType,identifier, affiliation, sor);
        if(r != null) {
            d.response = r;
            return d;
        }

        Object[] tvr = validateType(Type.DataTypes.ADDRESS, emailType, "The provided email type is invalid");
        if (tvr[1] != null) {
            d.response = (Response) tvr[1];
            return d;
        }
        d.emailAddressType = (Type)tvr[0];
        tvr = validateType(Type.DataTypes.AFFILIATION, affiliation, "The provided affiliation type is invalid");
        if (tvr[1] != null) {
            d.response = (Response) tvr[1];
            return d;
        }
        d.affiliationType = (Type)tvr[0];

        SorPerson sorPerson = this.personService.findByIdentifierAndSource(identifierType, identifier, sor);
        if (sorPerson == null) {
            //HTTP 404
            throw new NotFoundException("The person cannot be found in the registry or is not attached to the provided SOR");
        }
        d.sorPerson = sorPerson;
        return d;
    }

    private static class Data {
        Response response;
        Type emailAddressType;
        Type affiliationType;
        SorPerson sorPerson;
    }
}
