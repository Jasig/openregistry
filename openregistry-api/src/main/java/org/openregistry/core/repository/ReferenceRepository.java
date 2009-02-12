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

    List<Department> getDepartments();

    Department getDepartmentById(Long id);

    List<Campus> getCampuses();

    Campus getCampusById(Long id);

    Country getCountryById(Long id);

    List<Country> getCountries();

    List<RoleInfo> getRoleInfos();

    RoleInfo getRoleInfo(Long id);

    List<Region> getRegions();

    List<Type> getPersonStatus();

    Type findType(String type, String value);

    List<Type> getEmailTypes();

    List<Type> getAddressTypes();

    List<Type> getPhoneTypes();
}
