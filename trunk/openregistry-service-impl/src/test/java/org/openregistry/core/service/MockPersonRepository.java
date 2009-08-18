package org.openregistry.core.service;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import java.util.List;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockPersonRepository implements PersonRepository {

    private Person person;

    public MockPersonRepository(final Person person) {
        this.person = person;
    }

    public Person findByInternalId(final Long id) throws RepositoryAccessException {
        if (person.getId().equals(id)) {
            return person;
        }
        
        return null;
    }

    public SorPerson findSorByInternalId(Long id) throws RepositoryAccessException {
        return null;
    }

    public Person findByIdentifier(final String identifierType, final String identifierValue) throws RepositoryAccessException {
        for (final Identifier identifier : this.person.getIdentifiers()) {
            if (identifier.getType().getName().equals(identifierType) && identifier.getValue().equals(identifierValue)) {
                return this.person;
            }
        }
        throw new RepositoryAccessException();
    }

    public SorPerson findBySorIdentifierAndSource(String sorSourceIdentifier, String sorId) {
        return null;
    }

    public SorPerson findByPersonIdAndSorIdentifier(Long personId, String sorSourceIdentifier) {
        return null;
    }

    public SorRole findSorRoleByInternalId(Long id) {
        return null;
    }

    public List<Person> searchByCriteria(SearchCriteria searchCriteria) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Person> findByFamilyName(String family) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Person savePerson(Person person) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SorPerson saveSorPerson(SorPerson person) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addPerson(Person person) throws RepositoryAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteSorRole(SorPerson person, SorRole role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<SorPerson> getSoRRecordsForPerson(Person person) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Number getCountOfSoRRecordsForPerson(Person person) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteSorPerson(SorPerson person) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deletePerson(Person person) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRole(Person person, Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SorRole saveSorRole(SorRole role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role saveRole(Role role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SorPerson findSorPersonByPersonIdAndSorRoleId(Long personId, Long sorRoleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Identifier> findPersonIdentifiers(Long personId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Identifier> findNetIDBaseIdentifier(String identifierType, String netIDBase) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteName(Name name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
