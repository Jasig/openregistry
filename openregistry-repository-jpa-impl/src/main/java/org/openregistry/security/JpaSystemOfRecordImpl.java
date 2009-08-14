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

/**
 * Represents the {@link org.openregistry.security.SystemOfRecord} via JPA.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "systemOfRecord")
@Table(name="ctx_sors")
public final class JpaSystemOfRecordImpl implements SystemOfRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_sors_seq")
    @SequenceGenerator(name="ctx_sors_seq",sequenceName="ctx_sors_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="systemOfRecord", fetch = FetchType.LAZY, targetEntity = JpaPrivilegeImpl.class)
    private List<JpaPrivilegeImpl> privileges;

    public String getName() {
        return this.name;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaSystemOfRecordImpl that = (JpaSystemOfRecordImpl) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
