/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.OrganizationalUnit;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.normalization.FirstName;
import org.openregistry.core.domain.normalization.LastName;
import org.springframework.core.style.ToStringCreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class MutableSearchCriteriaImpl implements SearchCriteria {

    @FirstName
    private String givenName;

    @LastName
    private String familyName;

    private Date dateOfBirth;

    private String identifierValue;
    @LastName
    private String name;

    private IdentifierType identifierType;

    private java.util.List <IdentifierType> identifierTypes;

    private String sponsorNetID;

    private OrganizationalUnit organizationalUnit;

    private Date roleExpDate;

    private JpaTypeImpl affiliationType;

    public String getGivenName() {
        return this.givenName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public <IdentifierType>List getIdentifierTypes() {

        return identifierTypes;
    }

    public void setIdentifierTypes(List <IdentifierType> identifierTypes) {
        this.identifierTypes = identifierTypes;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getIdentifierValue() {
        return this.identifierValue;
    }

    public void setGivenName(final String givenName) {
        if (givenName != null)
            this.givenName = givenName.trim();
    }

    public void setFamilyName(final String familyName) {
        if (familyName  != null)
            this.familyName = familyName.trim();
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setIdentifierValue(final String identifierValue) {
        if (identifierValue != null)
         this.identifierValue = identifierValue.trim();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public String getSponsorNetID() {
        return sponsorNetID;
    }

    public void setSponsorNetID(String sponsorNetID) {
        this.sponsorNetID = sponsorNetID;
    }

    public JpaTypeImpl getAffiliationType() {
        return affiliationType;
    }

    public void setAffiliationType(JpaTypeImpl affiliationType) {
        this.affiliationType = affiliationType;
    }

    public Date getRoleExpDate() {
        return roleExpDate;
    }

    public void setRoleExpDate(Date roleExpDate) {
        this.roleExpDate = roleExpDate;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public void setName(final String name) {
        this.familyName = null;
        this.givenName = null;
        this.name = name.trim().replaceAll("( )+", " ");

        if (!this.name.contains(" ") && !this.name.contains(",")) {
            return;
        }

        if (this.name.contains(",")) {
            final String[] split = this.name.split(",", 2);
            this.setGivenName( split[1]);
            this.setFamilyName( split[0]);
            return;
        }

        final String[] split = this.name.split(" ", 2);
        this.setGivenName( split[0]);
        this.setFamilyName( split[1]);
    }

    public String toString() {
        final ToStringCreator toStringCreator = new ToStringCreator(this);
        toStringCreator.append("identifierValue", this.identifierValue);
        toStringCreator.append("dateOfBirth", this.dateOfBirth);
        toStringCreator.append("familyName", this.familyName);
        toStringCreator.append("givenName", this.givenName);
        toStringCreator.append("name", this.name);
        toStringCreator.append("name", this.identifierType);
        return toStringCreator.toString();
    }
}
