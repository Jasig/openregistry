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

import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.springframework.beans.factory.FactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'PUT /sor/{sorSourceId}/people/{sorPersonId}/roles/{sorRoleId}' scenarios of <code>SystemOfRecordRolesResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class UpdateSorRoleMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {

        //Subbing a set of execution errors
        Set<ConstraintViolation> mockSetWithErrors = mock(Set.class, "withErrors");
        when(mockSetWithErrors.size()).thenReturn(1);

        //Subbing a set of execution errors
        Set<ConstraintViolation> mockSetWithNoErrors = mock(Set.class, "withNoErrors");
        when(mockSetWithNoErrors.size()).thenReturn(0);

        //Stubbing SorSponsor
        final SorSponsor mockSorSponsor = mock(SorSponsor.class);

        //Stubbing 'good' sor role
        final SorRole mockGoodSorRole = mock(SorRole.class);
        when(mockGoodSorRole.getCode()).thenReturn("GOOD");
        when(mockGoodSorRole.setSponsor()).thenReturn(mockSorSponsor);

        //Stubbing 'bad' sor role
        final SorRole mockBadSorRole = mock(SorRole.class);
        when(mockBadSorRole.getCode()).thenReturn("BAD");
        when(mockBadSorRole.setSponsor()).thenReturn(mockSorSponsor);

        //Stubbing service execution result with validation errors
        final ServiceExecutionResult<SorRole> goodExecutionResult = mock(ServiceExecutionResult.class, "good execution result");
        when(goodExecutionResult.getValidationErrors()).thenReturn(mockSetWithNoErrors);

        //Stubbing service execution result without validation errors
        final ServiceExecutionResult<SorRole> badExecutionResult = mock(ServiceExecutionResult.class, "bad execution result");
        when(badExecutionResult.getValidationErrors()).thenReturn(mockSetWithErrors);

        //Stubbing Person
        final SorPerson mockPerson = mock(SorPerson.class);
        when(mockPerson.findSorRoleBySorRoleId(eq("GOOD-SOR-ROLE-ID"))).thenReturn(mockGoodSorRole);
        when(mockPerson.findSorRoleBySorRoleId(eq("BAD-SOR-ROLE-ID"))).thenReturn(mockBadSorRole);

        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("NON-EXISTING-SOR-PERSON"))).thenReturn(null);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("EXISTING-SOR-PERSON"))).thenReturn(mockPerson);
        when(ps.updateSorRole(argThat(new IsGoodSorRoleMatch()))).thenReturn(goodExecutionResult);
        when(ps.updateSorRole(argThat(new IsBadSorRoleMatch()))).thenReturn(badExecutionResult);

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

    private static class IsGoodSorRoleMatch extends ArgumentMatcher<SorRole> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "GOOD".equals(((SorRole) criteria).getCode());
        }
    }

    private static class IsBadSorRoleMatch extends ArgumentMatcher<SorRole> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "BAD".equals(((SorRole) criteria).getCode());
        }
    }

}