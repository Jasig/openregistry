package org.openregistry.core.domain.jpa;

import org.junit.Test;
import static org.junit.Assert.*;

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

        assertFalse(activationKey.getValue().contains("l")); // lowercase L
        assertFalse(activationKey.getValue().contains("I")); // uppercase i
        assertFalse(activationKey.getValue().contains("0")); // number zero
        assertFalse(activationKey.getValue().contains("O")); // uppercase o
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
        final JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl();
        activationKey.setActivationKeyValues(new Date(), new Date());

        final Date endDate1 = activationKey.getEnd();
        final Date endDate2 = activationKey.getEnd();

        final Date startDate1 = activationKey.getStart();
        final Date startDate2 = activationKey.getEnd();

        assertNotSame(endDate1, endDate2);
        assertNotSame(startDate1, startDate2);
    }
}
