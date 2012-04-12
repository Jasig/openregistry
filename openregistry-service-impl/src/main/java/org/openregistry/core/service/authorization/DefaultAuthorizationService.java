package org.openregistry.core.service.authorization;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.AuthorizationException;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.SearchUserCriteria;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("authorizationService")
@Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultAuthorizationService implements AuthorizationService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final UsersRepository usersRepository;
    private final GroupsRepository groupsRepository;
    private final AuthoritiesRepository authoritiesRepository;

     @Inject
    public DefaultAuthorizationService(UsersRepository usersRepository,GroupsRepository groupsRepository,AuthoritiesRepository authoritiesRepository) {
         this.authoritiesRepository = authoritiesRepository;
         this.groupsRepository = groupsRepository;
         this.usersRepository = usersRepository;
     }

    @Override
    public User findUserById(Long id) {
        return usersRepository.findByInternalId(id);
    }

    @Override
    public Group findGroupById(Long id) {
        return groupsRepository.findByInternalId(id);
    }

    @Override
    public Authority findAuthorityById(Long id) {
        return authoritiesRepository.findByInternalId(id);
    }

    @Override
    public List<User> findUserByName(String name) {
        return usersRepository.findByUserName(name);
    }

    @Override
    public List<User> findUserByCriteria(SearchUserCriteria searchCriteria) {
        return this.findUserByName(searchCriteria.getUserName());
    }

    @Override
    public List<Group> findGroupByName(String name) {
        return groupsRepository.findByGroupName(name);
    }

    @Override
    public List<Authority> findAuthorityByName(String name) {
        return authoritiesRepository.findByAuthorityName(name);
    }

    @Override
    public List<User> findAllUsers() {
        return usersRepository.findAllUsers();
    }
    @Override
    public List<User> findAllUsersSortedById(){
        List<User> allUsers = findAllUsers();
        Collections.sort(allUsers, new Comparator<User>() {
            public int compare(User user1, User user2) {
                return user1.getId().compareTo(user2.getId()); // Compare by Id
            }
        });
        return allUsers;
    }

    @Override
    public List<Group> findAllGroups() {
        return groupsRepository.findAllGroups();
    }

    @Override
    public List<Group> findAllGroupsSortedById(){
        List<Group> allGroups = findAllGroups();
        Collections.sort(allGroups, new Comparator<Group>() {
            public int compare(Group group1, Group group2) {
                return group1.getId().compareTo(group2.getId()); // Compare by Id
            }
        });
        return allGroups;
    }

    @Override
    public List<Authority> findAllAuthorities() {
        return authoritiesRepository.findAllAuthorities();
    }
    @Override
    public List<Authority> findAllAuthoritiesSortedById(){
        List<Authority> allAuthorities = findAllAuthorities();
        Collections.sort(allAuthorities, new Comparator<Authority>() {
            public int compare(Authority authority1, Authority authority2) {
                return authority1.getId().compareTo(authority2.getId()); // Compare by Id
            }
        });
        return allAuthorities;
    }


    @Override
    public User saveUser(User user) throws AuthorizationException,Exception{
        //return usersRepository.saveUser(user);

        User savedUser = null;
        if(null != user.getId() && user.getId().longValue() >=0){
            //this means that the user being save is being edited, otherwise the ID would not be available
            savedUser = usersRepository.saveUser(user);
        }else{
            //this is a user that is being saved the  first time
            //here check whether a user with the same name already exists?
            //if it does, there should be an exception
            List<User> listUserAvailable = usersRepository.findByUserName(user.getUserName());
            if(null!= listUserAvailable && listUserAvailable.size() > 0){
                throw new AuthorizationException("User name already exists: " + user.getUserName());
            }else{
                savedUser = usersRepository.saveUser(user);
            }
        }
        return savedUser;
    }

    @Override
    public void deleteUser(User user) {
        usersRepository.deleteUser(user);
    }

    @Override
    public void deleteGroupOfUser(User user, Group group) {
        //TODO- Check if it is right behavior
        usersRepository.deleteGroupOfUser(user,group);
    }

    @Override
    public void updateGroupOfUser(User user, Group group) {
        //TODO- Check if it is right behavior
        //usersRepository.updateGroupOfUser(user,group);
    }

    @Override
    public Group saveGroup(Group group) throws AuthorizationException,Exception {
        //return groupsRepository.saveGroup(group);
        Group savedGroup = null;
        if(null != group.getId() && group.getId().longValue() >=0){
            //this means that the group being save is being edited, otherwise the ID would not be available
            savedGroup = groupsRepository.saveGroup(group);
        }else{
            //this is a group that is being saved the first time
            //here check whether a group with the same name already exists?
            //if it does, there should be an exception
            List<Group> listGroupAvailable = groupsRepository.findByGroupName(group.getGroupName());
            if(null!= listGroupAvailable && listGroupAvailable.size() > 0){
                throw new AuthorizationException("Group name already exists: " + group.getGroupName());
            }else{
                savedGroup = groupsRepository.saveGroup(group);
            }
        }
        return savedGroup;
    }

    @Override
    public void deleteGroup(Group group) {
        groupsRepository.deleteGroup(group);
    }

    @Override
    public void deleteAuthorityOfGroup(Group group, Authority authority) {
        groupsRepository.deleteAuthorityOfGroup(group,authority);
    }


//    @Override
//    public void updateAuthorityOfGroup(Group group, Authority authority) {
//        //TODO- Check if it is right behavior
//        groupsRepository.addAuthorityToGroup(group,authority);
//    }

    @Override
    public Authority saveAuthority(Authority authority) throws AuthorizationException,Exception{
        //return authoritiesRepository.saveAuthority(authority);
        Authority savedAuthority = null;
        if(null != authority.getId() && authority.getId().longValue() >=0){
            //this means that the group being save is being edited, otherwise the ID would not be available
            savedAuthority= authoritiesRepository.saveAuthority(authority);
        }else{
            //this is an authority that is being saved the first time
            //here check whether a group with the same name already exists?
            //if it does, there should be an exception
            List<Authority> listAuthorityAvailable = authoritiesRepository.findByAuthorityName(authority.getAuthorityName());
            if(null!= listAuthorityAvailable && listAuthorityAvailable.size() > 0){
                throw new AuthorizationException("Authority name already exists: " + authority.getAuthorityName());
            }else{
                savedAuthority = authoritiesRepository.saveAuthority(authority);
            }
        }
        return savedAuthority;

    }

    @Override
    public void deleteAuthority(Authority authority) {
        authoritiesRepository.deleteAuthority(authority);
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupToUser(User user, Group group) {
        usersRepository.addGroupToUser(user,group);
    }


    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public Group addAuthorityToGroup(Group group, Authority authority) {
        return groupsRepository.addAuthorityToGroup(group, authority);
    }

    @Override
    public List<Authority> findAuthoritiesOfGroup(Long id) {
        return groupsRepository.findAuthoritiesOfGroup(id);

    }

    @Override
    public List<Authority> findAuthoritiesOfGroup(Group group) {
        return groupsRepository.findAuthoritiesOfGroup(group);
    }

    @Override
    public List<Group> findGroupOfUser(User user) {
        return usersRepository.findGroupOfUser(user);

    }

    @Override
    public List<Group> findGroupOfUser(Long id) {
        return usersRepository.findGroupOfUser(id);
    }

    @Override
    public void addGroupsToUser(User user, String[] groupIDs) {
//        if(null!=user && null!= groupIDs && groupIDs.length > 0){
//            System.out.println("User ID:"  + user.getId());
//
//            for(int i=0;i<groupIDs.length;i++){
//                String strGroup = groupIDs[i];
//                System.out.println("OBJ1:"  + strGroup);
//            }
            //Find the list of Groups associated with the GroupIDs (from groupRepository)
            List<Group> newGroupsList = groupsRepository.getGroupListByGroupIds(groupIDs);

            //remove the previous association of groups from the user and assign the new one
            usersRepository.deleteGroupsOfUser(user);
            usersRepository.addGroupsToUser(user, newGroupsList);

//        }
    }

    @Override
    public void addAuthoritiesToGroup(Group group, String[] authorityIDs) {
//            //Find the list of Groups associated with the GroupIDs (from groupRepository)
            Set<Authority> newAuthoritiesSet = authoritiesRepository.getAuthorityListByAuthorityIds(authorityIDs);
//            //remove the previous association of groups from the user and assign the new one
            groupsRepository.deleteAuthoritiesOfGroup(group);
            groupsRepository.addAuthoritiesToGroup(group, newAuthoritiesSet);


    }

    @Override
    public void deleteUser(Long id) {
        User userToBeDeleted = usersRepository.findByInternalId(id);
        if(null!=userToBeDeleted){
            usersRepository.deleteUser(userToBeDeleted);
        }
    }

    @Override
    public void deleteGroup(Long id) {
        Group groupToBeDeleted = groupsRepository.findByInternalId(id);
        groupsRepository.deleteGroup(groupToBeDeleted);
    }

    @Override
    public void deleteAuthority(Long id) {
        Authority authorityToBeDeleted = authoritiesRepository.findByInternalId(id);
        authoritiesRepository.deleteAuthority(authorityToBeDeleted);
    }
}
