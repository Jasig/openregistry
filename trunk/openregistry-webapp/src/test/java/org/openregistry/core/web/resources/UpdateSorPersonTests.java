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
 * @since 1.0
 */
public class UpdateSorPersonTests extends JerseyTestSupport {

    private static final String NON_EXISTING_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/NON-EXISTING-SOR-PERSON";
    private static final String GOOD_SOR_PERSON_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/GOOD-EXISTING-SOR-PERSON";
    private static final String VALIDATION_ERRORS_SOR_PERSON_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/ERROR-EXISTING-SOR-PERSON";
    private static final String RECONCILIATION_ERRORS_SOR_PERSON_RESOURCE_UNDER_TEST_URI = "/sor/TEST-SOR-ID/people/RECON-ERRORS-EXISTING-SOR-PERSON";

    public UpdateSorPersonTests() {
        super(new WebAppDescriptor.Builder("org.openregistry.core.web.resources")
                .contextPath("openregistry")
                .contextParam("contextConfigLocation", "classpath:testPUT-SORPersonViaRESTApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Test
    public void httpMethodNotAllowed() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(405, GOOD_SOR_PERSON_RESOURCE_UNDER_TEST_URI,
                GET_HTTP_METHOD, new PersonRequestRepresentation());

    }

    @Test
    public void incorrectMediaType() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(415, GOOD_SOR_PERSON_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, "bad media type");

    }

    @Test
    public void nonExistingSorPerson() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(404, NON_EXISTING_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, PersonRequestRepresentation.modifyRepresentationWithRequiredData());
    }

    @Test
    public void goodRequest() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(204, GOOD_SOR_PERSON_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, PersonRequestRepresentation.modifyRepresentationWithRequiredData());
    }

    @Test
    public void badRequest() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(400, VALIDATION_ERRORS_SOR_PERSON_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, PersonRequestRepresentation.modifyRepresentationWithRequiredData());
    }

    @Test
    public void reconcilationError() {
        assertStatusCodeEqualsForRequestUriAndHttpMethodAndEntity(409, RECONCILIATION_ERRORS_SOR_PERSON_RESOURCE_UNDER_TEST_URI,
                PUT_HTTP_METHOD, PersonRequestRepresentation.modifyRepresentationWithRequiredData());
    }
}