package org.openregistry.core.repository;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthoritiesRepository {

    Authority findByInternalId(Long id) throws RepositoryAccessException;

    List<Authority> findByAuthorityName(String name) throws RepositoryAccessException;

    List<Authority> findAllAuthorities() throws  RepositoryAccessException;

    public Authority saveAuthority(final Authority authority) throws RepositoryAccessException;

    public void deleteAuthority(final Authority authority) throws RepositoryAccessException;

    public Set<Authority> getAuthorityListByAuthorityIds(String[] authorityIDs) throws RepositoryAccessException;

}
