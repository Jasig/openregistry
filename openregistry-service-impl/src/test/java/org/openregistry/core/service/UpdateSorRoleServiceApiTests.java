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

package org.openregistry.core.service;

import org.jasig.openregistry.test.util.MockitoUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.repository.PersonRepository;

import javax.validation.Validator;
import java.util.Set;

/**
 * Unit tests for 'Update Sor Role' service API.
 * Uses Mockito-based stubs for necessary collaborators.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class UpdateSorRoleServiceApiTests {

    SorPerson mockSorPerson;

    SorRole mockSorRole;

    Person mockPerson;

    Role mockRole;

    Validator mockValidator;

    PersonRepository mockPersonRepository;

    @Before
    public void setUp() throws Exception {
        this.mockSorPerson = mock(SorPerson.class);
        this.mockSorRole = mock(SorRole.class);
        this.mockPerson = mock(Person.class);
        this.mockRole = mock(Role.class);
        this.mockPersonRepository = mock(PersonRepository.class);
        this.mockValidator = mock(Validator.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sorPersonAndSorRoleArgumentsCannotBeNull() {
        PersonService ps = new DefaultPersonService(null, null, null, null);
        ps.updateSorRole(null, null);
    }

    @Test
    public void invalidSorRoleShouldResultInNonEmptyValidationErrorsCollection() {
        Set mockValidationErrors = MockitoUtils.oneMinimalisticMockConstraintViolation();
        when(this.mockValidator.validate(eq(mockSorRole))).thenReturn(mockValidationErrors);

        final DefaultPersonService ps = new DefaultPersonService(null, null, null, null);
        ps.setValidator(this.mockValidator);

        ServiceExecutionResult<SorRole> result = ps.updateSorRole(this.mockSorPerson, this.mockSorRole);
        assertFalse(result.succeeded());
        assertFalse(result.getValidationErrors().isEmpty());
    }

    @Test
    public void successfulExectionOfUpdateSorRole() {
        //Stubbing collaborators' behavior        
        when(this.mockSorPerson.getPersonId()).thenReturn(1L);
        when(this.mockSorRole.getId()).thenReturn(1L);
        when(this.mockPerson.findRoleBySoRRoleId(eq(1L))).thenReturn(this.mockRole);
        when(this.mockPersonRepository.saveSorRole(eq(this.mockSorRole))).thenReturn(this.mockSorRole);
        when(this.mockPersonRepository.findByInternalId(eq(1L))).thenReturn(this.mockPerson);

        final DefaultPersonService ps = new DefaultPersonService(this.mockPersonRepository, null, null, null);
        ps.setValidator(this.mockValidator);
        ServiceExecutionResult<SorRole> result = ps.updateSorRole(this.mockSorPerson, this.mockSorRole);
        assertTrue(result.succeeded());
        assertTrue(result.getValidationErrors().isEmpty());
    }
}
