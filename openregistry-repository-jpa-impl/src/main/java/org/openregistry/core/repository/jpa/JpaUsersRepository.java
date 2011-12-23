package org.openregistry.core.repository.jpa;

import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.authorization.jpa.JpaUserImpl;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.repository.UsersRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Set;

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
    public List<User> findByUserName(String name) throws RepositoryAccessException {
        return (List<User>) this.entityManager.createQuery("Select u from auth_users u where u.userName = :name").setParameter("name", name).getResultList();
    }

    @Override
    public List<User> findAllUsers() throws RepositoryAccessException {

        return (List<User>) this.entityManager.createQuery("SELECT u FROM auth_users u").getResultList();
    }

    @Override
    public User saveUser(User user) throws RepositoryAccessException {
        return this.entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) throws RepositoryAccessException {
        //Before deleting the user, unassign all the groups. This is because CASCADE will delete all records
        Set<Group> setGroups = user.getUserGroups();
        for(Group group : setGroups){
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
    public void deleteGroupsOfUser(User user) throws RepositoryAccessException {
//            Set<Group> existingGroups = user.getUserGroups();
//            for(Group eachExistingGroup : existingGroups){
//                user.removeGroup(eachExistingGroup);
//            }
        user.removeAllGroups();
        saveUser(user);
        this.entityManager.flush();
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
    public void addGroupsToUser(User user, Set<Group> groups) throws RepositoryAccessException {
        for(Group newGroup : groups){
                user.addGroup(newGroup);
            }
        saveUser(user);
    }

    /**
     * Expose underlying EntityManager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Set<Group> findGroupOfUser(User user) throws RepositoryAccessException {
        User user1 = this.entityManager.find(JpaUserImpl.class,user.getId());
        return user1.getUserGroups();
    }

    @Override
    public Set<Group> findGroupOfUser(Long id) throws RepositoryAccessException {
        User user = this.entityManager.find(JpaUserImpl.class,id);
        return user.getUserGroups();
    }
}
