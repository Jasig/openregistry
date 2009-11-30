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

import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.PersonService;
import org.springframework.beans.factory.FactoryBean;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'DELETE /sor/{sorSourceId}/people/{sorPersonId}/roles/{sorRoleId}' scenarios of <code>SystemOfRecordRolesResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class DeleteSorRoleMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {
        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);

        //Stubbing SorRole
        final SorRole mockSorRole = mock(SorRole.class);

        //Stubbing SorPerson
        final SorPerson mockSorPerson = mock(SorPerson.class);
        when(mockSorPerson.findSorRoleBySorRoleId(eq("NON-EXISTING-ROLE"))).thenReturn(null);
        when(mockSorPerson.findSorRoleBySorRoleId(eq("EXISTING-ROLE"))).thenReturn(mockSorRole);

        //stubbing different deletion scenarios
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR"), eq("NON-EXISTING-PERSON"))).thenReturn(null);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR"), eq("EXISTING-PERSON"))).thenReturn(mockSorPerson);
        when(ps.deleteSystemOfRecordRole(eq(mockSorPerson), eq(mockSorRole), eq(false), eq("UNSPECIFIED"))).thenReturn(true);

        this.mockPersonService = ps;
    }

    public PersonService getObject() throws Exception {
        return this.mockPersonService;
    }

    public Class<? extends PersonService> getObjectType() {
        return PersonService.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
