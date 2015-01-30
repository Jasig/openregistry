package org.openregistry.core.service.authorization;

import org.openregistry.core.authorization.*;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserImpl;
import org.openregistry.core.authorization.jpa.UserGroupId;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.SearchUserCriteria;
import org.openregistry.core.service.authorization.group.AdminGroupInfo;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

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
    private static final String SUPER_ADMIN_GROUP_NAME = "AuthAdmin";
    private static final String SUPER_ADMIN_GROUP_NAME1 = "AUTH_ADMIN";
    private static final String DELEGATED_ADMIN_GROUP_NAME = "AuthAdminMidLevel";

    private static final boolean[][] decisionTable ={
            {false,false,false,false},
            {true,true,true,true},
            {true,false,false,true},
            {true,false,false,false}
    };

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
    public List<Group> findGroupsByIds(String[] groupIDs) {
        List<Group> listGroups = new ArrayList<Group>();
        for(String groupId : groupIDs){
            Group group = findGroupById(new Long(groupId));
            listGroups.add(group);
        }
        return listGroups;
    }

    @Override
    public Authority findAuthorityById(Long id) {
        return authoritiesRepository.findByInternalId(id);
    }

    @Override
    public List<User> findUserByExactName(String name) {
        return usersRepository.findByUserExactName(name);
    }

    @Override
    public List<User> findUserByCriteria(SearchUserCriteria searchCriteria) {
        return this.findUserByExactName(searchCriteria.getUserName());
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
    public User addUser(final String netId) throws AuthorizationException,Exception{
        User savedUser = null;

        List<User> lstUser = null;
        try{
        lstUser = usersRepository.findByUserExactName(netId);
        }catch(RepositoryAccessException rae){
            logger.info("could not find user with netId" + netId);
        }catch(Exception ex){
            logger.info("could not find user with netId" + netId);
        }

        if(null==lstUser || lstUser.size() == 0){
            User user = new JpaUserImpl();
            user.setUserName(netId);
            user.setEnabled(true);
            user.setDescription(null);
            user.setPassword("NOT_USED");
            savedUser = usersRepository.saveUser(user);
        }else{
            savedUser = lstUser.get(0);
        }

        return savedUser;
    }

    @Override
    public Long addUserAndGetUserId(final String netId) throws AuthorizationException,Exception{
        User user = null;
        user = addUser(netId);
        if (null == user){
            return new Long(0);
        }
        return user.getId();
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
            List<User> listUserAvailable = usersRepository.findByUserExactName(user.getUserName());
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
        if(null!= user && canLoggedInUserDeleteSearchedUser(user)){
            usersRepository.deleteUser(user);
        }
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
    public List<User> findUsersOfGroup(Group group) {
        return usersRepository.findUsersOfGroup(group);

    }

    @Override
    public List<User> findUsersOfGroup(Long id) {
        return usersRepository.findUsersOfGroup(id);
    }

    public List<Group> getGroupListByGroupIds(String[] groupIDs){
        return groupsRepository.getGroupListByGroupIds(groupIDs);
    }

    @Override
    public void addGroupsToUser(User user, String[] groupIDs) {

         Set<UserGroup> lstUserGroup = user.getGroups();
        //remove only those groups which were not passed in
        //this means if a group was assigned to this user previously, then do not delete that group
        List<Group> lstGroupsPassed = groupsRepository.getGroupListByGroupIds(groupIDs);
        //check if the list of groups to be saved contains group assigned to the user already and it is a group admin too (delegated or permanent)
        int listContainsLevel2OrLevel3Groups = 0;
        if(null!=lstGroupsPassed){
            listContainsLevel2OrLevel3Groups = groupContainsLevel2OrLevel3(user,lstGroupsPassed);
        }

        List<Long> listOfGroupIdsToBeDeleted = new ArrayList<Long>();
        //List<Group> lstGroupsNotAssignedToUser = new ArrayList<Group>();
        if(null!=lstUserGroup){


            for(UserGroup userGroup: lstUserGroup){
                boolean groupFound = false;
                for(Group groupPassed: lstGroupsPassed){
                    if(userGroup.getGroup().getId().longValue() == groupPassed.getId().longValue()){
                        groupFound=true;
                        synchronized (lstGroupsPassed){
                            lstGroupsPassed.remove(groupPassed);
                        }
                        break;
                    }
                }
                //if this group is not found in both lists, it is time to unassociate it with the user
                if(groupFound==false){

                    //deleteGroupOfUser() of UserRepository performs the following three steps
                    //1- remove the usergroup from the user
                    //2- remove the usergroup from table
                    //3- remove the user group from the group

                     //usersRepository.deleteGroupOfUser(user,userGroup);
                    //only remove Delegated Admin group if the groups passed contain a group which this user is not an admin of
                    if ((listContainsLevel2OrLevel3Groups == 2 || listContainsLevel2OrLevel3Groups==3) && userGroup.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                         //skip this group and do not include it in the list to be deleted
                    }else{
                        listOfGroupIdsToBeDeleted.add(new Long(userGroup.getGroup().getId().longValue()));
                    }

                }else{
                    groupFound = false;
                }
            }

            usersRepository.deleteGroupOfUser(user,listOfGroupIdsToBeDeleted);

            //Now lstGroupsPassed contains only those groups are not assigned to this user so far
            //therefore, these groups can be added without any issue
            if(null!=lstGroupsPassed && lstGroupsPassed.size()>0){
                usersRepository.addGroupsToUser(user, lstGroupsPassed);
            }
        }else{
            //following code takes care of the scenario when a user is being assigned groups for the first time

            usersRepository.addGroupsToUser(user, lstGroupsPassed);
        }
    }

    @Override
    public User saveUserAndAddGroupsToUser(final User user, final String[] groupIDs) throws AuthorizationException,Exception{
        //first save the new user
        User savedUser = null;
        try{
            savedUser = saveUser(user);
        }catch (AuthorizationException ae){
            throw ae;
        }catch (Exception ex){
            throw ex;
        }

        //second add the groups to the user

        addGroupsToUser(savedUser,groupIDs);

        return savedUser;

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
        //we need to make sure that the logged in user has the rights to delete this user

        User userToBeDeleted = usersRepository.findByInternalId(id);
        if(null!=userToBeDeleted){
            if(canLoggedInUserDeleteSearchedUser(userToBeDeleted)){
                usersRepository.deleteUser(userToBeDeleted);
            }
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

    public UserGroup[] getDelegatedAdminStatusForGroups(String[] groupIDs,User user){

        List<UserGroup> listUserGroups = new ArrayList<UserGroup>();
        for(String groupId : groupIDs){
            Group group = findGroupById(new Long(groupId));
            UserGroup ug = group.getUserGroupForUser(user);
            if (null!= ug){
                listUserGroups.add(ug);
            }else{
                UserGroup ugNew = new JpaUserGroupImpl(user,group,null,null,null,null);
                listUserGroups.add(ugNew);
            }
        }

        UserGroup[] ugs = listUserGroups.toArray(new UserGroup[listUserGroups.size()]);
        return ugs;
    }

    public UserGroup getUserGroup(Long userId,Long groupId){
        return usersRepository.getUserGroup(userId,groupId);
    }

    public void saveUserGroup(UserGroup userGroup){
        usersRepository.saveUserGroup(userGroup);
    }

    public User getLoggedInUser(){
        //get the net id of the logged in user
        String netId =  SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        //find the user associated with this NetID
        List<User> lstUser = usersRepository.findByUserExactName(netId);
        if(null!= lstUser){
            user = lstUser.get(0);
        }

        return user;
    }


    public List<Group> getGroupsForAdminUser(){
        //get the net id of the logged in user
        String netId =  SecurityContextHolder.getContext().getAuthentication().getName();

        List<Group> lstGroupsReturned = new ArrayList<Group>();

        //find the user associated with this NetID
        List<User> lstUser = usersRepository.findByUserExactName(netId);
        boolean delegatedAdmin = false;
        boolean superAdmin = false;
        if(null!= lstUser){
            User user = lstUser.get(0);
            if(null!=user){
                //find the groups of the user
                Set<UserGroup> stUserGroup = user.getGroups();
                //iterate the groups and see if the user has admin group
                if (null!= stUserGroup){
                    for(UserGroup ug : stUserGroup){
                        if(ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME) || ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME1)){
                            //This is a super admin therefore break the loop as we can return all groups
                            superAdmin = true;
                            break;
                        }else if(ug.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                            //dont break the loop as a user can be a delegated and super admin both
                            // but set delegated admin flag as true only if delegated admin roles are active
                            delegatedAdmin = true;
                        }
                    }
                }
            }
        }

        if(superAdmin){
            //return all groups
            lstGroupsReturned = groupsRepository.findAllGroups();

        }else if(delegatedAdmin){
            //return the groups this person is delegated admin of
            /**/
            if(null!= lstUser){
                User user = lstUser.get(0);
                if(null!=user){
                    //find the groups of the user
                    Set<UserGroup> stUserGroup = user.getGroups();
                    //iterate the groups and see if the user has admin group
                    if (null!= stUserGroup){
                        for(UserGroup ug : stUserGroup){
                            Date startDate = ug.getStartDate();
                            Date endDate = ug.getEndDate();

                            if(null != ug.getPermanentAdmin() && ug.getPermanentAdmin().longValue()==1){
                                //this is a permanent admin
                                //if the start and end dates are not provided it is OK for permanent admin
                                if(null==ug.getStartDate() && null==ug.getEndDate()){
                                    lstGroupsReturned.add(ug.getGroup());
                                }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                    //check if the role is still open
                                    if(null==ug.getEndDate()){
                                        lstGroupsReturned.add(ug.getGroup());
                                    }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                        lstGroupsReturned.add(ug.getGroup());
                                    }
                                }
                            }

                            //check if the user is a delegated admin
                            //Note: Assumption is that a user cannot be a delegated and permanent admin for the same group at one same time
                            if(null != ug.getDelegatedAdmin() && ug.getDelegatedAdmin().longValue()==1){
                                //this is a delegated / third level admin
                                //if the start and end dates are not provided it is OK for permanent admin
                                if(null==ug.getStartDate() && null==ug.getEndDate()){
                                    lstGroupsReturned.add(ug.getGroup());
                                }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                    //check if the role is still open
                                    if(null==ug.getEndDate()){
                                        lstGroupsReturned.add(ug.getGroup());
                                    }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                        lstGroupsReturned.add(ug.getGroup());
                                    }
                                }
                            }

                        }
                    }
                }
            }
            /**/

        }//end of delegated admin

        return lstGroupsReturned;
    }

    /**/
    public List<AdminGroupInfo> getAdminGroupInfoForUser(User user){
        //get the net id of the logged in user
        //String netId =  SecurityContextHolder.getContext().getAuthentication().getName();
        List<AdminGroupInfo> lstGroupsReturned = new ArrayList<AdminGroupInfo>();

        //return all groups
        Set<UserGroup> lstTempGroups = user.getGroups();
        if(null!= lstTempGroups){
            for(UserGroup userGroup : lstTempGroups){
                AdminGroupInfo adminGroupInfo = new AdminGroupInfo();
                //keep the admin level to 0 and ActiveAdminGroup to false unless it is set explicitly in the following code
                adminGroupInfo.setActiveGroupAdmin(false);
                adminGroupInfo.setAdminLevel(0);

                if(userGroup.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME)){
                    //super user = 1
                    adminGroupInfo.setAdminLevel(1);
                    adminGroupInfo.setActiveGroupAdmin(true);
                    //set the group
                    adminGroupInfo.setGroup(userGroup.getGroup());
                    //lstGroupsReturned.add(adminGroupInfo);

                }else if(userGroup.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                    //skip DELEGATED_ADMIN_GROUP_NAME role as its not needed for any user operations
                    //rather it just contains the authority to show GUI components

                    //also do not add this group to  lstGroupsReturned
                    //break;
                }else if(!userGroup.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                    //check the permanent admin and delegated statuses and also their dates
                    /**/
                    if(null != userGroup.getPermanentAdmin() && userGroup.getPermanentAdmin().longValue()==1){
                        //this is a permanent admin
                        //if the start and end dates are not provided it is OK for permanent admin
                        if(null==userGroup.getStartDate() && null==userGroup.getEndDate()){
                            adminGroupInfo.setActiveGroupAdmin(true);
                            //it is a delegated admin
                            adminGroupInfo.setAdminLevel(2);
                            adminGroupInfo.setGroup(userGroup.getGroup());
                            //lstGroupsReturned.add(adminGroupInfo);
                        }else if(null!=userGroup.getStartDate() && new Date().after(userGroup.getStartDate())){
                            //check if the role is still open
                            if(null==userGroup.getEndDate()){
                                adminGroupInfo.setActiveGroupAdmin(true);
                                //it is a delegated admin
                                adminGroupInfo.setAdminLevel(2);
                                adminGroupInfo.setGroup(userGroup.getGroup());
                                //lstGroupsReturned.add(adminGroupInfo);
                            }else if(null!=userGroup.getEndDate() && new Date().before(userGroup.getEndDate())){
                                adminGroupInfo.setActiveGroupAdmin(true);
                                //it is a delegated admin
                                adminGroupInfo.setAdminLevel(2);
                                adminGroupInfo.setGroup(userGroup.getGroup());
                                //lstGroupsReturned.add(adminGroupInfo);
                            }
                        }
                    }

                    //check if the user is a delegated admin
                    //Note: Assumption is that a user cannot be a delegated and permanent admin for the same group at one same time
                    if(null != userGroup.getDelegatedAdmin() && userGroup.getDelegatedAdmin().longValue()==1){
                        //this is a delegated / third level admin
                        //if the start and end dates are not provided it is OK for permanent admin
                        if(null==userGroup.getStartDate() && null==userGroup.getEndDate()){
                            adminGroupInfo.setActiveGroupAdmin(true);
                            //it is a delegated admin
                            adminGroupInfo.setAdminLevel(3);
                            adminGroupInfo.setGroup(userGroup.getGroup());
                            //lstGroupsReturned.add(adminGroupInfo);
                        }else if(null!=userGroup.getStartDate() && new Date().after(userGroup.getStartDate())){
                            //check if the role is still open
                            if(null==userGroup.getEndDate()){
                                adminGroupInfo.setActiveGroupAdmin(true);
                                //it is a delegated admin
                                adminGroupInfo.setAdminLevel(3);
                                adminGroupInfo.setGroup(userGroup.getGroup());
                                //lstGroupsReturned.add(adminGroupInfo);
                            }else if(null!=userGroup.getEndDate() && new Date().before(userGroup.getEndDate())){
                                adminGroupInfo.setActiveGroupAdmin(true);
                                //it is a delegated admin
                                adminGroupInfo.setAdminLevel(3);
                                adminGroupInfo.setGroup(userGroup.getGroup());
                                //lstGroupsReturned.add(adminGroupInfo);
                            }
                        }
                    }
                }

                //check if the group has been assigned or not
                //if it is not assigned then it means that user is not an admin user
                if(null==adminGroupInfo.getGroup()){
                    adminGroupInfo.setGroup(userGroup.getGroup());
                }

                //we are skipping the delegated admin role from this list
                if(!userGroup.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                    lstGroupsReturned.add(adminGroupInfo);
                }

            }
        }
        return lstGroupsReturned;
    }
    /**/

    public int groupContainsLevel2OrLevel3(User user, List<Group> lstGroupsPassed){
        int returnValue = 0;

        boolean permanentAdmin =false;
        boolean delegatedAdmin = false;

        for(Group group : lstGroupsPassed){
            Set<UserGroup> setUserGroup = group.getUsers();
            if(null!=setUserGroup){
                for(UserGroup ug: setUserGroup){
                    //this is the UserGroup for the right user and group
                    if(ug.getUser().getId().longValue() == user.getId().longValue()){
                        //check if the user is a second or third level admin for this group
                        /**/
                        if(null!=ug.getPermanentAdmin() && ug.getPermanentAdmin().longValue()==1){
                            //this is a permanent admin
                            //now verify the dates this admin is active
                            if(null==ug.getStartDate() && null==ug.getEndDate()){
                                permanentAdmin = true;
                                break;
                            }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                //check if the end date is after today
                                if(null==ug.getEndDate()){
                                    //since the end date is null, it is a mid level admin
                                    permanentAdmin=true;
                                    break;
                                }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                    //role end date is after today therefore it is a legitimate mid level admin
                                    permanentAdmin=true;
                                    break;
                                }
                            }

                        }else if(null!=ug.getDelegatedAdmin() && ug.getDelegatedAdmin().longValue()==1){
                            //this is a delegated admin
                            //now verify the dates this admin is active
                            if(null==ug.getStartDate() && null==ug.getEndDate()){
                                delegatedAdmin = true;
                                break;
                            }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                //check if the end date is after today
                                if(null==ug.getEndDate()){
                                    //since the end date is null, it is a delegated admin
                                    delegatedAdmin=true;
                                    break;
                                }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                    //role end date is after today therefore it is a delegated admin
                                    delegatedAdmin=true;
                                    break;
                                }
                            }

                        }
                        /**/

                    }
                }
            }
        }

        if(permanentAdmin){
            returnValue = 2;
        }else if(delegatedAdmin){
            returnValue = 3;
        }

        return returnValue;

    }

    public int getAdminLevelForUser(User user){
        int returnValue = 0;

        List<Group> lstGroupsReturned = new ArrayList<Group>();

        boolean isMidLevelAdmin = false;
        boolean superAdmin = false;
        boolean permanentAdmin =false;
        boolean delegatedAdmin = false;

        if(null!=user){
            //find the groups of the user
            Set<UserGroup> stUserGroup = user.getGroups();
            //iterate the groups and see if the user has admin group
            if (null!= stUserGroup){
                for(UserGroup ug : stUserGroup){
                    if(ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME) || ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME1)){
                        //This is a super admin therefore break the loop as we can return all groups
                        superAdmin = true;
                        break;
                    }else if(ug.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                        //now the user might be a permanent or a delegated admin
                        //look at the dates to verify whether a user is an active level 2 or level 3 admin
                        isMidLevelAdmin = true;
                        break;
                    }
                }
            }
        }

        //we now know that the user is either a super admin or a delegated admin
        if(superAdmin == false && isMidLevelAdmin==true){
            //check if the mid level admin is a delegated admin or a
            //find the groups of the user
            Set<UserGroup> stUserGroup = user.getGroups();
            //iterate the groups and see if the user has admin group
            if (null!= stUserGroup){
                for(UserGroup ug : stUserGroup){
                    if(null!=ug.getPermanentAdmin() && ug.getPermanentAdmin().longValue()==1){
                        //this is a permanent admin
                        //now verify the dates this admin is active
                        if(null==ug.getStartDate() && null==ug.getEndDate()){
                            permanentAdmin = true;
                            break;
                        }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                            //check if the end date is after today
                            if(null==ug.getEndDate()){
                                //since the end date is null, it is a mid level admin
                                permanentAdmin=true;
                                break;
                            }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                //role end date is after today therefore it is a legitimate mid level admin
                                permanentAdmin=true;
                                break;
                            }
                        }

                    }else if(null!=ug.getDelegatedAdmin() && ug.getDelegatedAdmin().longValue()==1){
                        //this is a delegated admin
                        //now verify the dates this admin is active
                        if(null==ug.getStartDate() && null==ug.getEndDate()){
                            delegatedAdmin = true;
                            break;
                        }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                            //check if the end date is after today
                            if(null==ug.getEndDate()){
                                //since the end date is null, it is a delegated admin
                                delegatedAdmin=true;
                                break;
                            }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                //role end date is after today therefore it is a delegated admin
                                delegatedAdmin=true;
                                break;
                            }
                        }

                    }
                }
            }
        }

        if(superAdmin){
            returnValue = 1;
        }else if(permanentAdmin){
            returnValue = 2;
        }else if(delegatedAdmin){
            returnValue = 3;
        }

        return returnValue;

    }

    public int getAdminLevelForUser(String userId){
        int returnValue = 0;

        List<Group> lstGroupsReturned = new ArrayList<Group>();

        //find the user associated with this NetID
        User user = usersRepository.findByInternalId(new Long(userId));

        if(null!=user){
            returnValue = getAdminLevelForUser(user);
        }
        return returnValue;
    }

    public int getAdminLevelForLoggedInUser(){

        int returnValue = 0;
        //get the net id of the logged in user
        String netId =  SecurityContextHolder.getContext().getAuthentication().getName();

        //find the user associated with this NetID
        List<User> lstUser = usersRepository.findByUserExactName(netId);

        if(null!= lstUser){
            User user = lstUser.get(0);
            if(null!=user){
                returnValue = getAdminLevelForUser(user);
            }
        }
        return returnValue;
    }

    private Hashtable<String,AdminGroupInfo> getAdminGroupHashtable(List<AdminGroupInfo> lstAdminGroupInfo){
        Hashtable<String,AdminGroupInfo> htGroups = new Hashtable<String, AdminGroupInfo>();
        //prepare the hashmap for comparison
        for(AdminGroupInfo adminGroupInfoLoggedInUser:lstAdminGroupInfo){
            htGroups.put(""+adminGroupInfoLoggedInUser.getGroup().getId().longValue(),adminGroupInfoLoggedInUser);
        }
        return htGroups;
    }

    private boolean loggedInUserHasMoreAuthorityThanPassedUser(int loggedInUserAuthority, int passedUserAuthority){
        boolean flag=false;
        if(loggedInUserAuthority>3 || loggedInUserAuthority <0 || passedUserAuthority >3 || passedUserAuthority<0){
            //these are not correct values
            flag=false;
        }else{
            flag = decisionTable[loggedInUserAuthority][passedUserAuthority];
        }

        return flag;
    }

    private boolean getDeletionFalg(Hashtable<String,AdminGroupInfo> hmGroupsLoggedInUser, List<AdminGroupInfo> lstAdminGroupInfoSearchedUser){
        boolean flag = false;
        boolean groupNotInHashTable = false;
        //check if the the user being searched has groups assigned to it which the logged in User is not allowed to manage
        List<AdminGroupInfo> lstLoggedInUserAdminGroupsOnly = new ArrayList<AdminGroupInfo>();

        for(AdminGroupInfo adminGroupInfoSearchedUser: lstAdminGroupInfoSearchedUser){
            //if the group  is not found then do not allow this searched user to be deleted
            //this means that the searched user contains an extra group
            if(null==hmGroupsLoggedInUser.get(""+adminGroupInfoSearchedUser.getGroup().getId())){
                groupNotInHashTable = true;
                break;
            }
        }

        //get the list of groups the admin user is admin of (i.e. get the groups that have admin status as 1, 2 or 3 and it is an active admin)
        for(AdminGroupInfo group: hmGroupsLoggedInUser.values()){
            if(group.getAdminLevel()==1 &&
                    (group.isActiveGroupAdmin() ||group.getAdminLevel()==2 ||group.getAdminLevel()==3)
            ){
                lstLoggedInUserAdminGroupsOnly.add(group);
            }
        }
        //it means that the user being searched has all the groups that the logged in user has
        //however it is time to check if the logged in user has higher authority for all the groups
        boolean foundOne = false;
        if(groupNotInHashTable==false){
        //this is time to check if the authority of user being searched is more than the logged in user
            for(AdminGroupInfo adminGroupInfoSearchedUser: lstAdminGroupInfoSearchedUser){
                //if the group  is not found then do not allow this searched user to be deleted
                //this means that the searched user contains an extra group

                if(!loggedInUserHasMoreAuthorityThanPassedUser(hmGroupsLoggedInUser.get(""+adminGroupInfoSearchedUser.getGroup().getId()).getAdminLevel(), adminGroupInfoSearchedUser.getAdminLevel())){
                    foundOne= true;
                    break;
                }

            }
            if(foundOne){
                flag = false;
            }else{
                flag = true;
            }

        }else{
            //do not allow the user to delete
            flag = false;
        }
        return flag;
    }

    public boolean canLoggedInUserDeleteSearchedUser(User searchedUser){
        boolean deletionOK = false;
        int adminLevelLoggedInUser = 0;
        User loggedInUser = null;
        //get the net id of the logged in user
        String netId =  SecurityContextHolder.getContext().getAuthentication().getName();

        List<AdminGroupInfo> lstAdminGroupInfoSearchedUser = getAdminGroupInfoForUser(searchedUser);
        List<AdminGroupInfo> lstAdminGroupInfoLoggedInUser = null;

        int adminLevelSearchedUser = getAdminLevelForUser(searchedUser);

        //find the user associated with this NetID
        List<User> lstUser = usersRepository.findByUserExactName(netId);

        if(null!= lstUser){
            loggedInUser = lstUser.get(0);
            if(null!=loggedInUser){
                adminLevelLoggedInUser = getAdminLevelForUser(loggedInUser);
                lstAdminGroupInfoLoggedInUser = getAdminGroupInfoForUser(loggedInUser);
            }
        }

        Hashtable<String,AdminGroupInfo> hmGroupsLoggedInUser = getAdminGroupHashtable(lstAdminGroupInfoLoggedInUser);



        if(adminLevelLoggedInUser==1){
            //this is a super user
            deletionOK = true;
        }else if(adminLevelLoggedInUser==2){
            if(adminLevelSearchedUser ==1 || adminLevelSearchedUser == 2 ){
                deletionOK = false;
            }else if( adminLevelSearchedUser==3){
                //check if the the user being searched has other groups assigned to it
                deletionOK = getDeletionFalg(hmGroupsLoggedInUser,lstAdminGroupInfoSearchedUser);

                /*for(AdminGroupInfo adminGroupInfoSearchedUser: lstAdminGroupInfoSearchedUser){
                    //if the group  is not found then do not allow this searched user to be deleted
                    //this means that the searched user contains an extra group
                    if(null==hmGroupsLoggedInUser.get(""+adminGroupInfoSearchedUser.getGroup().getId())){
                        deletionOK = false;
                        break;
                    }else{
                        deletionOK = true;
                    }
                }*/

            }else if(adminLevelSearchedUser==0){
                //check if the the user being searched has other groups assigned to it
                deletionOK = getDeletionFalg(hmGroupsLoggedInUser,lstAdminGroupInfoSearchedUser);
            }

        }else if(adminLevelLoggedInUser==3){
            if(adminLevelSearchedUser == 1 || adminLevelSearchedUser == 2 || adminLevelSearchedUser==3){
                deletionOK = false;
            }else if(adminLevelSearchedUser == 0){
                //check if the the user being searched has other groups assigned to it
                deletionOK = getDeletionFalg(hmGroupsLoggedInUser,lstAdminGroupInfoSearchedUser);
            }

        }else if(adminLevelLoggedInUser==0){
            deletionOK = false;
        }

        return deletionOK;
    }

    /*
    public int getAdminLevelForLoggedInUser(){

        int returnValue = 0;

        //get the net id of the logged in user
        String netId =  SecurityContextHolder.getContext().getAuthentication().getName();

        List<Group> lstGroupsReturned = new ArrayList<Group>();

        //find the user associated with this NetID
        List<User> lstUser = usersRepository.findByUserExactName(netId);

        boolean isMidLevelAdmin = false;
        boolean superAdmin = false;
        boolean permanentAdmin =false;
        boolean delegatedAdmin = false;


        if(null!= lstUser){
            User user = lstUser.get(0);
            if(null!=user){
                //find the groups of the user
                Set<UserGroup> stUserGroup = user.getGroups();
                //iterate the groups and see if the user has admin group
                if (null!= stUserGroup){
                    for(UserGroup ug : stUserGroup){
                        if(ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME) || ug.getGroup().getGroupName().equalsIgnoreCase(SUPER_ADMIN_GROUP_NAME1)){
                            //This is a super admin therefore break the loop as we can return all groups
                            superAdmin = true;
                            break;
                        }else if(ug.getGroup().getGroupName().equalsIgnoreCase(DELEGATED_ADMIN_GROUP_NAME)){
                            //now the user might be a permanent or a delegated admin
                            //look at the dates to verify whether a user is an active level 2 or level 3 admin
                            isMidLevelAdmin = true;
                            break;
                        }
                    }
                }
            }
            //we now know that the user is either a super admin or a delegated admin
            if(superAdmin == false && isMidLevelAdmin==true){
                //check if the mid level admin is a delegated admin or a
                //find the groups of the user
                Set<UserGroup> stUserGroup = user.getGroups();
                //iterate the groups and see if the user has admin group
                if (null!= stUserGroup){
                    for(UserGroup ug : stUserGroup){
                        if(null!=ug.getPermanentAdmin() && ug.getPermanentAdmin().longValue()==1){
                            //this is a permanent admin
                            //now verify the dates this admin is active
                            if(null==ug.getStartDate() && null==ug.getEndDate()){
                                permanentAdmin = true;
                                break;
                            }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                //check if the end date is after today
                                if(null==ug.getEndDate()){
                                    //since the end date is null, it is a mid level admin
                                    permanentAdmin=true;
                                    break;
                                }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                    //role end date is after today therefore it is a legitimate mid level admin
                                    permanentAdmin=true;
                                    break;
                                }
                            }

                        }else if(null!=ug.getDelegatedAdmin() && ug.getDelegatedAdmin().longValue()==1){
                            //this is a delegated admin
                            //now verify the dates this admin is active
                            if(null==ug.getStartDate() && null==ug.getEndDate()){
                                delegatedAdmin = true;
                                break;
                            }else if(null!=ug.getStartDate() && new Date().after(ug.getStartDate())){
                                //check if the end date is after today
                                if(null==ug.getEndDate()){
                                    //since the end date is null, it is a delegated admin
                                    delegatedAdmin=true;
                                    break;
                                }else if(null!=ug.getEndDate() && new Date().before(ug.getEndDate())){
                                    //role end date is after today therefore it is a delegated admin
                                    delegatedAdmin=true;
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }

        if(superAdmin){
            returnValue = 1;
        }else if(permanentAdmin){
            returnValue = 2;
        }else if(delegatedAdmin){
            returnValue = 3;
        }

        return returnValue;
    }*/


}
