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

package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class DeleteSorPersonViaRestResourceTests extends JerseyTestSupport {

    private static final String EXISTING_SOR_PERSON_TEST_URI = "/sor/TEST-SOR/people/EXISTING-PERSON";
    private static final String NON_EXISTING_SOR_PERSON_TEST_URI = "/sor/TEST-SOR/people/NON-EXISTING-PERSON";

    public DeleteSorPersonViaRestResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testDELETE-SORPersonViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void deletingNonExistingSorPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(404, NON_EXISTING_SOR_PERSON_TEST_URI, DELETE_HTTP_METHOD);
    }

    @Test
    public void postAndGetNotAllowedOnSorPersonResource() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(405, EXISTING_SOR_PERSON_TEST_URI, GET_HTTP_METHOD);
        assertStatusCodeEqualsForRequestUriAndHttpMethod(405, EXISTING_SOR_PERSON_TEST_URI, POST_HTTP_METHOD);
    }

    @Test
    public void deletingExistingSorPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethod(204, EXISTING_SOR_PERSON_TEST_URI, DELETE_HTTP_METHOD);
    }
}