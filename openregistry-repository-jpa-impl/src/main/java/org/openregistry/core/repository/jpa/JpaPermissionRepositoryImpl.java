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

    @Autowired(required=true)
    private ExpressionParser expressionParser;

    public List<Privilege> getPrivilegesFor(final Privilege.PermissionType userType) {
        return (List<Privilege>) this.entityManager.createQuery("select r from privilege r where r.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<Privilege> getPrivilegesForUser(String username, Person person) {
        final List<Privilege> permissions = new ArrayList<Privilege>();

        final List<Privilege> permissionsForUserOrEveryone = (List<Privilege>) this.entityManager.createQuery("select r from privilege r where (r.value = :userName and r.permissionType = :userPermissionType) or r.permissionType = :authenticatedPermissionType or r.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Privilege.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Privilege.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Privilege.PermissionType.EVERYONE)
                .getResultList();

        final List<PrivilegeSet> privilegeSets = (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where  (p.value = :userName and p.permissionType = :userPermissionType) or p.permissionType = :authenticatedPermissionType or p.permissionType = :anonymousPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Privilege.PermissionType.USER)
                .setParameter("authenticatedPermissionType", Privilege.PermissionType.AUTHENTICATED)
                .setParameter("anonymousPermissionType", Privilege.PermissionType.EVERYONE)
                .getResultList();

        if (person != null) {
            final List<Privilege> privilegesByExpression = (List<Privilege>) this.entityManager.createQuery("select p from privilege p where p.permissionType = :permissionType").setParameter("permissionType", Subject.PermissionType.EXPRESSION).getResultList();
            final List<PrivilegeSet> privilegeSetsByExpression = (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where p.permissionType = :permissionType").setParameter("permissionType", Subject.PermissionType.EXPRESSION).getResultList();

            for (final Privilege p : privilegesByExpression) {
                if (this.expressionParser.matches(person, p.getExpression())) {
                    permissions.add(p);
                }
            }

            for (final PrivilegeSet ps : privilegeSetsByExpression) {
                if (this.expressionParser.matches(person, ps.getExpression())) {
                    permissions.addAll(ps.getPrivileges());
                }
            }
        }

        permissions.addAll(permissionsForUserOrEveryone);

        for (final PrivilegeSet p : privilegeSets) {
            permissions.addAll(p.getPrivileges());
        }

        return permissions;
    }
}
