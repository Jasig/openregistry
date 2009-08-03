package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.service.ActivationService;
import org.openregistry.core.domain.PersonNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;


import com.sun.jersey.api.NotFoundException;

import java.net.URI;

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

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    @POST
    public Response generateNewActivationKey(@PathParam("personIdType") String personIdType,
                                           @PathParam("personId") String personId) {

        try {
            String activationKey = this.activationService.generateActivationKey(personIdType, personId).getValue();
            //HTTP 201
            return Response.created(buildActivationProcessorResourceUri(activationKey)).build();
        }
        catch (PersonNotFoundException ex) {
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }
    }

    private URI buildActivationProcessorResourceUri(String activationKey) {
        return this.uriInfo.getAbsolutePathBuilder().path(activationKey).build();
    }
}
