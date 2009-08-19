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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Default implementation of the {@link org.openregistry.core.service.ActivationService}.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Service("activationService")
public final class DefaultActivationService implements ActivationService {

    @Autowired(required=true)
    private final PersonRepository personRepository;

    private DateGenerator startDateGenerator = new CurrentDateTimeDateGenerator();

    private DateGenerator endDateGenerator = new AdditiveDateTimeDateGenerator(Calendar.DAY_OF_MONTH, 10);

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required=true)
    public DefaultActivationService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public ActivationKey generateActivationKey(final Person person) {
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
    public void invalidateActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        ActivationKey currentKey = person.getCurrentActivationKey();
        if (currentKey == null || currentKey.getValue() == null || !currentKey.getValue().equals(activationKey)) throw new IllegalArgumentException();
        if (!currentKey.isValid()) throw new IllegalStateException();
        person.removeCurrentActivationKey();
        this.personRepository.savePerson(person);
    }

    @Transactional
    public void invalidateActivationKey(String identifierType, String identifierValue, String activationKey) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        if (identifierType == null || identifierValue == null) throw new IllegalArgumentException();
        Person person = null;
        try {
            person = findPerson(identifierType, identifierValue);
            this.invalidateActivationKey(person, activationKey);
        } catch (PersonNotFoundException e){
            throw e;
        } catch (IllegalArgumentException e){
            throw e;
        } catch (IllegalStateException e){
            throw e;
        }
    }

    @Transactional
    public ActivationKey getActivationKey(String identifierType, String identifierValue, String activationKey) throws PersonNotFoundException, IllegalArgumentException {
        if (identifierType == null || identifierValue == null) throw new IllegalArgumentException();
        try {
            Person person = findPerson(identifierType, identifierValue);
            return this.getActivationKey(person, activationKey);
        } catch (PersonNotFoundException e){
            throw e;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    public ActivationKey getActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException {
        if (person == null) throw new PersonNotFoundException();
        ActivationKey key = person.getCurrentActivationKey();
        if (key == null || key.getValue() == null || !key.getValue().equals(activationKey)) throw new IllegalArgumentException();
        return key;
    }


    protected Person findPerson(String identifierType, String identifierValue) throws PersonNotFoundException {
        Person person = null;
        try {
            person = personRepository.findByIdentifier(identifierType, identifierValue);
            return person;
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