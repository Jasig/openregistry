package org.openregistry.core.web.resources.activation;

import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.grizzly.util.buf.Base64Utils;
import org.springframework.web.context.ContextLoaderListener;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class ActivationKeyProcessorResourceTests extends JerseyTest {

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
        final WebResource resource = resource().path("people/Net/testId/activation/whatKey");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        final String locationHeader = response.getHeaders().getFirst("Location");
        assertNull(locationHeader);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyKeyNotFound() {
        final WebResource resource = resource().path("people/NetId/valid/activation/whatKey");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyEverythingOkay() {
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        assertEquals(204, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testDoubleVerifyWithGoodLockOnSecondAttempt() {
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        assertEquals(204, response.getClientResponseStatus().getStatusCode());

        final WebResource resource2 = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder2 = ClientRequest.create();
        final ClientRequest request2 = builder2.build(resource2.getURI(), "GET");

        final ClientResponse response2 = client().handle(request2);
        assertEquals(204, response2.getClientResponseStatus().getStatusCode());
    }

    // we cheat on this one due to the way the test must be run.  Oops!
    @Test
    public void testDoubleVerifyWithBadLockOnSecondAttempt() {
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse responseIgnore = client().handle(request);
        final ClientResponse response = client().handle(request);

        assertEquals(204, response.getClientResponseStatus().getStatusCode());

        final WebResource resource2 = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder2 = ClientRequest.create();
        final ClientRequest request2 = builder2.build(resource2.getURI(), "GET");

        final ClientResponse response2 = client().handle(request2);
        assertEquals(409, response2.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyBeforeActivationWindow() {
        final WebResource resource = resource().path("people/NetId/notActive/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testVerifyAfterActivationWindow() {
        final WebResource resource = resource().path("people/NetId/expired/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        final ClientResponse response = client().handle(request);
        assertEquals(410, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidatePersonNotFound() {
        final WebResource resource = resource().path("people/Net/testId/activation/whatKey");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");
        final ClientResponse response = client().handle(request);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateActivationKeyNotFound() {
        final WebResource resource = resource().path("people/NetId/testId/activation/whatKey");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");
        final ClientResponse response = client().handle(request);
        assertEquals(404, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateWorked() {
        // lock it first
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        client().handle(request);

        final ClientRequest.Builder builder2 = ClientRequest.create();
        final ClientRequest request2 = builder2.build(resource.getURI(), "DELETE");

        final ClientResponse response = client().handle(request2);
        assertEquals(204, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateLockIssue() {
        // lock it first
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder = ClientRequest.create();
        final ClientRequest request = builder.build(resource.getURI(), "GET");

        client().handle(request);
        client().handle(request);

        final ClientRequest.Builder builder2 = ClientRequest.create();
        final ClientRequest request2 = builder2.build(resource.getURI(), "DELETE");

        final ClientResponse response = client().handle(request2);
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }

    @Test
    public void testInvalidateNotLocked() {
        final WebResource resource = resource().path("people/NetId/valid/activation/key");
        final ClientRequest.Builder builder2 = ClientRequest.create();
        final ClientRequest request2 = builder2.build(resource.getURI(), "DELETE");

        final ClientResponse response = client().handle(request2);
        assertEquals(409, response.getClientResponseStatus().getStatusCode());
    }
}
