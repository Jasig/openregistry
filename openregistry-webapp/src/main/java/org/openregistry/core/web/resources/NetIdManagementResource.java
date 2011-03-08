package org.openregistry.core.web.resources;

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
@Singleton
@Path("/netid")
public class NetIdManagementResource {

    private static final String PRIMARY_YES_FLAG = "Y";

    private static final String PRIMARY_NO_FLAG = "N";

    private NetIdManagementService netIdManagementService;

    //@Inject
    public NetIdManagementResource(NetIdManagementService netIdManagementService) {
        this.netIdManagementService = netIdManagementService;
    }

    @POST
    @Path("{currentNetId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response changeNetId(@FormParam("newNetId") String newNetIdValue,
                                @DefaultValue("true") @FormParam("primary") boolean primary) {
        return null;
    }
}
