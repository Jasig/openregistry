package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.service.ActivationService;
import org.openregistry.core.domain.PersonNotFoundException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.NotFoundException;

/**
 * RESTful resource acting as a factory for activation keys for people in Open Registry
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Scope("singleton")
@Path("/people/{personIdType}/{personId}/activation")
public final class ActivationKeyFactoryResource {

    @Autowired
    private ActivationService activationService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generateNewActivationKey(@PathParam("personIdType") String personIdType,
                                           @PathParam("personId") String personId) {

        try {
            return this.activationService.generateActivationKey(personIdType, personId).getId();
        }
        catch (PersonNotFoundException ex) {
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }
    }
}
