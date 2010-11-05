package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0
 */
public class ProcessEmailResourceTests extends JerseyTestSupport {

    private static final String RESOURCE_UNDER_TEST_URI = "/email";

    private static final String WELL_FORMED_EMAIL = "good@email.com";

    private static final String MALFORMED_EMAIL = "bad-email.com";

    private static final String EMAIL_WITH_CONFLICT = "conflict@email.com";

    public ProcessEmailResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPOSTEmailViaRESTApplicationContext.xml")
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

    @Test
    public void invalidEmailType() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "invalid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "existent-person");
        requestParams.put("affiliation", "valid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, WELL_FORMED_EMAIL, requestParams);
    }

    @Test
    public void invalidAffiliationType() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "valid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "existent-person");
        requestParams.put("affiliation", "invalid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, WELL_FORMED_EMAIL, requestParams);
    }

    @Test
    public void nonExistentPerson() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "valid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "non-existent-person");
        requestParams.put("affiliation", "valid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(404, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, WELL_FORMED_EMAIL, requestParams);
    }

    @Test
    public void malformedEmail() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "valid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "existent-person");
        requestParams.put("affiliation", "valid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(400, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, MALFORMED_EMAIL, requestParams);
    }

    @Test
    public void internalConflict() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "valid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "existent-person");
        requestParams.put("affiliation", "valid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(409, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, EMAIL_WITH_CONFLICT, requestParams);
    }

    @Test
    public void success() {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("emailType", "valid");
        requestParams.put("identifierType", "NETID");
        requestParams.put("identifier", "existent-person");
        requestParams.put("affiliation", "valid");
        requestParams.put("sor", "existent-sor");
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndMediaTypeAndEntityWithQueryParams(200, RESOURCE_UNDER_TEST_URI,
                POST_HTTP_METHOD, MediaType.TEXT_PLAIN_TYPE, WELL_FORMED_EMAIL, requestParams);
    }
}
