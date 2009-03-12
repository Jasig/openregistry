package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.IdentifierType;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaSorPersonSearchImpl implements PersonSearch {

    private SorPerson sorPerson = new JpaSorPersonImpl();

    private Map<IdentifierType, String> identifiersByType = new HashMap<IdentifierType, String>();

    public SorPerson getPerson() {
        return this.sorPerson;
    }

    public void setSorPerson(final SorPerson sorPerson) {
        this.sorPerson = sorPerson;
    }

    public Map<IdentifierType, String> getIdentifiersByType() {
        return this.identifiersByType;
    }
}
