package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.PersonNotFoundException;

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
     * @throws PersonNotFoundException if the person was not found, or if identifierType or identifierValue is NULL.
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
     * @throws PersonNotFoundException if the specified person is not found.  In this case, that means if a NULL person
     * was passed in.
     * @throws IllegalArgumentException if the activation key does not exist for the person.
     * @throws IllegalStateException if the activation key exists but is not valid.
     */
    void invalidateActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException;

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
     * @throws PersonNotFoundException if the person was not found, or if identifierType or identifierValue is NULL.
     * @throws IllegalArgumentException if the activation key does not exist for the person.
     * @throws IllegalStateException if the activation key exists but is expired.
     *
     */
    void invalidateActivationKey(String identifierType, String identifierValue, String activationKey)  throws PersonNotFoundException, IllegalArgumentException, IllegalStateException;

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
     * @return the activation key.  CANNOT be NULL.
     * @throws PersonNotFoundException if the person was not found, or if identifierType or identifierValue is NULL.
     * @throws IllegalArgumentException if the activation key does not exist for the person.
     */
    ActivationKey getActivationKey(String identifierType, String identifierValue, String activationKey) throws PersonNotFoundException, IllegalArgumentException;

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
     * @return the activation key.  CANNOT be NULL.
     * @throws PersonNotFoundException if the person passed in is NULL.
     * @throws IllegalArgumentException if the activation key does not exist for the person.
     */
    ActivationKey getActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException;
}