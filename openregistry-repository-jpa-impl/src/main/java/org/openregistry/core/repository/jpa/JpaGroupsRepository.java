package org.openregistry.core.repository.jpa;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.authorization.UserGroup;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserImpl;
import org.openregistry.core.repository.GroupsRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "groupsRepository")
public class JpaGroupsRepository implements GroupsRepository{

    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;

    @Override
    public Group findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaGroupImpl.class,id);
    }

    @Override
    public List<Group> findByGroupName(String name) throws RepositoryAccessException {
        return (List<Group>) this.entityManager.createQuery("select g from auth_groups g where g.groupName = :name").setParameter("name",name).getResultList();
    }

    @Override
    public List<Group> findAllGroups() throws RepositoryAccessException {
        return (List<Group>) this.entityManager.createQuery("select g from auth_groups g").getResultList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Group saveGroup(Group group) throws RepositoryAccessException {
        return this.entityManager.merge(group);
    }

//Start of Old Code for Groups
    /*
    @Override
    public void deleteGroup(Group group) throws RepositoryAccessException {
        List<Authority> lstAuthorities = group.getGroupAuthorities();
        for(Authority authority : lstAuthorities){
            authority.removeGroup(group);
            this.entityManager.merge(authority);
            this.entityManager.flush();
        }

        List<User> lstUsers = group.getUsers();
        for(User user : lstUsers){

            List<Group> lstTempGroups = user.getGroups();
            user.removeGroup(group);
            this.entityManager.merge(user);
            this.entityManager.flush();
        }
        //now remove authorities of the group
        group.removeAllAuthorities();
        //now remove all user from the Group
        group.removeAllUsers();
        this.entityManager.merge(group);
        this.entityManager.flush();

        this.entityManager.remove(group);
        this.entityManager.flush();
    }
*/
//End of Old Code for Groups
    //Modified Code
    @Override
    public void deleteGroup(Group group) throws RepositoryAccessException {
        //Get the Authorities of the Group first and remove the relationship between Group and the Authorities (from each authority object)
        List<Authority> lstAuthorities = group.getGroupAuthorities();
        for(Authority authority : lstAuthorities){
            authority.removeGroup(group);
            this.entityManager.merge(authority);
            this.entityManager.flush();
        }

        //Get the Users of the group and remove group entry from each user object

        Set<UserGroup> lstUsers = group.getUsers();
        if(null!=lstUsers){
            for(UserGroup userGroup : lstUsers){

                //Find the User object related to UserGroup.
                //User userWhoseGroupShouldBeDeleted = this.entityManager.find(JpaUserImpl.class,new Long(userGroup.getUserId()));
                User userWhoseGroupShouldBeDeleted = this.entityManager.find(JpaUserImpl.class,new Long(userGroup.getUser().getId()));
                userWhoseGroupShouldBeDeleted.removeUserGroup(userGroup);
                this.entityManager.merge(userWhoseGroupShouldBeDeleted);
                this.entityManager.flush();

                //remove the UserGroup as it is a separate entity
                this.entityManager.remove(userGroup);
            }
        }


        //now remove authorities of the group
        group.removeAllAuthorities();
        //now remove all user from the Group
        group.removeAllUsers();
        this.entityManager.merge(group);
        this.entityManager.flush();



        this.entityManager.remove(group);
        this.entityManager.flush();
    }

    @Override
    public void deleteAuthorityOfGroup(Group group, Authority authority) throws RepositoryAccessException {
        Authority authorityToBeDeleted = this.entityManager.getReference(authority.getClass(),authority.getId());
        this.entityManager.remove(authorityToBeDeleted);
        saveGroup(group);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Group addAuthorityToGroup(Group group, Authority authority) throws RepositoryAccessException {
        this.entityManager.merge(authority);
        //Authority authorityToBeAdded = this.entityManager.getReference(authority.getClass(),authority.getId());
        //group.addAuthority(authorityToBeAdded);
        group.addAuthority(authority);
        return saveGroup(group);
    }

        /**
     * Expose underlying EntityManager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Authority> findAuthoritiesOfGroup(Group group) throws RepositoryAccessException {
        Group group1 = this.entityManager.find(JpaGroupImpl.class,group.getId());
        return group1.getGroupAuthorities();
    }

    @Override
    public List<Authority> findAuthoritiesOfGroup(Long id) throws RepositoryAccessException {
        Group group = this.entityManager.find(JpaGroupImpl.class,id);
        return group.getGroupAuthorities();
    }

    @Override
    public List<Group> getGroupListByGroupIds(String[] groupIds) throws RepositoryAccessException {
        List<Group> groupList = new ArrayList<Group>();
        for(int i = 0; i< groupIds.length; i++){
            JpaGroupImpl group = this.entityManager.find(JpaGroupImpl.class, new Long(groupIds[i]));
            if(null!=group){
                groupList.add(group);
            }
        }
        return groupList;
    }
    @Override
    public void deleteAuthoritiesOfGroup(Group group) throws RepositoryAccessException {
        group.removeAllAuthorities();
        saveGroup(group);
        this.entityManager.flush();
    }

    @Override
    public void addAuthoritiesToGroup(Group group, Set<Authority> authorities){
           for(Authority newAuthority : authorities){
                group.addAuthority(newAuthority);
            }
        saveGroup(group);
    }

    @Override
    public void deleteUserOfGroup(Group group, User user) {
        for(UserGroup userGroup: group.getUsers()){
            if(userGroup.getUser().getId().longValue()==user.getId().longValue()){
                group.removeUserGroup(userGroup);
            }
        }
    }

    @Override
    public void addUserToGroup(Group group, User user) {
        UserGroup userGroup = new JpaUserGroupImpl(user,group,null,null,null,null);
        group.addUserGroup(userGroup);
    }
}
