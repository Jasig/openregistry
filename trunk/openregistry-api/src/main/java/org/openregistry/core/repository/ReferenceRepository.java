package org.openregistry.core.repository;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.Types;

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

    RoleInfo getRoleInfo(Long id);

    List<Region> getRegions();

    Region getRegionById(Long id);

    Type findType(String type, String value);  

    List<Type> getEmailTypes();

    List<Type> getAddressTypes();

    List<Type> getPhoneTypes();

    List<IdentifierType> getIdentifierTypes();

    IdentifierType findIdentifierType(String identifierName);

    /**
     * Returns the list of types defined by the type grouping.  Default types are registered on the {@link org.openregistry.core.domain.Type.Types}
     * interface.  For example {@link Types.ADDRESS}.
     *
     * @param type the typing grouping (i.e. ADDRESS, EMAIL, CAMPUS, etc.)
     * @return the list of types.  CANNOT be NULL.  CAN be empty.
     */
    List<Type> getTypesBy(final Types type);
}
