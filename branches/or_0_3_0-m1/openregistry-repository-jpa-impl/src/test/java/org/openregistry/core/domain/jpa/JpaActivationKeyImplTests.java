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

package org.openregistry.core.domain.jpa;

import org.junit.Test;
import static org.junit.Assert.*;
import org.openregistry.core.domain.ActivationKey;

import java.util.Date;
import java.util.Calendar;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class JpaActivationKeyImplTests {

    /**
     * Test whether the activation key value generator is not using certain values.  Clearly this test passing just
     * means that THAT particular random value didn't contain the values.
     */
    @Test
    public void testProperKeyGenerationId() {
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl();

        assertFalse(activationKey.asString().contains("l")); // lowercase L
        assertFalse(activationKey.asString().contains("I")); // uppercase i
        assertFalse(activationKey.asString().contains("0")); // number zero
        assertFalse(activationKey.asString().contains("O")); // uppercase o
    }

    /**
     * Tests that there are 10 days between the start and end dates.
     */
    @Test
    public void testDefaultDateGeneration() {
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl();
        final Date start = activationKey.getStart();
        final Date end = activationKey.getEnd();

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        final Date newEndDate = calendar.getTime();

        assertEquals(end, newEndDate);
    }

    /**
     * Test to make sure the dates truly are immutable to protect them from manipulation.
     */
    @Test
    public void testImmutabilityOfDates() {
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl(new Date(), new Date(System.currentTimeMillis()+100000));

        final Date endDate1 = activationKey.getEnd();
        final Date endDate2 = activationKey.getEnd();

        final Date startDate1 = activationKey.getStart();
        final Date startDate2 = activationKey.getEnd();

        assertNotSame(endDate1, endDate2);
        assertNotSame(startDate1, startDate2);
    }

    @Test
    public void testValidity() {
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl();
        assertTrue(activationKey.isValid());
    }

    @Test
    public void testNotValidYet() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        final Date newStartDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        final Date newEndDate = calendar.getTime();
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl(newStartDate, newEndDate);

        assertTrue(activationKey.isNotYetValid());
    }

    @Test
    public void testExpired() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        final Date newEndDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        final Date newStartDate = calendar.getTime();

        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl(newStartDate, newEndDate);

        assertTrue(activationKey.isExpired());
    }

    @Test
    public void testConstructorWithJustEndDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final Date newEndDate = calendar.getTime();
        final ActivationKey activationKey = new JpaActivationKeyImpl(newEndDate);

        assertEquals(newEndDate, activationKey.getEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadStartAndEndDates() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final Date newEndDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +2);
        final Date startDate = calendar.getTime();

        final ActivationKey activationKey = new JpaActivationKeyImpl(startDate, newEndDate);
    }

    @Test
    public void testEqualsAndHashCode() {
        final ActivationKey activationKey1 = new JpaActivationKeyImpl();
        final ActivationKey activationKey2 = new JpaActivationKeyImpl();

        assertFalse(activationKey1.equals(activationKey2));
        assertFalse(activationKey2.equals(activationKey1));
        assertFalse(activationKey1.hashCode() == activationKey2.hashCode());
    }
}
