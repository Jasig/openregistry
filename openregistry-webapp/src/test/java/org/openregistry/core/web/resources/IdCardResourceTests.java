package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.springframework.web.context.ContextLoaderListener;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**Tests for ID card resource
 * @author  Muhammad Siddique
 *
 */
public class IdCardResourceTests extends JerseyTestSupport{

    private static final String RESOURCE_UNDER_TEST_URI = "/person{personId}/idcard";
    public IdCardResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testIdCardResourceApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void testPOSTIDCardResource() {

        //todo complete this test case


    }
}
