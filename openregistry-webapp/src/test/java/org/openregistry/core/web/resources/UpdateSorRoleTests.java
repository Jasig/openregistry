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

    private static final String RESOURCE_UNDER_TEST_URI = "/sor/test/people/p123/roles/r123";

    public UpdateSorRoleTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPUT-SORRoleViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void toBeRenamedAndFurtherDeveloped() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(204, RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, new RoleRepresentation());
    }
}
