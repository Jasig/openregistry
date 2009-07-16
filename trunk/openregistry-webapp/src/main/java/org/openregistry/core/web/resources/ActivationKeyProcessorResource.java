package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.service.ActivationService;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.ActivationKey;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.NotFoundException;

/**
 * RESTful resource exposing the activation key processing functions i.e. 'invalidation' and 'verification' of activation keys
 * for people in Open Registry
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Scope("singleton")
@Path("/people/{personIdType}/{personId}/activation/{activationKey}")
public final class ActivationKeyProcessorResource {

    @Autowired
    private ActivationService activationService;

    @DELETE
    public Response invalidateActivationKey(@PathParam("personIdType") String personIdType,
                                            @PathParam("personId") String personId,
                                            @PathParam("activationKey") String activationKey) {
        Response response = null;
        try {
            this.activationService.invalidateActivationKey(personIdType, personId, activationKey);
        }
        catch (IllegalArgumentException e) {
            throw new NotFoundException(
                    String.format("The activation key [%s] does not exist", activationKey));
        }
        catch (PersonNotFoundException e) {
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }         
        //If response is null, that means HTTP 204
        return response;
    }

    @GET
    public Response verifyActivationKey(@PathParam("personIdType") String personIdType,
                                          @PathParam("personId") String personId,
                                          @PathParam("activationKey") String activationKey) {
        Response response = null;
        ActivationKey ak = null;
        try {
            ak = this.activationService.getActivationKey(personIdType, personId, activationKey);
            if(ak.isNotYetValid()) {
                //HTTP 409
                response = Response.status(409).entity(String.format("The activation key [%s] is not yet valid for use", activationKey))
                    .type(MediaType.TEXT_PLAIN).build();
            }
            else if(ak.isExpired()) {
                //HTTP 410
                response = Response.status(410).entity(String.format("The activation key [%s] has expired", activationKey))
                    .type(MediaType.TEXT_PLAIN).build();
            }
        }
        catch(PersonNotFoundException e) {
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));    
        }
        catch (IllegalArgumentException e) {
            throw new NotFoundException(
                    String.format("The activation key [%s] does not exist", activationKey));
        }
        //If response is null, that means HTTP 204
        return response;
    }
}
