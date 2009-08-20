/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.web.resources;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.service.ActivationService;
import org.openregistry.core.domain.PersonNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


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
    public Response generateNewActivationKey(@PathParam("personIdType") final String personIdType, @PathParam("personId") final String personId) {
        try {
            String activationKey = this.activationService.generateActivationKey(personIdType, personId).asString();
            //HTTP 201
            return Response.created(buildActivationProcessorResourceUri(activationKey)).build();
        } catch (final IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        } catch (final PersonNotFoundException ex) {
            throw new NotFoundException(String.format("The person resource identified by /people/%s/%s URI does not exist", personIdType, personId));
        }
    }

    private URI buildActivationProcessorResourceUri(String activationKey) {
        return this.uriInfo.getAbsolutePathBuilder().path(activationKey).build();
    }
}
