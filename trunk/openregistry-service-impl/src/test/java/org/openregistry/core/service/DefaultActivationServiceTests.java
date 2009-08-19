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

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.PersonNotFoundException;

/**
 * Test cases for the {@link org.openregistry.core.service.DefaultIdentifierChangeService}.  Note this does not actually
 * test the database.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class DefaultActivationServiceTests {

    private ActivationService activationService;

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

        assertFalse(activationKey.getValue().equals(newActivationKey.getValue()));
        assertNotSame(activationKey, newActivationKey);
    }

    /**
     * Tests whether the new key is generated for a person identified by type and value.
     */
    @Test
    public void testNewKeyGeneratedForIdentifierTypeAndValue() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();
        final ActivationKey newActivationKey = this.activationService.generateActivationKey("NetId", "testId");

        assertFalse(activationKey.getValue().equals(newActivationKey.getValue()));
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

    @Test(expected=NullPointerException.class)
    public void testPersonNotPassedGenerateActivationKey() {
        this.activationService.generateActivationKey(null);
    }

    @Test(expected=PersonNotFoundException.class)
    public void testGetActivationKeyPersonNotFoundIdentifierArgument() {
        this.activationService.getActivationKey("foo", "foo", "duh");
    }

    @Test(expected=PersonNotFoundException.class)
    public void testGetActivationKeyArgumentsNotThere() {
        this.activationService.getActivationKey(null, null, "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoActivationKeyIdentifiers() {
        this.activationService.getActivationKey("NetId", "testId", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetActivationKeyValueDoesntMatch() {
        this.activationService.getActivationKey("NetId", "testId", "foo");
    }

    @Test
    public void testGetActivationKeyValueMatches() {
        final Person person = this.person;
        final ActivationKey activationKey = person.getCurrentActivationKey();

        final ActivationKey actKey = this.activationService.getActivationKey("NetId", "testId", activationKey.getValue());

        assertSame(activationKey, actKey);
    }

    @Test(expected=NullPointerException.class)
    public void testActivationKeyWithPersonNoPerson() {
        this.activationService.getActivationKey(null, "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActivationKeyWithPersonButNoActivationKey() {
        this.activationService.getActivationKey(this.person, null);
    }

    @Test
    public void testGetActivationKeyWithPersonAndMatchingActivationKey() {
        final ActivationKey activationKey = this.person.getCurrentActivationKey();
        final ActivationKey newActivationKey = this.activationService.getActivationKey(this.person, activationKey.getValue());

        assertSame(activationKey, newActivationKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActivationKeyWithPersonButWithNoMatchingActivationKey() {
        this.activationService.getActivationKey(this.person, "foo");
    }
}
