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
package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.LockingException;

/**
 * Service Layer for interacting with Activation Keys in the OpenRegistry System.  ActivationKeys are a method of
 * remotely activating a person, generally to allow them to set up account information.
 * 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ActivationService {

    /**
     * Generates a new activation key for the specified person.  If there is already an existing activation key
     * for the person, the existing key MUST be removed before the new key is created and returned.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Generate+Activation+Key">Generate Activation Key Use Case</a>
     * for the extensive use case.
     * </p>
     *
     * @param person the person to create the key for.  CANNOT be null.
     * @return the newly constructed key.  CANNOT be NULL.  MUST be of the format: 8 characters, and consisting of
     * characters [A-Za-z0-9]
     * @throws IllegalArgumentException if person is null.
     */
    ActivationKey generateActivationKey(Person person);

    /**
     * Generates a new activation key for the specified person.  If there is already an existing activation key
     * for the person, the existing key MUST be removed before the new key is created and returned.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Generate+Activation+Key">Generate Activation Key Use Case</a>
     * for the extensive use case.
     * </p>
     *
     * @param identifierType the identifier type to use to look up the person. CANNOT be NULL.
     * @param identifierValue the identifier value to use to look up the person.  CANNOT be NULL.
     * @return the newly constructed key.  CANNOT be NULL.  MUST be of the format: 8 characters, and consisting of
     * characters [abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ2345679]
     * @throws PersonNotFoundException if the person was not found
     * @throws IllegalArgumentException if identifierType or identifierValue is NULL.
     */
    ActivationKey generateActivationKey(String identifierType, String identifierValue) throws PersonNotFoundException;

    /**
     * Invalidates an existing VALID activation key for a particular person.  This method EXPECTs that the key you are
     * trying to invalidate exists and is STILL valid.  It will act accordingly if this expectation is not met.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Invalidate+Activation+Key">Invalidate Activation Key Use Case</a>
     * for the extensive use case.
     * </p>
     *
     * @param person the person to invalidate the key for.  CANNOT be NULL.
     * @param activationKey the activation key to invalidate.  CANNOT be NULL.
     * @param lock the value to use to confirm your lock on the system.
     * @throws IllegalArgumentException if the activation key does not exist for the person or if the person or activation key is NULL.
     * @throws IllegalStateException if the activation key exists but is not valid.
     * @throws org.openregistry.core.domain.LockingException if you're not the one holding the lock.
     */
    void invalidateActivationKey(Person person, String activationKey, String lock) throws IllegalArgumentException, IllegalStateException, LockingException;

    /**
     * Invalidates an existing VALID activation key for a particular person.  This method EXPECTs that the key you are
     * trying to invalidate exists and is STILL valid.  It will act accordingly if this expectation is not met.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Invalidate+Activation+Key">Invalidate Activation Key Use Case</a>
     * for the extensive use case.
     * </p>
     *
     * @param identifierType the identifier type to use to look up the person. CANNOT be NULL.
     * @param identifierValue the identifier value to use to look up the person.  CANNOT be NULL.
     * @param activationKey the activation key to invalidate.  CANNOT be NULL.
     * @param lock the value to use to confirm your lock on the system.
     * @throws PersonNotFoundException if the person was not found
     * @throws IllegalArgumentException if the identifier type or value is NULL or if the activation key does not exist for the person.
     * @throws IllegalStateException if the activation key exists but is not valid.
     * @throws org.openregistry.core.domain.LockingException if you're not the one holding the lock.
     *
     */
    void invalidateActivationKey(String identifierType, String identifierValue, String activationKey, String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException;

    /**
     * Method for retrieving an activation key for "querying" purposes.   The most common querying will be to determine
     * if an activation key is valid or not.  By looking at the ActivationKey, one can determine whether its valid
     * by calling {@link org.openregistry.core.domain.ActivationKey#isValid()} and then look at any specific properties.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Verify+Activation+Key">Verify Activation Key Use Case</a> for
     * the extensive use case.
     * </p> 
     *
     * @param identifierType the identifier type to use to look up the person. CANNOT be NULL.
     * @param identifierValue the identifier value to use to look up the person.  CANNOT be NULL.
     * @param activationKey the activation key String representation to retrieve.  CANNOT be NULL.
     * @return the activation key or NULL if not found.
     * @param lock the value to use to confirm your lock on the system.
     * @throws PersonNotFoundException if the person was not found,
     * @throws IllegalArgumentException if activation key, identifierType or identifierValue is NULL.
     * @throws LockingException if someone already holds the lock on this.
     */
    ActivationKey getActivationKey(String identifierType, String identifierValue, String activationKey, String lock) throws PersonNotFoundException, IllegalArgumentException, LockingException;

    /**
     * Method for retrieving an activation key for "querying" purposes.   The most common querying will be to determine
     * if an activation key is valid or not.  By looking at the ActivationKey, one can determine whether its valid
     * by calling {@link org.openregistry.core.domain.ActivationKey#isValid()} and then look at any specific properties.
     * <p>
     * See <a href="http://www.ja-sig.org/wiki/display/OR/Verify+Activation+Key">Verify Activation Key Use Case</a> for
     * the extensive use case.
     * </p>
     *
     * @param person the person to retrieve the key for.  CANNOT be NULL.
     * @param activationKey the activation key String representation to retrieve.  CANNOT be NULL.
     * @param lock the value to use to confirm your lock on the system.
     * @return the activation key, or NULL if not found.
     * @throws IllegalArgumentException if the person or activation key is NULL.
     * @throws LockingException if someone already holds the lock on this.
     */
    ActivationKey getActivationKey(Person person, String activationKey, String lock) throws IllegalArgumentException, LockingException;
}