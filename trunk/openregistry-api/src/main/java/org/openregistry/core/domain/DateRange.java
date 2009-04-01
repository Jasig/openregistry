package org.openregistry.core.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DateRange extends Serializable {

    Date getStart();

    Date getEnd();

    void setStart(Date date);

    void setEnd(Date date);
}
