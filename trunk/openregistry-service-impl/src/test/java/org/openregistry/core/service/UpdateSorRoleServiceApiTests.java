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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
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
        PersonService ps = new DefaultPersonService();
        ps.updateSorRole(null, null);
    }

    @Test
    public void invalidSorRoleShouldResultInNonEmptyValidationErrorsCollection() {
        Set mockValidationErrors = MockitoUtils.oneMinimalisticMockConstraintViolation();
        when(this.mockValidator.validate(eq(mockSorRole))).thenReturn(mockValidationErrors);

        PersonService ps = new DefaultPersonService(this.mockValidator);

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

        PersonService ps = new DefaultPersonService(this.mockPersonRepository, this.mockValidator);
        ServiceExecutionResult<SorRole> result = ps.updateSorRole(this.mockSorPerson, this.mockSorRole);
        assertTrue(result.succeeded());
        assertTrue(result.getValidationErrors().isEmpty());
    }
}
