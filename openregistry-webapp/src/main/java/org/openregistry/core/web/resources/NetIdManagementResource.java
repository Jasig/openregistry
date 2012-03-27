package org.openregistry.core.web.resources;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.service.identifier.NetIdManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Named
@Singleton
@Path("/netids")
public class NetIdManagementResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private NetIdManagementService netIdManagementService;

    public void setNetIdManagementService(NetIdManagementService netIdManagementService) {
        this.netIdManagementService = netIdManagementService;
    }

    @PUT
    @Path("{currentPrimaryNetId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void changePrimaryNetId(@PathParam("currentPrimaryNetId") String currentPrimaryNetIdValue,
                                   @FormParam("newPrimaryNetId") String newPrimaryNetIdValue) {
        try{
            this.netIdManagementService.changePrimaryNetId(currentPrimaryNetIdValue, newPrimaryNetIdValue);
        }
        catch (IllegalArgumentException e) {
            //HTTP 404 - no such person with provided current netid
            throw new NotFoundException(e.getMessage());
        }
        catch(IllegalStateException e) {
            //HTTP 409 - the person with provided new netid already exists
            throw new ConflictException(e.getMessage());
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            //HTTP 500
            throw new WebApplicationException(500);
        }
        //HTTP 204 - success
    }

    @POST
    @Path("{currentPrimaryNetId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNonPrimaryNetId(@PathParam("currentPrimaryNetId") String currentPrimaryNetIdValue,
                                   @FormParam("newNonPrimaryNetId") String newNonPrimaryNetIdValue) {
        try{
            this.netIdManagementService.addNonPrimaryNetId(currentPrimaryNetIdValue, newNonPrimaryNetIdValue);
        }
        catch (IllegalArgumentException e) {
            //HTTP 404 - no such person with provided current netid OR the provided primary netid [%s] does not match the current primary netid
            throw new NotFoundException(e.getMessage());
        }
        catch(IllegalStateException e) {
            //HTTP 409 - the person with provided new netid already exists
            throw new ConflictException(e.getMessage());
        }
        //HTTP 204 - success
    }

}
