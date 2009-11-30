package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class DeleteSorRoleViaRestResourceTests extends JerseyTestSupport {

    private static final String GOOD_TEST_URI = "/sor/TEST-SOR/people/EXISTING-PERSON/roles/EXISTING-ROLE";
    private static final String NON_EXISTING_PERSON_TEST_URI = "/sor/TEST-SOR/people/NON-EXISTING-PERSON/roles/EXISTING-ROLE";
    private static final String NON_EXISTING_ROLE_TEST_URI = "/sor/TEST-SOR/people/NON-EXISTING-PERSON/roles/NON-EXISTING-ROLE";


    public DeleteSorRoleViaRestResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testDELETE-SORRoleViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void nonExistingSorPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(404, NON_EXISTING_PERSON_TEST_URI, "DELETE");
    }

    @Test
    public void nonExistingSorRole() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(404, NON_EXISTING_ROLE_TEST_URI, "DELETE");
    }

    @Test
    public void postAndGetNotAllowedOnSorPersonResource() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(405, GOOD_TEST_URI, "GET");
        assertStatusCodeEqualsForRequestUriAndHttpMethod(405, GOOD_TEST_URI, "POST");
    }

    @Test
    public void successfulDelete() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, GOOD_TEST_URI, "DELETE");
    }
}
