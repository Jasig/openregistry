package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Active extends DateRange {

    Type getTerminationReason();

    void setTerminationReason(Type reason);
}
