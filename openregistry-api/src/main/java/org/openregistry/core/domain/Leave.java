package org.openregistry.core.domain;

import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Leave extends MutableDateRange {

    Type getReason();

    void setReason(Type type);
}
