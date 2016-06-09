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

package org.openregistry.core.repository;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SystemOfRecord;

import java.util.List;

/**
 * Temporary interface for retrieving reference data until we work out how to use internal ESS Commons in an
 * open-source application.
 *
 */
public interface ReferenceRepository {

    List<Person> getPeople();

    Person getPersonById(Long id);

    List<OrganizationalUnit> getOrganizationalUnits();

    OrganizationalUnit getOrganizationalUnitById(Long id);

    OrganizationalUnit getOrganizationalUnitByCode(String code);

    List<Campus> getCampuses();

    Campus getCampusById(Long id);

    Country getCountryById(Long id);

    Country getCountryByCode(String code);

    List<Country> getCountries();
    
    List<Region> getRegions();

    public List<Region> getRegionsByCountryCode(final String countryCode);

    Region getRegionByCodeAndCountryId(String code, String countryCode);

    /**
     * Retrieves a region by code or name.  If there's more than one match, this MUST return NULL, indicating no
     * distinct match.
     *
     * @param code the code to check
     * @return the region, or null.
     */
    Region getRegionByCodeOrName(String code);

    SystemOfRecord findSystemOfRecord(String systemOfRecord);

    /**
     * Find a Type based on the original {@link org.openregistry.core.domain.Type.DataTypes} and the value we're
     * looking for.  If the Type doesn't exist, an error should be thrown.
     *
     * @param type the data type (i.e. TERMINATION)
     * @param value the value (i.e. Retired)
     * @return the Type object.
     *
     * @throws IllegalArgumentException if the Type can't be found.
     */
    Type findType(Type.DataTypes type, String value);

    Type findType(Type.DataTypes type, Enum value);

    Type getTypeById(final Long id);

    /**
     * Retrieves the list of Identifier Types.  CANNOT be NULL.  CAN be EMPTY (though that would be weird).
     * 
     * @return the list of identifier types.
     */
    List<IdentifierType> getIdentifierTypes();

    IdentifierType findIdentifierType(String identifierName);

    /**
     * Returns the list of types defined by the type grouping.  Default types are registered on the {@link org.openregistry.core.domain.Type.DataTypes}
     * interface.  For example {@link org.openregistry.core.domain.Type.DataTypes#ADDRESS}.
     *
     * @param type the typing grouping (i.e. ADDRESS, EMAIL, CAMPUS, etc.)
     * @return the list of types.  CANNOT be NULL.  CAN be empty.
     */
    List<Type> getTypesBy(final Type.DataTypes type);

    Type findValidType(Type.DataTypes type, String value);

    void updateOrganizationalUnit(OrganizationalUnit orgUnit);
}
