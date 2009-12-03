/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.web.resources;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.junit.Test;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class PostLinkSorPersonViaRestResourceTests extends JerseyTestSupport {

    private static final String RESOURCE_UNDER_TEST_URI_EXISTING = "/people/TEST-ID-TYPE/EXISTING-PERSON";

    private static final String RESOURCE_UNDER_TEST_URI_NON_EXISTING = "/people/TEST-ID-TYPE/NON-EXISTING";

    public PostLinkSorPersonViaRestResourceTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPOSTLinkSorPersonViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void incorrectIncomingMediaType() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(415, RESOURCE_UNDER_TEST_URI_EXISTING,
                POST_HTTP_METHOD, "incorrect incoming data");
    }

    @Test
    public void nonExistingPersonResource() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(404, RESOURCE_UNDER_TEST_URI_NON_EXISTING,
                POST_HTTP_METHOD, PersonRequestRepresentation.forLinkingSorPersonGood());
    }

    @Test
    public void linkingSorPersonToCalculatedPersonResultsInSomeConflict() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(409, RESOURCE_UNDER_TEST_URI_EXISTING,
                POST_HTTP_METHOD, PersonRequestRepresentation.forLinkingSorPersonBad());
    }

    @Test
    public void linkingSorPersonToCalculatedPersonSuccess() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(204, RESOURCE_UNDER_TEST_URI_EXISTING,
                POST_HTTP_METHOD, PersonRequestRepresentation.forLinkingSorPersonGood());
    }
}