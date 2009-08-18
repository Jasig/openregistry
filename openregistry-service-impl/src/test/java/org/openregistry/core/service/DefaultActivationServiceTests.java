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
    public void testProperValuesProvidedWithTypeAndId() {
        this.activationService.generateActivationKey(null, null);
    }

    /**
     * Tests what happens if the person is not found.
     */
    @Test(expected= PersonNotFoundException.class)
    public void testPersonNotFound() {
        this.activationService.generateActivationKey("foo", "bar");
    }

}
