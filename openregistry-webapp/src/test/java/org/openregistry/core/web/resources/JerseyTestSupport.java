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

    protected static final String POST_HTTP_METHOD = "POST";
    protected static final String PUT_HTTP_METHOD = "PUT";
    protected static final String GET_HTTP_METHOD = "GET";
    protected static final String DELETE_HTTP_METHOD = "DELETE";

    public JerseyTestSupport(WebAppDescriptor webAppDescriptor) {
        super(webAppDescriptor);
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethod(String uriPath, String httpMethod) {
        return client().handle(ClientRequest.create().build(resource().path(uriPath).getURI(), httpMethod));
    }

    protected final ClientResponse handleClientRequestForUriPathAndHttpMethodAndEntity(String uriPath, String httpMethod, Object entity) {
        final ClientRequest req = ClientRequest.create().build(resource().path(uriPath).getURI(), httpMethod);
        req.setEntity(entity);
        return client().handle(req);
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(int statusCode, String uriPath,
                                                                                             String httpMethod, Object entity) {

        final ClientResponse response = handleClientRequestForUriPathAndHttpMethodAndEntity(uriPath, httpMethod, entity);
        assertEquals(statusCode, response.getStatus());
        return response;
    }

    protected final ClientResponse assertStatusCodeEqualsForRequestUriAndHttpMethod(int statusCode, String uriPath, String httpMethod) {
        final ClientResponse response = handleClientRequestForUriPathAndHttpMethod(uriPath, httpMethod);
        assertEquals(statusCode, response.getStatus());
        return response;
    }
}
