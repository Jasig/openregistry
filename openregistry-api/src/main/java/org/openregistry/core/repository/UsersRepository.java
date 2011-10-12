package org.openregistry.core.repository;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.domain.Person;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/7/11
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UsersRepository {

    User findByInternalId(Long id) throws RepositoryAccessException;

    List<User> findByUserName(String name) throws RepositoryAccessException;

    List<User> findAllUsers() throws  RepositoryAccessException;

    public User saveUser(final User user) throws RepositoryAccessException;

    public void deleteUser(final User user) throws RepositoryAccessException;

    public void deleteGroupOfUser(final User user, final Group group) throws RepositoryAccessException;

    public void addGroupToUser(final User user, final Group group) throws RepositoryAccessException;

    public Set<Group> findGroupOfUser(final User user) throws RepositoryAccessException;

    public Set<Group> findGroupOfUser(final Long id) throws RepositoryAccessException;

    public void deleteGroupsOfUser(User user) throws RepositoryAccessException;

    public void addGroupsToUser(User user, Set<Group> groups) throws RepositoryAccessException ;




}
