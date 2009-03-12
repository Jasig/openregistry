package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.IdentifierType;

import java.util.Map;
import java.io.Serializable;

/**
 * TODO: Rename this to SOMETHING else.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PersonSearch extends Serializable {

    SorPerson getPerson();

    Map<IdentifierType, String> getIdentifiersByType();
}
