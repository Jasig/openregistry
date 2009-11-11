package org.openregistry.core.web.resources;

import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * /**
 * Root RESTful resource representing <i>system of record</i> view of people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @since 1.0
 */
@Component
@Scope("singleton")
@Path("/sor/{sorSourceId}/people/{sorPersonId}")
public class SystemOfRecordPeopleResource {

    @Autowired(required = true)
    private PersonService personService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DELETE
    public Response deletePerson(@PathParam("sorSourceId") final String sorSourceId,
                                 @PathParam("sorPersonId") final String sorPersonId,
                                 @QueryParam("mistake") @DefaultValue("false") final boolean mistake,
                                 @QueryParam("terminationType") @DefaultValue("UNSPECIFIED") final String terminationType) {
        try {
            if (!this.personService.deleteSystemOfRecordPerson(sorSourceId, sorPersonId, mistake, terminationType)) {
                throw new WebApplicationException(
                        new RuntimeException(String.format("Unable to Delete SorPerson for SoR [ %s ] with ID [ %s ]", sorSourceId, sorPersonId)), 500);
            }
            //HTTP 204
            logger.debug("The SOR Person resource has been successfully DELETEd");
            return null;
        }
        catch (final PersonNotFoundException e) {
            throw new NotFoundException(String.format("The system of record person resource identified by /sor/%s/people/%s URI does not exist",
                    sorSourceId, sorPersonId));
        }
    }
}
