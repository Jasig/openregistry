package org.openregistry.core.repository.jpa;

import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.authorization.UserGroup;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserImpl;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.repository.UsersRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/7/11
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "usersRepository")
public class JpaUsersRepository implements UsersRepository {
    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;

    @Override
    public User findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaUserImpl.class, id);
    }

    @Override
    public List<User> findByUserExactName(String name) throws RepositoryAccessException {
        return (List<User>) this.entityManager.createQuery("Select u from auth_users u where u.userName = :name").setParameter("name", name).getResultList();
    }

    @Override
    public List<User> findByUserName(String name) throws RepositoryAccessException {
        return (List<User>) this.entityManager.createQuery("Select u from auth_users u where upper(u.userName) LIKE :name").setParameter("name", "%" + name.toUpperCase() + "%").getResultList();
    }

    @Override
    public List<User> findAllUsers() throws RepositoryAccessException {

        return (List<User>) this.entityManager.createQuery("SELECT u FROM auth_users u").getResultList();
    }

    @Override
    public User saveUser(User user) throws RepositoryAccessException {
        return this.entityManager.merge(user);
    }

    /*
    @Override
    public void deleteUser(User user) throws RepositoryAccessException {
        //Before deleting the user, unassign all the groups. This is because CASCADE will delete all records
        List<Group> lstGroups = user.getGroups();
        for(Group group : lstGroups){
            group.removeUser(user);
            this.entityManager.merge(group);
        }
        user.removeAllGroups();
        //save the user with the changes
        this.entityManager.merge(user);
        //commit to the DB
        this.entityManager.flush();
        //remove the user
        this.entityManager.remove(user);
        //commit to the DB
        this.entityManager.flush();
    }

    @Override
    public void deleteGroupOfUser(User user, Group group) throws RepositoryAccessException {
        //Group groupToDelete = this.entityManager.getReference(group.getClass(),group.getId());
        //this.entityManager.remove(groupToDelete);
        //this.entityManager.remove(group);
        user.removeGroup(group);
        saveUser(user);
    }

     @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupToUser(User user, Group group) throws RepositoryAccessException {
        this.entityManager.merge(group);
        user.addGroup(group);
        //this.entityManager.merge(user);
        saveUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupsToUser(User user, List<Group> groups) throws RepositoryAccessException {
        for(Group newGroup : groups){
                user.addGroup(newGroup);
            }
        saveUser(user);
    }

    @Override
    public List<Group> findGroupOfUser(User user) throws RepositoryAccessException {
        User user1 = this.entityManager.find(JpaUserImpl.class,user.getId());
        return user1.getGroups();
    }

    @Override
    public List<Group> findGroupOfUser(Long id) throws RepositoryAccessException {
        User user = this.entityManager.find(JpaUserImpl.class,id);
        return user.getGroups();
    }

    @Override
    public List<User> findUsersOfGroup(Group group) throws RepositoryAccessException {
        Group group1 = this.entityManager.find(JpaGroupImpl.class,group.getId());
        return group1.getUsers();
    }

    @Override
    public List<User> findUsersOfGroup(Long id) throws RepositoryAccessException {
        Group group1= this.entityManager.find(JpaGroupImpl.class,id);
        return group1.getUsers();
    }

         */
    @Override
    public void deleteGroupsOfUser(User user) throws RepositoryAccessException {
        if(null!=user.getGroups()){
            for(UserGroup userGroup: user.getGroups()){
                //Group group = this.entityManager.find(JpaGroupImpl.class,userGroup.getGroup().getId());
                Group group = userGroup.getGroup();
                group.removeUserGroup(userGroup);
                this.entityManager.merge(group);
                this.getEntityManager().remove(userGroup);
            }
            user.removeAllGroups();
            saveUser(user);
            this.entityManager.flush();
        }
    }




    /**
     * Expose underlying EntityManager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void deleteUser(User user) throws RepositoryAccessException {
        //Before deleting the user, unassign all the groups. This is because CASCADE will delete all records
        Set<UserGroup> lstUserGroups = user.getGroups();
        for(UserGroup userGroup : lstUserGroups){
            //Group group = this.getEntityManager().find(JpaGroupImpl.class,userGroup.getGroupId());
            //Group group = this.getEntityManager().find(JpaGroupImpl.class,userGroup.getGroup().getId());
            Group group = userGroup.getGroup();
            //group.removeUserGroup(userGroup);
            //group.getUsers().remove(user);
            this.entityManager.remove(userGroup);
            this.entityManager.merge(group);
            this.entityManager.flush();
        }
        user.removeAllGroups();
        //save the user with the changes
        this.entityManager.merge(user);
        //commit to the DB
        this.entityManager.flush();
        //remove the user
        this.entityManager.remove(user);
        //commit to the DB
        this.entityManager.flush();
    }

    @Override
    public void deleteGroupOfUser(User user, Group group) throws RepositoryAccessException {
        //Group groupToDelete = this.entityManager.getReference(group.getClass(),group.getId());
        //this.entityManager.remove(groupToDelete);
        //this.entityManager.remove(group);

        //remove the UserGroup from User side
        UserGroup userGroup = (UserGroup) this.entityManager.createQuery("Select ug from auth_user_group ug where ug.user_id = :user_id and ug.group_id =:group_id").setParameter("user_id", user.getId()).setParameter("group_id",group.getId()).getSingleResult();
        user.getGroups().remove(userGroup);
        this.entityManager.merge(user);
        this.entityManager.flush();

        //Remove the UserGroup from group side
        group.getUsers().remove(userGroup);
        this.entityManager.merge(group);
        this.entityManager.flush();

        //Remove the UserGroup from the DB
        this.entityManager.remove(userGroup);
        this.entityManager.flush();

    }

    @Override
    public void deleteGroupOfUser(User user, UserGroup userGroup) throws RepositoryAccessException {
        Group group = userGroup.getGroup();
        //remove the UserGroup from User side
        user.removeUserGroup(userGroup);
        this.entityManager.merge(user);
        this.entityManager.flush();

        //Remove the UserGroup from group side
        group.removeUserGroup(userGroup);
        //group.getUsers().remove(userGroup);
        this.entityManager.merge(group);
        this.entityManager.flush();

        //Remove the UserGroup from the DB
        this.entityManager.remove(userGroup);
        this.entityManager.flush();

        //saveUser(user);
    }

    @Override
    public void deleteGroupOfUser(User user, List<Long> listOfGroupIdsToBeDeleted) throws RepositoryAccessException{
        Set<UserGroup> userGroups = user.getGroups();
        UserGroup[] ugs = userGroups.toArray(new UserGroup[userGroups.size()]);

        for(Long groupId: listOfGroupIdsToBeDeleted){
            if(null!=ugs && ugs.length>0){
                for(int i=0;i<ugs.length;i++){
                    if(null!= ugs[i] && ugs[i].getGroup().getId().longValue()==groupId.longValue()){
                        ugs[i].getGroup().removeUserGroup(ugs[i]);

                        this.entityManager.merge(ugs[i].getGroup());
                        this.entityManager.flush();

                        this.entityManager.remove(ugs[i]);
                        this.entityManager.flush();

                        ugs[i] = null; //remove the reference

                    }
                }
            }
            /*
            Iterator ugIterator =  userGroups.iterator();
            while(ugIterator.hasNext()){
            //for(UserGroup userGroup:userGroups){
                UserGroup userGroup = (UserGroup) ugIterator.next();
                if(userGroup.getGroup().getId().longValue() == groupId.longValue()){
                    user.removeUserGroup(userGroup);
                    userGroup.getGroup().removeUserGroup(userGroup);
                    this.entityManager.merge(user);
                    this.entityManager.flush();

                    this.entityManager.merge(userGroup.getGroup());
                    this.entityManager.flush();

                    this.entityManager.remove(userGroup);
                    this.entityManager.flush();
                    //ugIterator.remove();

                }
            } */
        }
        Set<UserGroup> modifiedUserGroups = new HashSet<UserGroup>(Arrays.asList(ugs));
        user.setGroups(modifiedUserGroups);
        this.entityManager.merge(user);
        this.entityManager.flush();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupToUser(User user, Group group) throws RepositoryAccessException {
        UserGroup userGroup = new JpaUserGroupImpl(user,group,null,null,null,null);
        user.addUserGroup(userGroup);
        group.addUserGroup(userGroup);
        this.entityManager.persist(userGroup);
        this.entityManager.merge(group);
        this.entityManager.merge(user);
        this.entityManager.flush();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUserGroupToUser(User user, UserGroup userGroup) throws RepositoryAccessException{
        userGroup.getGroup().addUserGroup(userGroup);
        if(!user.getGroups().contains(userGroup)){
            user.addUserGroup(userGroup);
        }
        //this.entityManager.persist(userGroup);
        this.entityManager.merge(userGroup);
        this.entityManager.merge(userGroup.getGroup());
        this.entityManager.merge(user);
        this.entityManager.flush();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGroupsToUser(User user, List<Group> groups) throws RepositoryAccessException {
        for(Group newGroup : groups){
            addGroupToUser(user,newGroup);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUserGroupsToUser(User user, Set<UserGroup> userGroups) throws RepositoryAccessException {

        for(UserGroup newGroup : userGroups){
            addUserGroupToUser(user, newGroup);
        }
    }

    @Override
    public List<Group> findGroupOfUser(User user) throws RepositoryAccessException {
        List<Group> returnListGroups= new ArrayList<Group>();

        User user1 = this.entityManager.find(JpaUserImpl.class,user.getId());
        Set<UserGroup> listUserGroups = user1.getGroups();
        for(UserGroup userGroup:listUserGroups){
            //Group group = this.entityManager.find(JpaGroupImpl.class,userGroup.getGroupId());
            Group group = this.entityManager.find(JpaGroupImpl.class,userGroup.getGroup().getId());
            returnListGroups.add(group);
        }

        return returnListGroups;
    }

    @Override
    public List<Group> findGroupOfUser(Long id) throws RepositoryAccessException {
        //find the user based upon ID
        User user = this.entityManager.find(JpaUserImpl.class,id);

        //find the groups of the user
        List<Group> returnListGroups= new ArrayList<Group>();

        Set<UserGroup> listUserGroups = user.getGroups();
        for(UserGroup userGroup:listUserGroups){
            //Group group = this.entityManager.find(JpaGroupImpl.class,userGroup.getGroupId());
            Group group = this.entityManager.find(JpaGroupImpl.class,userGroup.getGroup().getId());
            returnListGroups.add(group);
        }

        return returnListGroups;

    }

    @Override
    public List<User> findUsersOfGroup(Group group) throws RepositoryAccessException {

        List<User> returnListUsers = new ArrayList<User>();
        //get the group from the DB based upon the groupId
        Group group1 = this.entityManager.find(JpaGroupImpl.class,group.getId());

        Set<UserGroup> listUserGroups = group1.getUsers();
        for(UserGroup userGroup : listUserGroups){
            //User user = this.entityManager.find(JpaUserImpl.class,userGroup.getUserId());
            User user = this.entityManager.find(JpaUserImpl.class,userGroup.getUser().getId());
            returnListUsers.add(user);
        }
        return returnListUsers;
    }

    @Override
    public List<User> findUsersOfGroup(Long id) throws RepositoryAccessException {
        List<User> returnListUsers = new ArrayList<User>();
        //get the group from the DB based upon the groupId
        Group group1= this.entityManager.find(JpaGroupImpl.class,id);
        Set<UserGroup> listUserGroups = group1.getUsers();
        for(UserGroup userGroup : listUserGroups){
            //User user = this.entityManager.find(JpaUserImpl.class,userGroup.getUserId());
            User user = this.entityManager.find(JpaUserImpl.class,userGroup.getUser().getId());
            returnListUsers.add(user);
        }
        return returnListUsers;
    }

    public UserGroup getUserGroup(Long userId,Long groupId) throws RepositoryAccessException{
        UserGroup userGroup = (UserGroup) this.entityManager.createQuery("Select ug from auth_user_group ug where ug.user_id = :user_id and ug.group_id =:group_id").setParameter("user_id", userId.longValue()).setParameter("group_id",groupId.longValue()).getSingleResult();
        return userGroup;
    }

    public void saveUserGroup(UserGroup userGroup) throws RepositoryAccessException{
        this.entityManager.merge(userGroup);
        this.getEntityManager().flush();
    }

}
