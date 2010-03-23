/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain.jpa.sor;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.repository.hibernate.URLUserType;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * Unique constraints assumes that each role can have only one URL per type
 * 
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 2:45:38 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorUrl")
@Table(name="prs_urls")
@TypeDef(name="URLUserType",typeClass = URLUserType.class)
// TODO commented out because MySql blows up: uniqueConstraints = @UniqueConstraint(columnNames={"url", "address_t", "role_record_id"}))
@Audited
public class JpaSorUrlImpl extends Entity implements Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_urls_seq")
    @SequenceGenerator(name="prs_urls_seq",sequenceName="prs_urls_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    @NotNull
    @AllowedTypes(property = "url.type")
    private JpaTypeImpl type;

    @org.hibernate.annotations.Type(type="URLUserType")
    @Column(name="url",length=500,nullable = false)
    @NotNull
    private URL url;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    public JpaSorUrlImpl() {
        // nothing to do
    }

    public JpaSorUrlImpl(final JpaSorRoleImpl sorRole) {
        this.sorRole = sorRole;
    }

    public Type getType() {
        return this.type;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setType(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.type = (JpaTypeImpl) type;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }
}
