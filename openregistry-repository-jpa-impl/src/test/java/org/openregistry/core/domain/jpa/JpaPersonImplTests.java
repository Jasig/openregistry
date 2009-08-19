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

        person.generateNewActivationKey(new Date(System.currentTimeMillis() + 10000));

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
