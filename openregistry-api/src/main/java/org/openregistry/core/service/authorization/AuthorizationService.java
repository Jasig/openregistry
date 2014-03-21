package org.openregistry.core.service.authorization;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.AuthorizationException;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.service.SearchUserCriteria;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
    public interface AuthorizationService {
    User findUserById(Long id);
    Group findGroupById(Long id);
    Authority findAuthorityById(Long id);

    List<User> findUserByExactName(String name);
    List<User> findUserByName(String name);
    List<User> findUserByCriteria(SearchUserCriteria searchCriteria);
    List<Group> findGroupByName(String name);
    List<Authority> findAuthorityByName(String name);

    List<User> findAllUsers();
    List<User> findAllUsersSortedById();
    List<Group> findAllGroups();
    List<Group> findAllGroupsSortedById();
    List<Authority> findAllAuthorities();
    List<Authority> findAllAuthoritiesSortedById();

    public User saveUser(final User user) throws AuthorizationException,Exception;
    public User addUser(final String netId) throws AuthorizationException,Exception;
    public Long addUserAndGetUserId(final String netId) throws AuthorizationException,Exception;

    public void deleteUser(final User user);
    public void deleteUser(final Long id);

    public void deleteGroupOfUser(final User user, final Group group);
    public void updateGroupOfUser(final User user, final Group group);

    public void addGroupToUser(final User user, final Group group);
    public void addGroupsToUser(final User user, final String[] groupIDs);

    public User saveUserAndAddGroupsToUser(final User user, final String[] groupIDs) throws AuthorizationException,Exception;

    public Group saveGroup(final Group group) throws AuthorizationException,Exception;
    public void deleteGroup(final Group group);
    public void deleteGroup(final Long id);

    public void deleteAuthorityOfGroup(final Group group, final Authority authority);
    //public void updateAuthorityOfGroup(final Group group, final Authority authority);

    public Group addAuthorityToGroup(final Group group,final Authority authority);

    public Authority saveAuthority(final Authority authority) throws AuthorizationException,Exception;
    public void deleteAuthority(final Authority authority);
    public void deleteAuthority(final Long id);

    //find authorities belonging to a group
    public List<Authority> findAuthoritiesOfGroup(Long id);
    public List<Authority> findAuthoritiesOfGroup(Group group);

    //find Groups belonging to a user
    public List<Group> findGroupOfUser(Long id);
    public List<Group> findGroupOfUser(User user);
    public void addAuthoritiesToGroup(Group group, String[] authorityIDs);

    //find the users belonging to a group
    public List<User> findUsersOfGroup(Long id);
    public List<User> findUsersOfGroup(Group group);



}
