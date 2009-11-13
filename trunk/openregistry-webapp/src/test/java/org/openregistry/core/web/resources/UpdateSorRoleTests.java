package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.openregistry.core.web.resources.representations.RoleRepresentation;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @since 1.0
 */
public class UpdateSorRoleTests extends JerseyTestSupport {

    private static final String NON_EXISTING_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/NON-EXISTING-SOR-PERSON/roles/r123";
    private static final String GOOD_SOR_ROLE_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/EXISTING-SOR-PERSON/roles/GOOD-SOR-ROLE-ID";
    private static final String BAD_SOR_ROLE_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/EXISTING-SOR-PERSON/roles/BAD-SOR-ROLE-ID";

    public UpdateSorRoleTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPUT-SORRoleViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void httpMethodNotAllowed() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(405, GOOD_SOR_ROLE_RESOURCE_UNDER_TEST_URI,
                GET_HTTP_METHOD, new RoleRepresentation());

    }

    @Test
    public void incorrectMediaType() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(415, GOOD_SOR_ROLE_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, "bad media type");

    }

    @Test
    public void nonExistingSorPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(404, NON_EXISTING_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, new RoleRepresentation());
    }
}
