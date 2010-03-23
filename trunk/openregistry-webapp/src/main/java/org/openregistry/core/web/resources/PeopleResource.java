/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.web.resources;

import org.openregistry.core.domain.*;
import org.openregistry.core.web.resources.representations.ErrorsResponseRepresentation;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.ObjectFactory;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.openregistry.core.web.resources.representations.PersonResponseRepresentation;
import org.openregistry.core.repository.ReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.Resource;
import java.util.*;

import com.sun.jersey.api.NotFoundException;

/**
 * Root RESTful resource representing <i>canonical</i> view of people in Open Registry.
 * This component is managed and autowired by Spring by means of context-component-scan,
 * and served by Jersey when URI is matched against the @Path definition. This bean is a singleton,
 * and therefore is thread-safe.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Named
@Singleton
@Path("/people")
public final class PeopleResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonService personService;

    private final ReferenceRepository referenceRepository;

    @Resource(name = "reconciliationCriteriaFactory")
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaObjectFactory;

    @Inject
    public PeopleResource(final PersonService personService, final ReferenceRepository referenceRepository) {
        this.personService = personService;
        this.referenceRepository = referenceRepository;
    }

    @GET
    @Path("{personIdType}/{personId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //auto content negotiation!
    public PersonResponseRepresentation showPerson(@PathParam("personId") String personId,
                                                   @PathParam("personIdType") String personIdType) {

        final Person person = findPersonOrThrowNotFoundException(personIdType, personId);
        logger.info("Person is found. Building a suitable representation...");
        return new PersonResponseRepresentation(buildPersonIdentifierRepresentations(person.getIdentifiers()));
    }

    @POST
    @Path("{personIdType}/{personId}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response linkSorPersonWithCalculatedPerson(@PathParam("personId") String personId,
                                                      @PathParam("personIdType") String personIdType,
                                                      PersonRequestRepresentation personRequestRepresentation) {

        final Person person = findPersonOrThrowNotFoundException(personIdType, personId);
        final ReconciliationCriteria reconciliationCriteria = PeopleResourceUtils.buildReconciliationCriteriaFrom(personRequestRepresentation,
                this.reconciliationCriteriaObjectFactory, this.referenceRepository);
        logger.info("Trying to link incoming SOR person with calculated person...");
        try {
            this.personService.addPersonAndLink(reconciliationCriteria, person);
        }
        catch (IllegalStateException ex) {
            return Response.status(409).entity(new ErrorsResponseRepresentation(Arrays.asList(ex.getMessage()))).type(MediaType.APPLICATION_XML).build();
        }
        //HTTP 204
        return null;
    }


    private List<PersonResponseRepresentation.PersonIdentifierRepresentation> buildPersonIdentifierRepresentations(final Set<? extends Identifier> identifiers) {

        final List<PersonResponseRepresentation.PersonIdentifierRepresentation> idsRep = new ArrayList<PersonResponseRepresentation.PersonIdentifierRepresentation>();

        for (final Identifier id : identifiers) {
            idsRep.add(new PersonResponseRepresentation.PersonIdentifierRepresentation(id.getType().getName(), id.getValue()));
        }

        if (idsRep.isEmpty()) {
            throw new IllegalStateException("Person identifiers cannot be empty");
        }
        return idsRep;
    }


    private Person findPersonOrThrowNotFoundException(final String personIdType, final String personId) {
        logger.info(String.format("Searching for a person with  {personIdType:%s, personId:%s} ...", personIdType, personId));
        final Person person = this.personService.findPersonByIdentifier(personIdType, personId);
        if (person == null) {
            //HTTP 404
            logger.info("Person is not found.");
            throw new NotFoundException(
                    String.format("The person resource identified by /people/%s/%s URI does not exist",
                            personIdType, personId));
        }
        return person;
    }
}

