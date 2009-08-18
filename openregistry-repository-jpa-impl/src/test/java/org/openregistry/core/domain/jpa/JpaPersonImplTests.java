package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.ActivationKey;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;

/**
 * Tests for the {@link org.openregistry.core.domain.jpa.JpaPersonImpl}.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class JpaPersonImplTests {

    /**
     * Test determines whether the generation of a new activation key actually constructs a new activation key.
     */
    @Test
    public void testDifferentObjectsOnNewGeneration() {
        final JpaPersonImpl person = new JpaPersonImpl();
        final ActivationKey activationKey = person.getCurrentActivationKey();

        person.generateNewActivationKey(new Date());

        final ActivationKey activationKeyNew = person.getCurrentActivationKey();

        assertNotSame(activationKey, activationKeyNew);
    }

    /**
     * Tests whether remove activation key actually removes the activation key.
     */
    @Test
    public void testRemoveActivationKey() {
        final JpaPersonImpl person = new JpaPersonImpl();
        assertNotNull(person.getCurrentActivationKey());

        person.removeCurrentActivationKey();
        assertNull(person.getCurrentActivationKey());
    }
}
