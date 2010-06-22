package org.openregistry.core.web.resources;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.EmailService;
import org.openregistry.core.service.PersonService;
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
@Path("/affiliated-email")
public class AffiliatedEmailResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonService personService;

    private final ReferenceRepository referenceRepository;

    private final EmailService emailService;

    @Inject
    public AffiliatedEmailResource(EmailService emailService,
                                   PersonService personService,
                                   ReferenceRepository referenceRepository) {

        this.personService = personService;
        this.referenceRepository = referenceRepository;
        this.emailService = emailService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addOrUpdateEmail(String emailAddress,
                                     @QueryParam("emailType") String emailType,
                                     @QueryParam("identifierType") String identifierType,
                                     @QueryParam("identifier") String identifier,
                                     @QueryParam("affiliation") String affiliation) {
        //TODO: Work in progress...
        return Response.ok(String.format("EMAIL: %s, EMAIL TYPE: %s", emailAddress, emailType)).build();
    }
}
