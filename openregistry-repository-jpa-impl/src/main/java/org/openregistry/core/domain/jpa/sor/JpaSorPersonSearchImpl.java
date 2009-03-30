package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.IdentifierType;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.core.ValidateDefinition;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition
public class JpaSorPersonSearchImpl implements PersonSearch {

    private SorPerson sorPerson = new JpaSorPersonImpl();

    private Map<IdentifierType, String> identifiersByType = new HashMap<IdentifierType, String>();

    private String emailAddress;

    private String phoneNumber;

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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
