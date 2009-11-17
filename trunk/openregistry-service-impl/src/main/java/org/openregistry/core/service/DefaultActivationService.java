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
package org.openregistry.core.service;

import org.openregistry.core.domain.*;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Default implementation of the {@link org.openregistry.core.service.ActivationService}.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Named("activationService")
public final class DefaultActivationService implements ActivationService {

    private final PersonRepository personRepository;

    private DateGenerator startDateGenerator = new CurrentDateTimeDateGenerator();

    private DateGenerator endDateGenerator = new AdditiveDateTimeDateGenerator(Calendar.DAY_OF_MONTH, 10);

    @Inject
    public DefaultActivationService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public ActivationKey generateActivationKey(final Person person) {
        Assert.notNull(person, "person cannot be null.");
        final Date startDate = this.startDateGenerator.getNewDate();
        final ActivationKey key = person.generateNewActivationKey(startDate, this.endDateGenerator.getNewDate(startDate));
        this.personRepository.savePerson(person);
        return key;
    }

    @Transactional
    public ActivationKey generateActivationKey(String identifierType, String identifierValue) throws PersonNotFoundException, IllegalArgumentException  {
        Assert.notNull(identifierType, "identifierType cannot be null.");
        Assert.notNull(identifierValue, "identifierValue cannot be null.");

        return this.generateActivationKey(findPerson(identifierType, identifierValue));
    }

    @Transactional
    public void invalidateActivationKey(final Person person, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        Assert.notNull(person, "person cannot be null.");
        Assert.notNull(activationKey, "activationKey cannot be null.");
        Assert.notNull(lock, "lock cannot be null.");

        final ActivationKey currentKey = person.getCurrentActivationKey();

        if (currentKey == null || !currentKey.asString().equals(activationKey)) {
            throw new NoSuchElementException("No Activation Key matching [" + activationKey + "] found for that Person.");
        }
        
        if (!currentKey.isValid()) {
            throw new IllegalStateException("No valid activationKey found for activation key matching [" + activationKey + "]");
        }

        if (currentKey.hasLock(lock)) {
            person.removeCurrentActivationKey();
            this.personRepository.savePerson(person);
        } else {
            throw new LockingException("You do not hold the lock for this key.");
        }
    }

    @Transactional
    public void invalidateActivationKey(final String identifierType, final String identifierValue, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        Assert.notNull(identifierType, "identifierType cannot be null.");
        Assert.notNull(identifierValue, "identifierValue cannot be null.");
        Assert.notNull(activationKey, "activationKey cannot be null.");
        Assert.notNull(lock, "lock cannot be null.");

        final Person person = findPerson(identifierType, identifierValue);
        this.invalidateActivationKey(person, activationKey, lock);
    }

    @Transactional
    public ActivationKey getActivationKey(final String identifierType, final String identifierValue, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException {
        Assert.notNull(identifierType, "identifierType cannot be null.");
        Assert.notNull(identifierValue, "identifierValue cannot be null.");
        Assert.notNull(activationKey, "activationKey cannot be null.");
        Assert.notNull(lock, "lock cannot be null.");

        final Person person = findPerson(identifierType, identifierValue);
        return this.getActivationKey(person, activationKey, lock);
    }

    @Transactional
    public ActivationKey getActivationKey(final Person person, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException {
        Assert.notNull(person, "person cannot be null.");
        Assert.notNull(activationKey, "activationKey cannot be null.");
        Assert.notNull(lock, "lock cannot be null.");

        ActivationKey key = person.getCurrentActivationKey();

        if (key == null || !key.asString().equals(activationKey)) {
            return null;
        }

        key.lock(lock);
        this.personRepository.savePerson(person);

        return key;
    }


    protected Person findPerson(final String identifierType, final String identifierValue) throws PersonNotFoundException {
        try {
            return this.personRepository.findByIdentifier(identifierType, identifierValue);
        } catch (Exception e){
            throw new PersonNotFoundException();
        }
    }

    public void setStartDateGenerator(final DateGenerator startDateGenerator) {
        this.startDateGenerator = startDateGenerator;
    }

    public void setEndDateGenerator(final DateGenerator endDateGenerator) {
        this.endDateGenerator = endDateGenerator;
    }
}