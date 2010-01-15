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

import org.jasig.openregistry.test.domain.MockPerson;
import org.jasig.openregistry.test.repository.MockPersonRepository;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.openregistry.core.domain.*;

import java.util.Date;
import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 * Test cases for the {@link org.openregistry.core.service.DefaultIdentifierChangeService}.  Note this does not actually
 * test the database.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class DefaultActivationServiceTests {

    private DefaultActivationService activationService;

    private Person person;

    @Before
    public void setUp() throws Exception {
        this.person = new MockPerson();
        this.activationService = new DefaultActivationService(new MockPersonRepository(this.person));
    }

    // GENERATE KEY TESTS

    /**
     * Tests whether a new key is generated for a person that replaces the key that came with the person.
     */
    @Test
    public void testNewKeyGeneratedForPerson() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();

        final ActivationKey newActivationKey = this.activationService.generateActivationKey(person);

        assertFalse(activationKey.asString().equals(newActivationKey.asString()));
        assertNotSame(activationKey, newActivationKey);
    }

    /**
     * Tests whether the new key is generated for a person identified by type and value.
     */
    @Test
    public void testNewKeyGeneratedForIdentifierTypeAndValue() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();
        final ActivationKey newActivationKey = this.activationService.generateActivationKey("NETID", "testId");

        assertFalse(activationKey.asString().equals(newActivationKey.asString()));
        assertNotSame(activationKey, newActivationKey);
    }

    /**
     * Tests the illegal argument exception.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testProperValuesProvidedWithTypeAndIdForGenerateActivationKey() {
        this.activationService.generateActivationKey(null, null);
    }

    /**
     * Tests what happens if the person is not found.
     */
    @Test(expected= PersonNotFoundException.class)
    public void testPersonNotFoundForGenerateActivationKey() {
        this.activationService.generateActivationKey("foo", "bar");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPersonNotPassedGenerateActivationKey() {
        this.activationService.generateActivationKey(null);
    }

    @Test(expected=PersonNotFoundException.class)
    public void testGetActivationKeyPersonNotFoundIdentifierArgument() {
        this.activationService.getActivationKey("foo", "foo", "duh", "whocares");
    }

    // TESTS FOR GET ACTIVATION KEY

    @Test(expected=IllegalArgumentException.class)
    public void testGetActivationKeyArgumentsNotThere() {
        this.activationService.getActivationKey(null, null, "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoActivationKeyIdentifiers() {
        this.activationService.getActivationKey("NETID", "testId", null, "whocares");
    }

   @Test(expected = IllegalArgumentException.class)
    public void testNoActivationKeyLock() {
        this.activationService.getActivationKey("NETID", "testId", "booyah", null);
    }

    @Test
    public void testGetActivationKeyValueDoesntMatch() {
        assertNull(this.activationService.getActivationKey("NETID", "testId", "foo", "whocares"));
    }

    @Test
    public void testGetActivationKeyValueMatchesWithCorrectLock() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();
        activationKey.lock("me");
        final ActivationKey actKey = this.activationService.getActivationKey("NETID", "testId", activationKey.asString(), "me");

        assertSame(activationKey, actKey);
    }

    @Test(expected = LockingException.class)
    public void testGetActivationKeyValueMatchesWithBadLock() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();
        activationKey.lock("me");

        this.activationService.getActivationKey("NETID", "testId", activationKey.asString(), "notMe");
    }


    @Test(expected=IllegalArgumentException.class)
    public void testActivationKeyWithPersonNoPerson() {
        this.activationService.getActivationKey(null, "foo", "who cares");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActivationKeyWithPersonButNoActivationKey() {
        this.activationService.getActivationKey(this.person, null, "who cares");
    }

    @Test
    public void testGetActivationKeyWithPersonAndMatchingActivationKeyWithProperLock() {
        final ActivationKey activationKey = this.person.getCurrentActivationKey();
        activationKey.lock("foo");
        final ActivationKey newActivationKey = this.activationService.getActivationKey(this.person, activationKey.asString(), "foo");

        assertSame(activationKey, newActivationKey);
    }

    @Test(expected = LockingException.class)
    public void testGetActivationKeyWithPersonAndMatchingActivationKeyWithBadLock() {
        final ActivationKey activationKey = this.person.getCurrentActivationKey();
        activationKey.lock("foo");
        this.activationService.getActivationKey(this.person, activationKey.asString(), "myLock");
    }

    @Test
    public void testGetActivationKeyWithPersonButWithNoMatchingActivationKey() {
        assertNull(this.activationService.getActivationKey(this.person, "foo", "who cares"));
    }

    @Test
    public void testCheckConfigurableDateSettings() {
        final Date startDate = new Date();
        final Date endDate = new Date(System.currentTimeMillis() + 10000);

        this.activationService.setStartDateGenerator(new DateGenerator() {
            public Date getNewDate() {
                return startDate;
            }

            public Date getNewDate(final Date futureDate) {
                return startDate;
            }
        });

        this.activationService.setEndDateGenerator(new DateGenerator() {
            public Date getNewDate() {
                return endDate;
            }

            public Date getNewDate(final Date futureDate) {
                return endDate;
            }
        });

        final ActivationKey activationKey = this.activationService.generateActivationKey(this.person);

        assertEquals(startDate, activationKey.getStart());
        assertEquals(endDate, activationKey.getEnd());
    }

    // TESTS FOR INVALIDATE ACTIVATION KEY
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidateActivationKeyForPersonNullPerson() {
        this.activationService.invalidateActivationKey(null, "foo", "who cares");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidateActivationKeyForPersonWithNullKey() {
        this.activationService.invalidateActivationKey(this.person, null, "who cares");
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalidateActivationKeyForPersonWithInvalidKey() {
        this.activationService.invalidateActivationKey(this.person, "foo", "who cares");
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidateActivationKeyForPersonWithExpiredKey() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        final Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final Date endDate = calendar.getTime();
        final ActivationKey activationKey = this.person.generateNewActivationKey(startDate, endDate);

        this.activationService.invalidateActivationKey(this.person, activationKey.asString(), "who cares");
    }

    @Test
    public void testInvalidateActivationKeyForPersonValidKeyWithValidLock() {
        final ActivationKey activationKey = this.person.getCurrentActivationKey();
        this.activationService.getActivationKey(this.person, this.person.getCurrentActivationKey().asString(), "myLock");
        this.activationService.invalidateActivationKey(this.person, activationKey.asString(), "myLock");
    }

    @Test(expected = LockingException.class)
    public void testInvalidateActivationKeyForPersonValidKeyWithBadLock() {
        final ActivationKey activationKey = this.person.getCurrentActivationKey();
        this.activationService.getActivationKey(this.person, this.person.getCurrentActivationKey().asString(), "myLock");
        this.activationService.invalidateActivationKey(this.person, activationKey.asString(), "notMyLock");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidateActivationKeyNoIdentifierType() {
        this.activationService.invalidateActivationKey(null, "foo", "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidateActivationKeyNoIdentifierValue() {
        this.activationService.invalidateActivationKey("foo", null, "foo", "who cares");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidateActivationKeyNoActivationKey() {
        this.activationService.invalidateActivationKey("foo", "foo", null, "who cares");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidateActivationKeyNoPersonFound() {
        this.activationService.invalidateActivationKey("foo", "foo", null, "who cares");
    }

    @Test
    public void testInvalidateActivationKeyKeyFoundAndCorrectLock() {
        this.activationService.getActivationKey(this.person, this.person.getCurrentActivationKey().asString(), "myLock");
        this.activationService.invalidateActivationKey("NETID", "testId", this.person.getCurrentActivationKey().asString(), "myLock");
    }

    @Test(expected = LockingException.class)
    public void testInvalidateActivationKeyKeyFoundAndIncorrectLock() {
        this.activationService.getActivationKey(this.person, this.person.getCurrentActivationKey().asString(), "myLock");
        this.activationService.invalidateActivationKey("NETID", "testId", this.person.getCurrentActivationKey().asString(), "notMyLock");
    }


    @Test(expected = NoSuchElementException.class)
    public void testInvalidateActivationKeyKeyNotFound() {
        this.activationService.invalidateActivationKey("NETID", "testId", "foo", "who cares");

    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidateActivationKeyNotValidKey() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        final Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final Date endDate = calendar.getTime();
        final ActivationKey activationKey = this.person.generateNewActivationKey(startDate, endDate);

        this.activationService.invalidateActivationKey("NETID", "testId", activationKey.asString(), "who cares");

    }
}
