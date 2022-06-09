package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.EmailService;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.utils.ValidationUtils;
import org.openregistry.core.web.resources.representations.ErrorsResponseRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
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
@Path("/preferredname")
public class PreferredNameResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonService personService;

    @Inject
    public PreferredNameResource(EmailService emailService,
                         PersonService personService,
                         ReferenceRepository referenceRepository) {

        this.personService = personService;
    }

    //@POST
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addOrUpdateEmail(String emailAddress,
                                     @QueryParam("rcpid") String rcpid,
                                     @QueryParam("sor") String sor,
                                     @QueryParam("firstname") String firstname,
                                     @QueryParam("middlename") String middlename,
                                     @QueryParam("lastname") String lastname) {

        boolean result = false;

        if (rcpid == null || sor == null || firstname == null) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorsResponseRepresentation(Arrays.asList
                            ("The request URI is malformed. Missing one or more required parameters: rcpid, sor and firstname")))
                    .type(MediaType.APPLICATION_XML).build();
        }

        // validate input names
        final String name_regexp = "^[a-zA-Z][a-zA-Z0-9-'. ]*$";
        if (!StringUtils.isBlank(firstname)) {
            if (!firstname.matches(name_regexp)) {
                //HTTP 400
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorsResponseRepresentation(Arrays.asList
                                ("First name must start with a letter and cannot contain special character")))
                        .type(MediaType.APPLICATION_XML).build();
            }
        }
        if (!StringUtils.isBlank(middlename)) {
            if (!middlename.matches(name_regexp)) {
                //HTTP 400
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorsResponseRepresentation(Arrays.asList
                                ("Middle name must start with a letter and cannot contain special character")))
                        .type(MediaType.APPLICATION_XML).build();
            }
        }
        if (!StringUtils.isBlank(lastname)) {
            if (!lastname.matches(name_regexp)) {
                //HTTP 400
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorsResponseRepresentation(Arrays.asList
                                ("Last name must start with a letter and cannot contain special character")))
                        .type(MediaType.APPLICATION_XML).build();
            }
        }

        Person person = this.personService.findPersonByIdentifier("RCPID", rcpid);

        if (person == null) {
            //HTTP 400
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorsResponseRepresentation(Arrays.asList
                    ("No person found with the given identifier"))).type(MediaType.APPLICATION_XML).build();
        }

        SorPerson sorPerson = this.personService.findByIdentifierAndSource("RCPID", rcpid, sor);
        if (sorPerson == null) {
            //HTTP 404
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorsResponseRepresentation(Arrays.asList
                    ("No SOR person found with the given identifier and sor type"))).type(MediaType.APPLICATION_XML).build();
        }

        result = this.personService.addOrUpdatePreferredName(person, sorPerson, firstname, middlename, lastname);

        if (!result) {
            //HTTP 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorsResponseRepresentation(
                            Arrays.asList("Unable to add or update the preferred name to unexpected errors.")))
                    .type(MediaType.APPLICATION_XML).build();
        }

        //HTTP 200
        return Response.ok("The preferred name has been processed successfully.").build();
    }

}

