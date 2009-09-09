package org.openregistry.core.web.resources;

import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.web.context.ContextLoaderListener;
import org.junit.Test;
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
                POST_HTTP_METHOD, new String("incorrect incoming data"));
    }

    @Test
    public void missingRequiredData() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, new PersonRequestRepresentation());
    }
}
