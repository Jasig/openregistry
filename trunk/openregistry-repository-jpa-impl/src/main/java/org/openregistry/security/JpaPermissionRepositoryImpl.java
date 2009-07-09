package org.openregistry.security;

import org.openregistry.core.domain.Person;
import org.springframework.stereotype.Repository;

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

    public List<Permission> getPermissionsFor(final Permission.PermissionType userType) {
        return (List<Permission>) this.entityManager.createQuery("select p from permission p where p.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<Permission> getPermissionsForUser(String username, Person person) {
        final List<Permission> permissionsForUserOrEveryone = (List<Permission>) this.entityManager.createQuery("select p from permission p where (p.value = :userName and p.permissionType = :userPermissionType) or p.permissionType = :authenticatedPermissionType or p.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Permission.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Permission.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Permission.PermissionType.EVERYONE)
                .getResultList();

        // TODO retrieve these expressions and parse them
        final List<Permission> permissionsByExpression = new ArrayList<Permission>();

        final List<Permission> permissions = new ArrayList<Permission>();
        permissions.addAll(permissionsByExpression);
        permissions.addAll(permissionsForUserOrEveryone);

        return permissions;
    }
}
