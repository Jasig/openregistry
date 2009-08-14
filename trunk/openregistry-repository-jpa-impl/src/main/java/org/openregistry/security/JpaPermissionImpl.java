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
package org.openregistry.security;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the {@link org.openregistry.security.Permission} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="permission")
@Table(name="ctx_permissions")
public final class JpaPermissionImpl implements Permission {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_permissions_seq")
    @SequenceGenerator(name="ctx_permissions_seq",sequenceName="ctx_permissions_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="resource_ex")
    private String resource;

    @Column(name="description")
    private String description;

    @Column(name="p_create")
    private boolean create;

    @Column(name="p_read")
    private boolean read;

    @Column(name="p_update")
    private boolean update;

    @Column(name="p_delete")
    private boolean delete;

    @OneToMany(mappedBy = "permission",fetch = FetchType.LAZY)
    private List<JpaPrivilegeImpl> rules;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY,targetEntity = JpaPermissionSetImpl.class)
    private Set<PermissionSet> permissionSets;

    public String getResource() {
        return this.resource;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canCreate() {
        return this.create;
    }

    public boolean canRead() {
        return this.read;
    }

    public boolean canUpdate() {
        return this.update;
    }

    public boolean canDelete() {
        return this.delete;
    }
}
