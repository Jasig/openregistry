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

    public List<Rule> getRulesFor(final Rule.PermissionType userType) {
        return (List<Rule>) this.entityManager.createQuery("select r from rule r where r.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<Rule> getRulesForUser(String username, Person person) {
        final List<Rule> permissionsForUserOrEveryone = (List<Rule>) this.entityManager.createQuery("select r from rule r where (r.value = :userName and r.permissionType = :userPermissionType) or r.permissionType = :authenticatedPermissionType or r.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Rule.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Rule.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Rule.PermissionType.EVERYONE)
                .getResultList();

        // TODO retrieve these expressions and parse them
        final List<Rule> permissionsByExpression = new ArrayList<Rule>();

        final List<Rule> permissions = new ArrayList<Rule>();
        permissions.addAll(permissionsByExpression);
        permissions.addAll(permissionsForUserOrEveryone);

        return permissions;
    }
}
