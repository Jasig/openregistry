package org.openregistry.core.service.authorization;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
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
    public List<Group> findAllGroups() {
        return groupsRepository.findAllGroups();
    }

    @Override
    public List<Authority> findAllAuthorities() {
        return authoritiesRepository.findAllAuthorities();
    }

    @Override
    public User saveUser(User user) {
        return usersRepository.saveUser(user);
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
    public Group saveGroup(Group group) {
        return groupsRepository.saveGroup(group);
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
    public Authority saveAuthority(Authority authority) {
        return authoritiesRepository.saveAuthority(authority);
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
    public Set<Authority> findAuthoritiesOfGroup(Long id) {
        return groupsRepository.findAuthoritiesOfGroup(id);

    }

    @Override
    public Set<Authority> findAuthoritiesOfGroup(Group group) {
        return groupsRepository.findAuthoritiesOfGroup(group);
    }

    @Override
    public Set<Group> findGroupOfUser(User user) {
        return usersRepository.findGroupOfUser(user);

    }

    @Override
    public Set<Group> findGroupOfUser(Long id) {
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
            Set<Group> newGroupsSet = groupsRepository.getGroupListByGroupIds(groupIDs);

            //remove the previous association of groups from the user and assign the new one
            usersRepository.deleteGroupsOfUser(user);
            usersRepository.addGroupsToUser(user, newGroupsSet);

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
        usersRepository.deleteUser(userToBeDeleted);
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
