package org.openregistry.core.domain;

import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DateRange {

    Date getStart();

    Date getEnd();

    void setStart(Date date);

    void setEnd(Date date);
}
