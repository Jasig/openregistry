package org.openregistry.core.service;

import java.util.Date;
import java.io.Serializable;

/**
 * Criteria used to conduct a search for a set of people.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SearchCriteria extends Serializable {

    /**
     * The first or given name of the person.
     *
     * @return the given or first name of the person, or NULL.
     */
    String getGivenName();

    /**
     * The family or last name of the person.
     *
     * @return the family or last name of the person, or NULL.
     */
    String getFamilyName();

    /**
     * The complete date of birth.  Partial date of birth searches will not work.
     * @return the date of birth, or null.
     */
    Date getDateOfBirth();

    /**
     * The identifier type, as specified as one of the legitimate types, or null.
     * @return the identifier type, or null.  This CANNOT be null if the value of the identifier is set.
     */
    String getIdentifierType();

    /**
     * The value of the identifier type.  If the value is set, the type MUST be set.
     * @return the value, or null.
     */
    String getIdentifierValue();
}
