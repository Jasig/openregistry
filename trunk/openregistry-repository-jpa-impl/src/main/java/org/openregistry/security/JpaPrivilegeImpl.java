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

/**
 * Implementation of the {@link Privilege}.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "privilege")
@Table(name = "ctx_privileges")
public final class JpaPrivilegeImpl implements Privilege {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_privileges_seq")
    @SequenceGenerator(name="ctx_privileges_seq",sequenceName="ctx_privileges_seq",initialValue=1,allocationSize=50)
    private long id;

    @ManyToOne(optional = false,targetEntity = JpaPermissionImpl.class)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne(optional=false,targetEntity = JpaSystemOfRecordImpl.class)
    @JoinColumn(name="sor_id")
    private SystemOfRecord systemOfRecord;

    @Column(name="permission_type")
    private SubjectType subjectType;

    @Column(name="value")
    private String value;

    public JpaPrivilegeImpl() {
        // nothing to do for JPA
    }

    public JpaPrivilegeImpl(final SubjectType subjectType, final String value, final SystemOfRecord systemOfRecord, final Permission permission) {
        this.subjectType = subjectType;
        this.value = value;
        this.systemOfRecord = systemOfRecord;
        this.permission = permission;
    }

    public String getUser() {
        if (getSubjectType() == SubjectType.USER) {
            return this.value;
        }

        return null;
    }

    public String getExpression() {
        if (getSubjectType() == SubjectType.EXPRESSION) {
            return this.value;
        }
        return null;
    }

    public SubjectType getSubjectType() {
        return this.subjectType;
    }

    public SystemOfRecord getSystemOfRecord() {
        return this.systemOfRecord;
    }

    public String getResource() {
        return this.permission.getResource();
    }

    public String getDescription() {
        return this.permission.getDescription();
    }

    public boolean canCreate() {
        return this.permission.canCreate();
    }

    public boolean canRead() {
        return this.permission.canRead();
    }

    public boolean canUpdate() {
        return this.permission.canUpdate();
    }

    public boolean canDelete() {
        return this.permission.canDelete();
    }

    public int compareTo(final Privilege privilege) {
        if (this.equals(privilege)) {
            return 0;
        }

        final int thisIndex = this.getSubjectType().ordinal();
        final int otherIndex = privilege.getSubjectType().ordinal();

        return thisIndex - otherIndex;
    }
}
