package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Root RESTful resource representing people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Path("/people")
@Component
@Scope("singleton")
public class PeopleResource {

    @GET
    @Path("{personId}")
    @Produces("text/plain")
    public String getItem(@PathParam("personId") String personId) {
        return "Yes, this is the representation from a Jersey-managed resource! The person ID is: " + personId;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public void postSomeData(MultivaluedMap<String, String> formParams) {
        System.out.println("GOT HTTP form POSTed: " + formParams);
    }

}
