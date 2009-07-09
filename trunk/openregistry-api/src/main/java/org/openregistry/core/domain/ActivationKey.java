package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a key used to remotely activate a person.  A Person
 * should not be allowed to be activated using this key if its
 * been expired.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ActivationKey extends DateRange, Comparable<ActivationKey>, Serializable {

    /**
     * Returns the Activation Key as a String.  Per the use case,
     * this MUST be returned as an 8 character String only consisting
     * of characters [abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ2345679].
     * <p>
     * Whenever this method is called it MUST ALWAYS return the same value.
     * </p>
     *
     * @return the key as a String.  CANNOT be NULL.
     */
    String getId();

    /**
     * Convenience method for determining if current date is before the start date.
     * @return true if its not yet valid, false otherwise.
     */

    boolean isNotYetValid();

    /**
     * Convenience method for determining if the current date is after the end date.
     * @return true if it is, false otherwise.
     */
    boolean isExpired();

    /**
     * Convenience method for determining if the current date is in the start/end date range.
     * 
     * @return if the current date is in the range, false otherwise.
     */
    boolean isValid();
}
