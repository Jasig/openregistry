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
package org.openregistry.core.web.resources.activation;

import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.context.ContextLoaderListener;
import org.openregistry.core.web.resources.JerseyTestSupport;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class ActivationKeyFactoryResourceTests extends JerseyTestSupport {

    public ActivationKeyFactoryResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources.activation")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    /**
     * Create new activation key.
     */
    @Test
    public void testActivationKeyGeneration() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/testId/activation", "POST");
        final String locationHeader = response.getHeaders().getFirst("Location");
        assertNotNull(locationHeader);
        assertEquals(201, response.getClientResponseStatus().getStatusCode());
    }

    /**
     * Person Not Found.
     */
    @Test
    public void testPersonNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/Net/testId/activation", "POST");
        final String locationHeader = response.getHeaders().getFirst("Location");
        assertNull(locationHeader);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }
}
