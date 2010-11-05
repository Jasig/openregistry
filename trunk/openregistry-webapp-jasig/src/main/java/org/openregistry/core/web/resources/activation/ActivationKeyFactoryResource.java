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

package org.openregistry.core.web.resources.activation;

import com.sun.jersey.api.NotFoundException;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.service.ActivationService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * RESTful resource acting as a factory for activation keys for people in Open Registry
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Named("activationkeyFactoryResource")
@Singleton
@Path("/people/{personIdType}/{personId}/activation")
public final class ActivationKeyFactoryResource {

    private final ActivationService activationService;

    //Jersey specific injection
    @Context
    UriInfo uriInfo;

    @Inject
    public ActivationKeyFactoryResource(final ActivationService activationService) {
        this.activationService = activationService;
    }

    @POST
    public Response generateNewActivationKey(@PathParam("personIdType") final String personIdType, @PathParam("personId") final String personId) {
        try {
            String activationKey = this.activationService.generateActivationKey(personIdType, personId).asString();
            //HTTP 201
            return Response.created(buildActivationProcessorResourceUri(activationKey)).build();
        } catch (final PersonNotFoundException ex) {
            throw new NotFoundException(String.format("The person resource identified by /people/%s/%s URI does not exist", personIdType, personId));
        }
    }

    private URI buildActivationProcessorResourceUri(final String activationKey) {
        return this.uriInfo.getAbsolutePathBuilder().path(activationKey).build();
    }
}
