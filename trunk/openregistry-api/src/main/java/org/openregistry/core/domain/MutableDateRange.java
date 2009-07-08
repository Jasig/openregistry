package org.openregistry.core.domain;

import java.util.Date;

/**
 * Extends the date range to allow for the range to be immutable.
 * <p>
 * Impementers should ensure that START is always BEFORE END.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface MutableDateRange extends DateRange {

    /**
     * Sets the start date.
     *
     * @param date the start date.
     */
    void setStart(Date date);

    /**
     * Sets the end date.
     *
     * @param date the end date
     */
    void setEnd(Date date);
}
