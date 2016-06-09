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

import org.jasig.openregistry.test.domain.MockIdentifierType;
import org.jasig.openregistry.test.domain.MockType;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.DataTypes;
import org.openregistry.core.domain.sor.SystemOfRecord;
import org.openregistry.core.repository.ReferenceRepository;

import java.util.regex.Pattern;
import java.util.List;

/**
 * Default implementation of temporary repository.
 */

public final class MockReferenceRepository implements ReferenceRepository {

    IdentifierType rcpIdIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "RCPID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
             throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };

    IdentifierType netIdIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "NETID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };

    IdentifierType ssnIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "SSN";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
             throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };

    IdentifierType emplIdIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "EMPLID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
             throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };
    IdentifierType aNumberIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "ANUMBER";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };
    IdentifierType ruIdIdentifierType =  new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "RUID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
             throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };


    IdentifierType iIdIdentifierType = new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "IID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };

    IdentifierType externalIdentifierType = new IdentifierType() {

        public Long getId() {
            return 1L;
        }

        public String getDescription() {
            return "EXTERNALID";
        }

        public String toString() {
            return String.format("%s", getDescription());
        }

        public String getFormatAsString() {
            return null;
        }

        public Pattern getFormatAsPattern() {
            return Pattern.compile("%s");
        }

        public boolean isPrivate() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isModifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isNotifiable() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }


        public boolean isDeleted() {
            throw new UnsupportedOperationException("This mock does not implement this method yet");
        }

        public String getName() {
            return getDescription();
        }

    };





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

    public List<Region> getRegions() {
        return null;
    }

    public List<Region> getRegionsByCountryCode(final String countryCode) {
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
                    return (type.name().equals(other.getDataType()) && value.equals(other.getDescription()));
                }
            };
        }
        else {
            return new MockType("test", "test");
        }
    }

    @Override
    public Type findValidType(DataTypes type, String value) {
        return "valid".equals(value) ? new MockType("test", "test") : null;
    }

    public List<IdentifierType> getIdentifierTypes() {
        return null;
    }

    public IdentifierType findIdentifierType(final String identifierName) {
        if (identifierName != null) {
            if (identifierName.equals("RCPID"))
                return rcpIdIdentifierType;
            if (identifierName.equals("NETID"))
                return netIdIdentifierType;
            if (identifierName.equals("SSN"))
                return ssnIdentifierType;
            if (identifierName.equals("EMPLID"))
                return emplIdIdentifierType;
            if (identifierName.equals("RUID"))
                return ruIdIdentifierType;
            if (identifierName.equals("IID"))
                return iIdIdentifierType;
            if (identifierName.equals("EXTERNALID"))
                return externalIdentifierType;
            if (identifierName.equals("ANUMBER"))
                return aNumberIdentifierType;


        }

        return new MockIdentifierType("test", true);
    }

    public SystemOfRecord findSystemOfRecord(final String systemOfRecord){
        return new SystemOfRecord() {
            @Override
            public String getSorId() {
                return systemOfRecord;
            }
        };
    }


    public void updateOrganizationalUnit(OrganizationalUnit orgUnit) {}
}