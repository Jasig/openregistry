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

    List<Campus> getCampuses();

    Campus getCampusById(Long id);

    Country getCountryById(Long id);

    Country getCountryByCode(String code);

    List<Country> getCountries();

    List<RoleInfo> getRoleInfos();

    RoleInfo getRoleInfoById(Long id);
    
    RoleInfo getRoleInfoByCode(String code);
    
    RoleInfo getRoleInfoByOrganizationalUnitAndTitle(OrganizationalUnit ou, String title);

    List<Region> getRegions();

    Region getRegionById(Long id);

    /**
     * Find a Type based on the original {@link org.openregistry.core.domain.Type.DataTypes} and the value we're
     * looking for.  If the Type doesn't exist, an error should be thrown.
     *
     * @param type the data type (i.e. TERMINATION)
     * @param value the value (i.e. Retired)
     * @return the Type object.
     *
     * // TODO add an error to this javadoc and implementation
     */
    Type findType(Type.DataTypes type, String value);

    Type getTypeById(final Long id); 

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
}
