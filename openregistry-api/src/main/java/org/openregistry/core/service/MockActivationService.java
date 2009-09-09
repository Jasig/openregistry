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

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Default implementation of the {@link ActivationService}.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class MockActivationService implements ActivationService {

    private final PersonRepository personRepository;

    private DateGenerator startDateGenerator = null;

    private DateGenerator endDateGenerator = null;

    public MockActivationService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ActivationKey generateActivationKey(final Person person) {
        return null;
    }

    public ActivationKey generateActivationKey(String identifierType, String identifierValue) throws PersonNotFoundException, IllegalArgumentException  {
        return null;
    }

    public void invalidateActivationKey(final Person person, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
    }

    public void invalidateActivationKey(final String identifierType, final String identifierValue, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
    }

    public ActivationKey getActivationKey(final String identifierType, final String identifierValue, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException {
        return null;
    }

    public ActivationKey getActivationKey(final Person person, final String activationKey, final String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException {
        return null;
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