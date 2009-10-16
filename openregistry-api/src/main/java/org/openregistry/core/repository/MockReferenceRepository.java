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
package org.openregistry.core.repository;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.DataTypes;

import java.util.List;

/**
 * Default implementation of temporary repository.
 */

public final class MockReferenceRepository implements ReferenceRepository {

    public List<Person> getPeople() {
        return null;
    }

    public Person getPersonById(final Long id) {
        return null;
    }

    public List<OrganizationalUnit> getOrganizationalUnits() {
        return null;
    }

    public OrganizationalUnit getOrganizationalUnitById(final Long id) {
        return null;
    }

    public List<Campus> getCampuses() {
        return null;
    }

    public Campus getCampusById(final Long id) {
        return null;
    }

    public Country getCountryById(final Long id) {
        return null;
    }

    public List<Country> getCountries() {
        return null;
    }

    public List<RoleInfo> getRoleInfos() {
        return null;
    }

    public RoleInfo getRoleInfoById(final Long id) {
        return null;
    }

    public RoleInfo getRoleInfoByCode(final String code) {
    	return null;
    }

    public RoleInfo getRoleInfoByOrganizationalUnitAndTitle(final OrganizationalUnit ou, final String title) {
       	return null;
    }

    public List<Region> getRegions() {
        return null;
    }

    public Region getRegionById(final Long id) {
        return null;
    }

    public Type getTypeById(final Long id) {
        return null;
    }

    public List<Type> getTypesBy(final DataTypes type) {
        return null;
    }

    public Type findType(final DataTypes type, final String value) {
        return new Type(){

			public Long getId() {
				return 1L;
			}

			public String getDataType() {
				return type.name();
			}

			public String getDescription() {
				return value;
			}

			public String toString(){
				return String.format("%s - %s", getDataType(), getDescription());
			}
		};
    }

    public List<IdentifierType> getIdentifierTypes(){
        return null;
    }

    public IdentifierType findIdentifierType(final String identifierName){
        return null;
    }
}