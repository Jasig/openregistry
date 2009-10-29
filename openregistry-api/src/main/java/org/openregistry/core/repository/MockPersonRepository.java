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
package org.openregistry.core.repository;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.SearchCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockPersonRepository implements PersonRepository {

    private final List<Person> persons = new ArrayList<Person>();

    private final List<SorPerson> sorPersons = new ArrayList<SorPerson>();

    public MockPersonRepository(final Person person) {
        this(new Person[] {person});
    }

    public MockPersonRepository(final Person... persons) {
        this(persons, new SorPerson[] {});
    }

    public MockPersonRepository(final Person[] persons, final SorPerson[] sorPersons) {
        this.persons.addAll(Arrays.asList(persons));
        this.sorPersons.addAll(Arrays.asList(sorPersons));
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public Person findByInternalId(final Long id) throws RepositoryAccessException {
        for (final Person person : this.persons) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        
        return null;
    }

    public SorPerson findSorByInternalId(Long id) throws RepositoryAccessException {
        for (final SorPerson person : this.sorPersons) {
            if (person.getId().equals(id)) {
                return person;
            }
        }

        return null;
    }

    public Person findByIdentifier(final String identifierType, final String identifierValue) throws RepositoryAccessException {
        System.out.println("PERSON COUNT: " + this.persons.size());
        for (final Person person : this.persons) {
            for (final Identifier identifier : person.getIdentifiers()) {
                if (identifier.getType().getName().equals(identifierType) && identifier.getValue().equals(identifierValue)) {
                    return person;
                }
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

    public List<Person> searchByCriteria(SearchCriteria searchCriteria) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Person> findByFamilyName(String family) throws RepositoryAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Person savePerson(Person person) throws RepositoryAccessException {
        return person;
    }

    public SorPerson saveSorPerson(SorPerson sorPerson) throws RepositoryAccessException {
        return sorPerson;
    }

    public void deleteSorRole(SorPerson person, SorRole role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<SorPerson> getSoRRecordsForPerson(final Person person) {
        final List<SorPerson> sorPersons = new ArrayList<SorPerson>();

        for (final SorPerson sorPerson : this.sorPersons) {
            if (sorPerson.getPersonId().equals(person.getId())) {
                sorPersons.add(sorPerson);
            }
        }

        return sorPersons;
    }

    public Number getCountOfSoRRecordsForPerson(final Person person) {
        return getSoRRecordsForPerson(person).size();
    }

    public void deleteSorPerson(final SorPerson person) {
        this.sorPersons.remove(person);
        System.out.println("DELETE SORPERSON: " + this.sorPersons.size());
    }

    public void deletePerson(final Person person) {
        this.persons.remove(person);
    }

    public void updateRole(Person person, Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SorRole saveSorRole(SorRole role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SorPerson findSorPersonByPersonIdAndSorRoleId(Long personId, Long sorRoleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
