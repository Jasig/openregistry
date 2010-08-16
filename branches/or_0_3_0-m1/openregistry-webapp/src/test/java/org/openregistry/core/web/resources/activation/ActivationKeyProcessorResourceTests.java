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
        assertNull(assertStatusCodeEqualsForRequestUriAndHttpMethod(404, "people/Net/testId/activation/whatKey", "GET")
                .getHeaders().getFirst("Location"));
    }

    @Test
    public void testVerifyKeyNotFound() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(404, "people/NETID/valid/activation/whatKey", "GET");
    }

    @Test
    public void testVerifyEverythingOkay() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "GET");
    }

    @Test
    public void testDoubleVerifyWithGoodLockOnSecondAttempt() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "GET");
    }

    // we cheat on this one due to the way the test must be run.  Oops!
    @Test
    public void testDoubleVerifyWithBadLockOnSecondAttempt() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(409, "people/NETID/valid/activation/key", "GET");
    }

    @Test
    public void testVerifyBeforeActivationWindow() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(409, "people/NETID/notActive/activation/key", "GET");
    }

    @Test
    public void testVerifyAfterActivationWindow() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(410, "people/NETID/expired/activation/key", "GET");
    }

    @Test
    public void testInvalidatePersonNotFound() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(404, "people/Net/testId/activation/whatKey", "DELETE");
    }

    @Test
    public void testInvalidateActivationKeyNotFound() {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod(pathToURI("people/NETID/testId/activation/whatKey"), "DELETE");
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateWorked() {
        // lock it first
        handleClientRequestForUriPathAndHttpMethod(pathToURI("people/NETID/valid/activation/key"), "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, "people/NETID/valid/activation/key", "DELETE");

    }

    @Test
    public void testInvalidateLockIssue() {
        // double lock it first
        handleClientRequestForUriPathAndHttpMethod(pathToURI("people/NETID/valid/activation/key"), "GET");
        handleClientRequestForUriPathAndHttpMethod(pathToURI("people/NETID/valid/activation/key"), "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(409, "people/NETID/valid/activation/key", "DELETE");
    }

    @Test
    public void testInvalidateNotLocked() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(409, "people/NETID/valid/activation/key", "DELETE");
    }
}
