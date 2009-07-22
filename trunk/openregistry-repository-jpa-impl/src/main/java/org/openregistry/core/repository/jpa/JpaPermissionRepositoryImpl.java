package org.openregistry.core.repository.jpa;

import org.openregistry.core.domain.Person;
import org.openregistry.security.PermissionRepository;
import org.openregistry.security.Privilege;
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

    public List<Privilege> getPrivilegesFor(final Privilege.PermissionType userType) {
        return (List<Privilege>) this.entityManager.createQuery("select r from rule r where r.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<Privilege> getPrivilegesForUser(String username, Person person) {
        final List<Privilege> permissionsForUserOrEveryone = (List<Privilege>) this.entityManager.createQuery("select r from rule r where (r.value = :userName and r.permissionType = :userPermissionType) or r.permissionType = :authenticatedPermissionType or r.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Privilege.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Privilege.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Privilege.PermissionType.EVERYONE)
                .getResultList();

        // TODO retrieve these expressions and parse them
        final List<Privilege> permissionsByExpression = new ArrayList<Privilege>();

        final List<Privilege> permissions = new ArrayList<Privilege>();
        permissions.addAll(permissionsByExpression);
        permissions.addAll(permissionsForUserOrEveryone);

        return permissions;
    }
}
