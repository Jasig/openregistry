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

import org.springframework.beans.factory.FactoryBean;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatcher;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.MockName;
import org.openregistry.core.repository.ReferenceRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.*;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'POST /sor/{sorSourceId}/people' scenarios of <code>SystemOfRecordPeopleResource</code>
 *
 * @author Nancy Mond
 * @since 1.0
 */
public class UpdateSorPersonMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;
    ReferenceRepository mockReferenceRepository;

    public void init() throws Exception {

        //Subbing a set of execution errors
        Set<ConstraintViolation> mockSetWithErrors = mock(Set.class, "withErrors");
        when(mockSetWithErrors.isEmpty()).thenReturn(false);

        //Subbing a set of execution errors
        Set<ConstraintViolation> mockSetWithNoErrors = mock(Set.class, "withNoErrors");
        when(mockSetWithNoErrors.isEmpty()).thenReturn(true);

        //Stubbing 'good' sor person
        final SorPerson mockGoodSorPerson = mock(SorPerson.class);
        when(mockGoodSorPerson.getSsn()).thenReturn("good");

        //Stubbing 'valiation errors' sor person
        final SorPerson mockValidationErrorsSorPerson = mock(SorPerson.class);
        when(mockValidationErrorsSorPerson.getSsn()).thenReturn("errors");

        //Stubbing 'reconciliaton error' sor person
        final SorPerson mockReconciliationErrorSorPerson = mock(SorPerson.class);
        when(mockReconciliationErrorSorPerson.getSsn()).thenReturn("reconciliationError");

        //stubbing ReferenceRepository
        final ReferenceRepository rr = mock(ReferenceRepository.class);
        when(rr.findType(Type.DataTypes.NAME, "FORMAL")).thenReturn(null);
        when(rr.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL)).thenReturn(null);
        when(rr.findType(Type.DataTypes.NAME, Type.NameTypes.AKA)).thenReturn(null);

        this.mockReferenceRepository = rr;

        //Stubbing service execution result no errors
        final ServiceExecutionResult<SorPerson> goodExecutionResult = mock(ServiceExecutionResult.class, "good execution result");
        when(goodExecutionResult.getValidationErrors()).thenReturn(mockSetWithNoErrors);

        //Stubbing service execution result without validation errors
        final ServiceExecutionResult<SorPerson> badExecutionResult = mock(ServiceExecutionResult.class, "bad execution result");
        when(badExecutionResult.getValidationErrors()).thenReturn(mockSetWithErrors);

        //Stubbing Name
        final Name mockName = mock(Name.class);

        //Stubbing Person
        final SorPerson mockPerson = mock(SorPerson.class);
        when(mockPerson.addName()).thenReturn(mockName);

        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("NON-EXISTING-SOR-PERSON"))).thenReturn(null);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("GOOD-EXISTING-SOR-PERSON"))).thenReturn(mockGoodSorPerson);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("ERROR-EXISTING-SOR-PERSON"))).thenReturn(mockValidationErrorsSorPerson);
        when(ps.findBySorIdentifierAndSource(eq("TEST-SOR-ID"), eq("RECON-ERRORS-EXISTING-SOR-PERSON"))).thenReturn(mockReconciliationErrorSorPerson);
        when(ps.updateSorPerson(argThat(new IsGoodUpdate()))).thenReturn(goodExecutionResult);
        when(ps.updateSorPerson(argThat(new HasValidationErrors()))).thenReturn(badExecutionResult);
        when(ps.updateSorPerson(argThat(new HasReconciliationError()))).thenThrow(new IllegalStateException());

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

    private static class IsGoodUpdate extends ArgumentMatcher<SorPerson> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "good".equals(((SorPerson) criteria).getSsn());
        }
    }

   private static class HasValidationErrors extends ArgumentMatcher<SorPerson> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "errors".equals(((SorPerson) criteria).getSsn());
        }
    }

   private static class HasReconciliationError extends ArgumentMatcher<SorPerson> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "reconciliationError".equals(((SorPerson) criteria).getSsn());
        }
    }
}