package org.openregistry.core.repository.jpa;

import org.openregistry.core.domain.Person;
import org.openregistry.security.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of {@link org.openregistry.security.PermissionRepository}.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@Repository("permissionRepository")
public final class JpaPermissionRepositoryImpl implements PermissionRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Privilege> getPrivilegesFor(final Subject.PermissionType userType) {
        return (List<Privilege>) this.entityManager.createQuery("select r from privilege r where r.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<PrivilegeSet> getPrivilegeSetsFor(final Subject.PermissionType userType) {
        return (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where p.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<PrivilegeSet> getPrivilegeSetsForUser(final String username) {
        return (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where p.permissionType = :permissionType and p.value = :userName").setParameter("userName", username)
                .setParameter("permissionType", Subject.PermissionType.USER)
                .getResultList();       
    }

    public List<Privilege> getPrivilegesForUser(String username) {
        return (List<Privilege>) this.entityManager.createQuery("select r from privilege r where r.value = :userName and r.permissionType = :userPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Privilege.PermissionType.USER)
                .getResultList();
    }
}
