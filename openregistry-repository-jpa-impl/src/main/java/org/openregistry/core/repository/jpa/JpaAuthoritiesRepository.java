package org.openregistry.core.repository.jpa;

import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.jpa.JpaAuthorityImpl;
import org.openregistry.core.authorization.jpa.JpaGroupImpl;
import org.openregistry.core.repository.AuthoritiesRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/8/11
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "authoritiesRepository")
public class JpaAuthoritiesRepository implements AuthoritiesRepository{
    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;
    @Override
    public Authority findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaAuthorityImpl.class,id);
    }

    @Override
    public List<Authority> findByAuthorityName(String name) throws RepositoryAccessException {
        return (List<Authority>) this.entityManager.createQuery("select a from auth_authorities a where a.authorityName = :name").setParameter("name",name).getResultList();
    }

    @Override
    public List<Authority> findAllAuthorities() throws RepositoryAccessException {
        return (List<Authority>) this.entityManager.createQuery("select a from auth_authorities a").getResultList();
    }

    @Override
    public Authority saveAuthority(Authority authority) throws RepositoryAccessException {
        return this.entityManager.merge(authority);
    }

    @Override
    public void deleteAuthority(Authority authority) throws RepositoryAccessException {
        List<Group> lstGroups = authority.getGroups();
        for(Group group : lstGroups){
            group.removeAuthority(authority);
            this.entityManager.merge(group);
        }
        authority.removeAllGroups();
        this.entityManager.merge(authority);
        this.entityManager.flush();

        this.entityManager.remove(authority);
        this.entityManager.flush();
    }

        /**
     * Expose underlying EntityManager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Set<Authority> getAuthorityListByAuthorityIds(String[] authorityIDs) throws RepositoryAccessException {
        Set<Authority> authoritySet = new HashSet<Authority>();
        for(int i = 0; i< authorityIDs.length; i++){
            JpaAuthorityImpl authority = this.entityManager.find(JpaAuthorityImpl.class, new Long(authorityIDs[i]));
            if(null!=authority){
                authoritySet.add(authority);
            }
        }
        return authoritySet;
    }
}
