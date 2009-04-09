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

    String getGivenName();

    String getFamilyName();

    Date getDateOfBirth();

    String getIdentifierType();

    String getIdentifierValue();
}
