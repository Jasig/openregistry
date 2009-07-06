package org.openregistry.security;

import org.openregistry.core.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<Permission> getPermissionsFor(final Permission.PermissionType userType) {
        return (List<Permission>) this.entityManager.createQuery("select p from permission p where p.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    // TODO expressions!!
    public List<Permission> getPermissionsForUser(String username, Person person) {
        return (List<Permission>) this.entityManager.createQuery("select p from permission p where (p.value = :userName and p.permissionType = :userPermissionType) or p.permissionType = :authenticatedPermissionType or p.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Permission.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Permission.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Permission.PermissionType.EVERYONE)
                .getResultList();
    }
}
