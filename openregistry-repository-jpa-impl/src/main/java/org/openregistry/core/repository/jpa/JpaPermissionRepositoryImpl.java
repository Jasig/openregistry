/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.repository.jpa;

import org.openregistry.security.*;
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

    public List<Privilege> getPrivilegesFor(final Subject.SubjectType userType) {
        return (List<Privilege>) this.entityManager.createQuery("select r from privilege r where r.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<PrivilegeSet> getPrivilegeSetsFor(final Subject.SubjectType userType) {
        return (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where p.permissionType = :permissionType").setParameter("permissionType", userType).getResultList();
    }

    public List<PrivilegeSet> getPrivilegeSetsForUser(final String username) {
        return (List<PrivilegeSet>) this.entityManager.createQuery("select p from privilegeSet p where p.permissionType = :permissionType and p.value = :userName").setParameter("userName", username)
                .setParameter("permissionType", Subject.SubjectType.USER)
                .getResultList();       
    }

    public List<Privilege> getPrivilegesForUser(String username) {
        return (List<Privilege>) this.entityManager.createQuery("select r from privilege r where r.value = :userName and r.permissionType = :userPermissionType").setParameter("userName", username)
                .setParameter("userPermissionType", Subject.SubjectType.USER)
                .getResultList();
    }
}
