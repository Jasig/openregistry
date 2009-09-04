package org.openregistry.core.web.resources;

import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;

import static org.junit.Assert.*;

/**
 * A support abstract class containing common pieces of <code>JerseyClient</code> API boilerplate code that all
 * JerseyTest-based functional tests could take advantage of
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public abstract class JerseyTestSupport extends JerseyTest {

    public JerseyTestSupport(WebAppDescriptor webAppDescriptor) {
        super(webAppDescriptor);
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethod(String uriPath, String httpMethod) {
        return client().handle(ClientRequest.create().build(resource().path(uriPath).getURI(), httpMethod));
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethod(int statusCode, String uriPath, String httpMethod) {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod(uriPath, httpMethod);
        assertEquals(statusCode, response.getStatus());
        return response;
    }
}
