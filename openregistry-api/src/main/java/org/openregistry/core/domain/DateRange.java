package org.openregistry.core.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * Represents a range of dates.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DateRange extends Serializable {

    /**
     * The start of the date range.  CANNOT be NULL.
     * @return the start date.
     */
    Date getStart();

    /**
     * The end date range.  CAN be NULL if the range has not yet ended.
     *
     * @return the end date, CAN be NULL.
     */
    Date getEnd();
}
