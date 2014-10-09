package org.openregistry.core.service.authorization;

import org.openregistry.core.authorization.*;
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
    List<Group> findGroupsByIds(String[] groupIDs);
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

    public UserGroup[] getDelegatedAdminStatusForGroups(String[] groupIDs,User user);
    public UserGroup getUserGroup(Long userId,Long groupId);
    public void saveUserGroup(UserGroup userGroup);

    //find authorities belonging to a group
    public List<Authority> findAuthoritiesOfGroup(Long id);
    public List<Authority> findAuthoritiesOfGroup(Group group);

    //find Groups belonging to a user
    public List<Group> findGroupOfUser(Long id);
    public List<Group> findGroupOfUser(User user);
    public void addAuthoritiesToGroup(Group group, String[] authorityIDs);

    //find the users belonging to a group
    public List<User> findUsersOfGroup(Long id);
    public List<Group> getGroupListByGroupIds(String[] groupIDs);
    public List<User> findUsersOfGroup(Group group);

    public List<Group> getGroupsForAdminUser();

    public int getAdminLevelForLoggedInUser();

    public int getAdminLevelForUser(User user);

    public int getAdminLevelForUser(String userId);
    public boolean canLoggedInUserDeleteSearchedUser(User searchedUser);

}
