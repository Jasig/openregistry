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

package org.jasig.openregistry.test.repository;

import org.jasig.openregistry.test.domain.MockType;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.DataTypes;
import org.openregistry.core.domain.sor.SystemOfRecord;
import org.openregistry.core.repository.ReferenceRepository;

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

    public OrganizationalUnit getOrganizationalUnitByCode(String code) {
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

    public Country getCountryByCode(String code) {
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

    public RoleInfo getRoleInfoByCode(final String systemOfRecordId, final String code) {
        return new RoleInfo() {
            public String getTitle() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public OrganizationalUnit getOrganizationalUnit() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Campus getCampus() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Type getAffiliationType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setCode(String code) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getDisplayableName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Long getId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public SystemOfRecord getSystemOfRecord() {
                return null;
            }
        };
    }

    public List<Region> getRegions() {
        return null;
    }

    public RoleInfo getRoleInfoByOrganizationalUnitAndTitle(final OrganizationalUnit ou, final String title) {
        return null;
    }

    public Region getRegionByCodeAndCountryId(final String code, final String countryCode) {
        return null;
    }

    @Override
    public Region getRegionByCodeOrName(final String code) {
        return null;
    }

    public Type getTypeById(final Long id) {
        return null;
    }

    public List<Type> getTypesBy(final DataTypes type) {
        return null;
    }

    public Type findType(DataTypes type, Enum value) {
        return findType(type, value.name());
    }

    public Type findType(final DataTypes type, final String value) {
        if (type != null && value != null) {
            return new Type() {

                public Long getId() {
                    return 1L;
                }

                public String getDataType() {
                    return type.name();
                }

                public String getDescription() {
                    return value;
                }

                public String toString() {
                    return String.format("%s - %s", getDataType(), getDescription());
                }

                @Override
                public boolean isSameAs(Type other) {
                    throw new UnsupportedOperationException("This mock does not implement this method yet");
                }
            };
        }
        else {
            return new MockType("test", "test");
        }
    }

    @Override
    public boolean findValidType(DataTypes type, String value) {
        throw new UnsupportedOperationException("This mock does not implement this method yet");
    }

    public List<IdentifierType> getIdentifierTypes() {
        return null;
    }

    public IdentifierType findIdentifierType(final String identifierName) {
        return null;
    }
}