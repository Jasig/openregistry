package org.openregistry.core.web.resources;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.service.identifier.NetIdManagementService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful resource used to expose functionality of managing the netids of canonical registry Persons
 *
 * @since 1.0
 */
//@Named
//@Singleton
@Path("/netid")
public class NetIdManagementResource {

    private NetIdManagementService netIdManagementService;

    @Inject
    public NetIdManagementResource(NetIdManagementService netIdManagementService) {
        this.netIdManagementService = netIdManagementService;
    }

    @POST
    @Path("{currentNetId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void changeNetId(@PathParam("currentNetId") String currentNetId,
                                @FormParam("newNetId") String newNetIdValue,
                                @DefaultValue("true") @FormParam("primary") boolean primary) {
        try {
            this.netIdManagementService.changeNetId(currentNetId, newNetIdValue, primary);
        }
        catch (IllegalArgumentException e) {
            //HTTP 404 - no such person with provided current netid
            throw new NotFoundException(e.getMessage());
        }
        catch(IllegalStateException e) {
            //HTTP 409 - the person with provided new netid already exists
            throw new ConflictException(e.getMessage());
        }
        //HTTP 204 - success
    }
}
