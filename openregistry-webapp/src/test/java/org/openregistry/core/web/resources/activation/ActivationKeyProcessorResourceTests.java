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
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.web.context.ContextLoaderListener;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.openregistry.core.web.resources.JerseyTestSupport;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class ActivationKeyProcessorResourceTests extends JerseyTestSupport {

    public ActivationKeyProcessorResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources.activation")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void testVerifyPersonNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/Net/testId/activation/whatKey", "GET");
        final String locationHeader = response.getHeaders().getFirst("Location");
        assertNull(locationHeader);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyKeyNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/whatKey", "GET");
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyEverythingOkay() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        assertEquals(204, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testDoubleVerifyWithGoodLockOnSecondAttempt() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        assertEquals(204, response.getClientResponseStatus().getStatusCode());

        final ClientResponse response2 = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        assertEquals(204, response2.getClientResponseStatus().getStatusCode());
    }

    // we cheat on this one due to the way the test must be run.  Oops!
    @Test
    public void testDoubleVerifyWithBadLockOnSecondAttempt() {
        final ClientResponse responseIgnore = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        assertEquals(204, response.getClientResponseStatus().getStatusCode());

        final ClientResponse response2 = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        assertEquals(409, response2.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyBeforeActivationWindow() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/notActive/activation/key", "GET");
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyAfterActivationWindow() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/expired/activation/key", "GET");
        assertEquals(410, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidatePersonNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/Net/testId/activation/whatKey", "DELETE");
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateActivationKeyNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/testId/activation/whatKey", "DELETE");
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateWorked() {
        // lock it first
        handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "DELETE");
        assertEquals(204, response.getClientResponseStatus().getStatusCode());

    }

    @Test
    public void testInvalidateLockIssue() {
        // double lock it first
        handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "GET");
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "DELETE");
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateNotLocked() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod("people/NetId/valid/activation/key", "DELETE");
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }
}
