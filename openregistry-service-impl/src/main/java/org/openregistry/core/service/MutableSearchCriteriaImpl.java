package org.openregistry.core.service;

import org.openregistry.core.service.SearchCriteria;
import org.springframework.core.style.ToStringCreator;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Apr 9, 2009
 * Time: 4:27:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MutableSearchCriteriaImpl implements SearchCriteria {

    private String givenName;

    private String familyName;

    private Date dateOfBirth;

    private String identifierType;

    private String identifierValue;

    public String getGivenName() {
        return this.givenName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public String getIdentifierValue() {
        return this.identifierValue;
    }

    public void setGivenName(final String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(final String familyName) {
        this.familyName = familyName;
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setIdentifierType(final String identifierType) {
        this.identifierType = identifierType;
    }

    public void setIdentifierValue(final String identifierValue) {
        this.identifierValue = identifierValue;
    }

    public String toString() {
        final ToStringCreator toStringCreator = new ToStringCreator(this);
        toStringCreator.append("identifierType", this.identifierType);
        toStringCreator.append("identifierValue", this.identifierValue);
        toStringCreator.append("dateOfBirth", this.dateOfBirth);
        toStringCreator.append("familyName", this.familyName);
        toStringCreator.append("givenName", this.givenName);
        return toStringCreator.toString();
    }
}
