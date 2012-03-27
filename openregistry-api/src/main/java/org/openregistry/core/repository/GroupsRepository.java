package org.openregistry.core.repository;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupsRepository {

    Group findByInternalId(Long id) throws RepositoryAccessException;

    List<Group> findByGroupName(String name) throws RepositoryAccessException;

    List<Group> findAllGroups() throws  RepositoryAccessException;

    public Group saveGroup(final Group group) throws RepositoryAccessException;

    public void deleteGroup(final Group group) throws RepositoryAccessException;

    public void deleteAuthorityOfGroup(final Group group, final Authority authority) throws RepositoryAccessException;

    public Group addAuthorityToGroup(final Group group, final Authority authority) throws RepositoryAccessException;

    public List<Authority> findAuthoritiesOfGroup(final Group group) throws RepositoryAccessException;

    public List<Authority> findAuthoritiesOfGroup(final Long id) throws RepositoryAccessException;

    public List<Group> getGroupListByGroupIds(String[] groupIds) throws RepositoryAccessException;

    public void deleteAuthoritiesOfGroup(Group group) throws RepositoryAccessException;

    public void addAuthoritiesToGroup(Group group, Set<Authority> authorities);


}
