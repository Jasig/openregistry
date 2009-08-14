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
import java.util.Set;

/**
 * JPA-backed implementation of the {@link org.openregistry.security.PermissionSet} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="permissionSet")
@Table(name="ctx_permission_sets")
public class JpaPermissionSetImpl implements PermissionSet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_permission_sets_seq")
    @SequenceGenerator(name="ctx_permission_sets_seq",sequenceName="ctx_permission_sets_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="name", length = 100, nullable = false,unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = JpaPermissionImpl.class)
    @JoinTable(name = "ctx_permission_set_mapping")
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "permissionSet",fetch = FetchType.LAZY,targetEntity = JpaPrivilegeSetImpl.class)
    private Set<PrivilegeSet> privilegeSets;

    public String getName() {
        return this.name;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }
}
