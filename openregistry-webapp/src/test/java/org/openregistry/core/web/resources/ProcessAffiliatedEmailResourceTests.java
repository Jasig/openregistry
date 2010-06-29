package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * @since 1.0
 */
public class ProcessAffiliatedEmailResourceTests  extends JerseyTestSupport {

    private static final String RESOURCE_UNDER_TEST_URI = "/affiliated-email";

    private static final String WELL_FORMED_EMAIL = "test@email.com";

    public ProcessAffiliatedEmailResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPOSTAffiliatedEmailViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void incorrectIncomingMediaType() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(415, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.APPLICATION_XML_TYPE, WELL_FORMED_EMAIL, new HashMap<String, String>());
    }

    @Test
    public void methodNotAllowed() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(405, RESOURCE_UNDER_TEST_URI, PUT_HTTP_METHOD);
    }

    @Test
    public void requiredRequestParamsMissing() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, WELL_FORMED_EMAIL, new HashMap<String, String>());
    }
}
