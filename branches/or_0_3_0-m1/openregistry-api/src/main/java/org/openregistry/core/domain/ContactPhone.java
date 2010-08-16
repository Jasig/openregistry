package org.openregistry.core.domain;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface ContactPhone extends Phone {

    void clear();

    /**
     * Update this phone number with information from the provided phone number.
     * <p>
     * If the phone number is null, this is the same as calling clear.
     *
     * @param phone the phone number to update with.
     */
    void update(Phone phone);
}
