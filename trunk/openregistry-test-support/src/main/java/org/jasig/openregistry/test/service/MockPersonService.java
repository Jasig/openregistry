package org.jasig.openregistry.test.service;

import org.jasig.openregistry.test.domain.MockPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.SearchCriteria;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.service.GeneralServiceExecutionResult;

import java.util.List;

/**
 * @since 1.0
 */
public class MockPersonService implements PersonService {

    @Override
    public Person findPersonById(Long id) {
        return new MockPerson();
    }

    @Override
    public Person findPersonByIdentifier(String identifierType, String identifierValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public SorPerson findByPersonIdAndSorIdentifier(Long id, String sourceSorIdentifier) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public SorPerson findBySorIdentifierAndSource(String sorSource, String sorId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<SorPerson> getSorPersonsFor(Person person) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<SorPerson> getSorPersonsFor(Long personId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean deleteSystemOfRecordPerson(SorPerson sorPerson, boolean mistake, String terminationTypes) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean deleteSystemOfRecordPerson(String sorSource, String sorId, boolean mistake, String terminationTypes) throws PersonNotFoundException, IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean deleteSystemOfRecordRole(SorPerson sorPerson, SorRole sorRole, boolean mistake, String terminationTypes) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public ServiceExecutionResult<SorRole> validateAndSaveRoleForSorPerson(SorPerson sorPerson, SorRole sorRole) throws IllegalArgumentException {
        return new GeneralServiceExecutionResult<SorRole>(sorRole);
    }

    @Override
    public ServiceExecutionResult<Person> addPerson(ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public ServiceExecutionResult<Person> forceAddPerson(ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public ServiceExecutionResult<Person> addPersonAndLink(ReconciliationCriteria reconciliationCriteria, Person person) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<PersonMatch> searchForPersonBy(SearchCriteria searchCriteria) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public ServiceExecutionResult<SorPerson> updateSorPerson(SorPerson sorPerson) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public ServiceExecutionResult<SorRole> updateSorRole(SorPerson sorPerson, SorRole role) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean removeSorName(SorPerson sorPerson, Long nameId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean moveAllSystemOfRecordPerson(Person fromPerson, Person toPerson) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson sorPerson) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson sorPerson) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean expireRole(SorRole role) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean renewRole(SorRole role) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
