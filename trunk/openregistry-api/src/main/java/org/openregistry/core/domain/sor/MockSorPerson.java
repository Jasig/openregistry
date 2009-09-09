/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.internal.Entity;

import java.util.*;

/**
 * Implementation of the SoR Person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

public class MockSorPerson extends Entity implements SorPerson {

    private String sorId;

    private String sourceSorIdentifier;

    private Long personId;

    private Date dateOfBirth;

    private String gender;

    private List<Name> names = new ArrayList<Name>();

    private String ssn;

    private List<SorRole> roles = new ArrayList<SorRole>();

    public List<SorRole> getRoles(){
        return this.roles;
    }

    public String getSsn() {
        return this.ssn;
    }

    public void setSsn(final String ssn) {
        this.ssn = ssn;
    }

    public Long getId() {
        return 1L;
    }

    public String getSorId() {
        return this.sorId;
    }

    public String getSourceSorIdentifier() {
        return this.sourceSorIdentifier;
    }

    public void setSourceSorIdentifier(final String sorIdentifier) {
        this.sourceSorIdentifier = sorIdentifier;
    }

    public List<Name> getNames() {
        return this.names;
    }

    public void setNames(List<Name> names){
        this.names = names;
    }

    public void setSorId(final String id) {
        this.sorId = id;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final Date date) {
        this.dateOfBirth = date;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Name addName() {
        final MockSorName jpaSorName = new MockSorName(this);
        this.names.add(jpaSorName);
        return jpaSorName;
    }

    public void addName(Name name) {
        this.names.add(name);
        ((MockSorName)name).moveToPerson(this);
    }

    public synchronized Name findNameByNameId(final Long id) {
        Name nameToFind = null;
        for (final Name name : this.names) {
            final Long nameId = name.getId();
            if (nameId != null && nameId.equals(id)) {
                nameToFind = name;
                break;
            }
        }
        return nameToFind;
    }

    public synchronized void removeName(Name name) {
        this.names.remove(name);
    }

    public synchronized void removeAllNames(){
        this.names.clear();
    }

    public String getFormattedNameAndID(){
        return null;
    }

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public SorRole addRole(final RoleInfo roleInfo) {
        return null;
    }

    public void addRole(final SorRole role){
    }

    public synchronized void removeRole(final SorRole role){
        this.roles.remove(role);
    }

    public synchronized void removeAllRoles(){
        this.roles.clear();
    }

    public SorRole pickOutRole(String code) {
        return null;
    }

}