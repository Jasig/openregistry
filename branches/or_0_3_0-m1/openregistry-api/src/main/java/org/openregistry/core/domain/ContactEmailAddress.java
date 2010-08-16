package org.openregistry.core.domain;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface ContactEmailAddress extends EmailAddress {

    void clear();

    /**
     * Updates the email address.  Calling this with null is the same as calling clear.
     *
     * @param emailAddress the email address to set.
     */
    void update(EmailAddress emailAddress);
}
