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

import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.web.context.ContextLoaderListener;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;

/**
 * @since 1.0
 */
public class PostNewPersonToPeopleResourceTests extends JerseyTestSupport {

    private static final String RESOURCE_UNDER_TEST_URI = "/people";

    public PostNewPersonToPeopleResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPOSTPersonViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void incorrectIncomingMediaType() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(415, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, "incorrect incoming data");
    }

    @Test
    public void missingRequiredData() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, new PersonRequestRepresentation());
    }

    @Test
    public void addingNonExistentPerson() {
        assertNotNull(assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(201, RESOURCE_UNDER_TEST_URI, POST_HTTP_METHOD,
                PersonRequestRepresentation.forNewPerson()).getHeaders().getFirst("Location"));
    }

    @Test
    public void addingExistingPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(303, RESOURCE_UNDER_TEST_URI, POST_HTTP_METHOD,
                PersonRequestRepresentation.forExistingPerson());
    }

    @Test
    public void addingPersonWithValidationErrors() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(400, RESOURCE_UNDER_TEST_URI, POST_HTTP_METHOD,
                PersonRequestRepresentation.withValidationErrors());
    }

    @Test
    public void forceAddingPersonWithMultiplePeopleFound() {
        assertNotNull(assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntityWithQueryParam(201, RESOURCE_UNDER_TEST_URI, POST_HTTP_METHOD,
                PersonRequestRepresentation.forMultiplePeople(), "force", "y").getHeaders().getFirst("Location"));
    }

    @Test
    public void addingPersonWithMultiplePeopleFound() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(409, RESOURCE_UNDER_TEST_URI, POST_HTTP_METHOD,
                PersonRequestRepresentation.forMultiplePeople());
    }
}
