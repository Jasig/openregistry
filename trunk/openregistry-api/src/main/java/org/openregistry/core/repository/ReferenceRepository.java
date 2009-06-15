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
     */
    Type findType(Type.DataTypes type, String value);

    Type getTypesById(final Long id); 

    List<Type> getEmailTypes();

    List<Type> getAddressTypes();

    List<Type> getPhoneTypes();

    List<Type> getAffiliationTypes();

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
