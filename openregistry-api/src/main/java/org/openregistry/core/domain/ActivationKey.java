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
package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a key used to remotely activate a person.  A Person
 * should not be allowed to be activated using this key if its
 * been expired.
 * <p>
 * An ActivationKey MUST be locked before used.  Locks MUST have a timeout of 20 minutes.
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
    String asString();

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

    /**
     * Attempts to acquire a lock on this object.  If someone else holds a lock on this, then an exception is thrown.
     * <p>
     * If the person locking is the same as the one who already holds the lock, the time is extended.
     *
     * @param lock the lock, cannot be null.
     * @throws LockingException if someone has the lock and its not the current person requesting.
     */
    void lock(String lock) throws LockingException;

    /**
     * Determines if the supplied lock is the current lock on the system.
     *
     * @param lock the lock to check
     * @return true if it is, false otherwise.
     */
    boolean hasLock(String lock);

}
