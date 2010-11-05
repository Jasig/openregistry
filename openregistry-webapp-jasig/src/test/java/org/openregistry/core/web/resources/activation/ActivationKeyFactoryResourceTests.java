/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.web.resources.activation;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.openregistry.core.web.resources.JerseyTestSupport;
import org.springframework.web.context.ContextLoaderListener;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class ActivationKeyFactoryResourceTests extends JerseyTestSupport {

    public ActivationKeyFactoryResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources.activation")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    /**
     * Create new activation key.
     */
    @Test
    public void testActivationKeyGeneration() {
        assertNotNull(assertStatusCodeEqualsForRequestUriAndHttpMethod(201, "people/NETID/testId/activation", "POST")
                .getHeaders().getFirst("Location"));
    }

    /**
     * Person Not Found.
     */
    @Test
    public void testPersonNotFound() {
        assertNull(assertStatusCodeEqualsForRequestUriAndHttpMethod(404, "people/Net/testId/activation", "POST")
                .getHeaders().getFirst("Location"));
    }
}
