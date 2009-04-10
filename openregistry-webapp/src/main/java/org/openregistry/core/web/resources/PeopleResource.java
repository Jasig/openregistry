package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.XmlElement;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

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

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{personIdType}/{personId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) //auto content negotiation!
    public PersonResponseRepresentation showPerson(@PathParam("personId") String personId,
                                                   @PathParam("personIdType") String personIdType) {

        //Build activation generator URI
        URI activationGeneratorUri = this.uriInfo.getAbsolutePathBuilder().path("activation").build();

        //Build activation proccess URI
        URI activationProcessorUri = this.uriInfo.getAbsolutePathBuilder().path("activation").path("proccess")
                .queryParam("activation-token", "activation-token-skjfskjfhskjdfh").build();

        return new PersonResponseRepresentation(
                activationGeneratorUri.toString(),
                activationProcessorUri.toString(),
                "AT-153254325",
                Arrays.asList(new PersonResponseRepresentation.PersonIdentifierRepresentation(personIdType, personId),
                        new PersonResponseRepresentation.PersonIdentifierRepresentation("rcpId", "12345")));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //resp-type query param is here temporarily, for testing
    public Response processIncomingPerson(@DefaultValue("201") @QueryParam("resp-type") int respType,
                                          MultivaluedMap<String, String> formParams) {

        //NOTE: The real handling of the POSTed representation will be done
        //here by means of un-marshalling the submitted form data into a Java type
        //and invoking the internal Open Registry API/APIs (TBD), and based on the outcome
        //return the appropriate response.

        //The code below is just for testing the Jersey framework
        //and to lay out the foundation for further work.        

        Response response = null;
        URI uri = this.uriInfo.getAbsolutePathBuilder().path("rcpId").path("1234567").build();
        PersonRequestRepresentation personRequest = null;
        try {
            personRequest = new PersonRequestRepresentation(formParams);
        }
        catch (IllegalArgumentException ex) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        switch (respType) {
            case 201:
                response = Response.created(uri).build();
                break;
            case 409:
                LinkRepresentation entityBody =
                        new LinkRepresentation(Arrays.asList(new LinkRepresentation.Link("person", uri.toString()),
                                                             new LinkRepresentation.Link("person", uri.toString())));

                response = Response.status(409).entity(entityBody).type(MediaType.APPLICATION_XHTML_XML).build();
                break;
            case 307:
                response = Response.temporaryRedirect(uri).build();
            default:
                break;
        }
        System.out.println("GOT HTTP form POSTed: " + formParams);
        return response;
    }
}
