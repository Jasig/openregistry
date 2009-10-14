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
package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.net.URL;

/**
 * Unique constraints assumes that each role can have only one URL per type
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="url")
@Table(name="prc_urls",
	uniqueConstraints = @UniqueConstraint(columnNames={"url", "address_t", "role_record_id"})

)
@Audited
public class JpaUrlImpl extends Entity implements Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_urls_seq")
    @SequenceGenerator(name="prc_urls_seq",sequenceName="prc_urls_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="url",length=500,nullable = false)
    private URL url;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaUrlImpl() {
        // nothing to do
    }

    public JpaUrlImpl(final JpaRoleImpl role) {
        this.role = role;
    }

    public Type getType() {
        return this.type;
    }

    public URL getUrl() {
        return this.url;
    }

    /**
     * Sets the type for this Url.
     *
     * @param type the type of the URL.  Cannot be NULL.
     * @throws IllegalArgumentException if the provided Type is not of class JpaTypeImpl.
     */
    public void setType(final Type type) throws IllegalArgumentException {
        Assert.isInstanceOf(JpaTypeImpl.class, type, "Must be of type JpaTypeImpl");

        this.type = (JpaTypeImpl) type;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }
}
