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

package org.jasig.openregistry.test.repository;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.*;

import java.util.*;

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
        for (final Person person : this.persons) {
            for (final Identifier identifier : person.getIdentifiers()) {
                if (identifier.getType().getName().equals(identifierType) && identifier.getValue().equals(identifierValue)) {
                    return person;
                }
            }
        }
        throw new RepositoryAccessException();
    }

    @Override
    public List<Person> findByUnknownIdentifier(String identifierValue) throws RepositoryAccessException {
        final List<Person> people = new ArrayList<Person>();

        for (final Person person : this.persons) {
            for (final Identifier identifier : person.getIdentifiers()) {
                if (identifier.getValue().equalsIgnoreCase(identifierValue)) {
                    people.add(person);
                }
            }
        }
        return people;
    }

    public SorPerson findBySorIdentifierAndSource(String sorSourceIdentifier, String sorId) {
        for (final SorPerson person : this.sorPersons) {
            if (person.getSourceSor().equals(sorSourceIdentifier) && person.getSorId().equals(sorId)) {
                return person;
            }
        }

        return null;
    }

    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSourceIdentifier) {
        for (final SorPerson sorPerson : this.sorPersons) {
            if (sorPerson.getPersonId().equals(personId)  && sorPerson.getSourceSor().equals(sorSourceIdentifier)) {
                return sorPerson;
            }
        }

        return null;
    }

    public List<Person> searchByCriteria(SearchCriteria searchCriteria) throws RepositoryAccessException {
 		return persons;
 	 }

    public List<Person> findByFamilyName(String family) throws RepositoryAccessException {
        final List<Person> people = new ArrayList<Person>();

        for (final Person person : this.persons) {
            for (final Name name : person.getNames()) {
                if (name.getFamily().equalsIgnoreCase(family)) {
                    people.add(person);
                }
            }
        }
        return people;
    }

    public Person savePerson(Person person) throws RepositoryAccessException {
        this.persons.add(person);
        return person;
    }

    public SorPerson saveSorPerson(SorPerson sorPerson) throws RepositoryAccessException {
        this.sorPersons.add(sorPerson);
        return sorPerson;
    }

    public void deleteSorRole(SorPerson person, SorRole role) {
		person.getRoles().remove(role);
    }

    public List<SorPerson> getSoRRecordsForPerson(final Person person) {
        final List<SorPerson> sorPersons = new ArrayList<SorPerson>();

        for (final SorPerson sorPerson : this.sorPersons) {
            if (person.getId().equals(sorPerson.getPersonId())) {
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

	public List<Person> findByEmailAddress(String email) {
        final List<Person> people = new ArrayList<Person>();
        for (final Person person : this.persons) {
            for (final Role role : person.getRoles()) {
                for (final EmailAddress emailAddress : role.getEmailAddresses()) {
                    if (emailAddress.getAddress().equalsIgnoreCase(email)) {
                        people.add(person);
                        break;
                    }
                }
            }
        }
		return people;
	}

	public List<Person> findByEmailAddressAndPhoneNumber(String email,
			String countryCode, String areaCode, String number) {

        final List<Person> people = new ArrayList<Person>();

        boolean foundEmail;

        for (final Person person : this.persons) {
            for (final Role role : person.getRoles()) {
                foundEmail = false;
                for (final EmailAddress emailAddress : role.getEmailAddresses()) {
                    if (emailAddress.getAddress().equalsIgnoreCase(email)) {
                        foundEmail = true;
                        break;
                    }
                }

                if (foundEmail){
                    for (final Phone phone : role.getPhones()){
                        if (phone.getAreaCode().equals(areaCode) && phone.getNumber().equals(number)){
                            people.add(person);
                            break;
                        }
                    }
                }
            }
        }
		return people;
	}

	public List<Person> findByEmailAddressAndPhoneNumber(String email,
			String countryCode, String areaCode, String number, String extension) {
		return new ArrayList<Person>();
	}

	public List<Person> findByPhoneNumber(String countryCode, String areaCode,
			String number, String extension) {

        final List<Person> people = new ArrayList<Person>();
        for (final Person person : this.persons) {
            for (final Role role : person.getRoles()) {
                for (final Phone phone : role.getPhones()) {
                    if (phone.getCountryCode().equals(countryCode) && phone.getAreaCode().equals(areaCode) && phone.getNumber().equals(number) && phone.getExtension().equals(extension)) {
                        people.add(person);
                        break;
                    }
                }
            }
        }
		return people;
	}

	public List<Person> findByPhoneNumber(String countryCode, String areaCode,
			String number) {

        final List<Person> people = new ArrayList<Person>();
        for (final Person person : this.persons) {
            for (final Role role : person.getRoles()) {
                for (final Phone phone : role.getPhones()) {
                    if (phone.getCountryCode().equals(countryCode) && phone.getAreaCode().equals(areaCode) && phone.getNumber().equals(number)) {
                        people.add(person);
                        break;
                    }
                }
            }
        }
		return people;
	}

    @Override
    public SorPerson findSorBySSN(String ssn) {
        for (final SorPerson sorPerson : this.sorPersons) {
            if (sorPerson.getSsn().equals(ssn)) {
                return sorPerson;
            }
        }

        return null;
    }
}
