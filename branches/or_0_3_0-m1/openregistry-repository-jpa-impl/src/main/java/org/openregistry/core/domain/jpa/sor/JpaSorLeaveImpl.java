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

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.internal.Entity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 0.1
 */
@javax.persistence.Entity(name="sorLoa")
@Table(name="prs_leaves_of_absence")
@Audited
@org.hibernate.annotations.Table(appliesTo = "prs_leaves_of_absence", indexes = @Index(name = "PRS_LOA_ROLE_INDEX", columnNames = "role_record_id"))
public class JpaSorLeaveImpl extends Entity implements Leave {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_leaves_of_absence_seq")
    @SequenceGenerator(name="prs_leaves_of_absence_seq",sequenceName="prs_leaves_of_absence_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="start_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date start;

    @Column(name="end_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne(optional = false)
    @JoinColumn(name="leave_t")
    @NotNull
    @AllowedTypes(property = "leave.reason")
    private JpaTypeImpl reason;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    protected Long getId() {
        return this.id;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setStart(final Date date) {
        this.start = date;
    }

    public void setEnd(final Date date) {
        this.end = date;
    }

    public Type getReason() {
        return this.reason;
    }

    public void setReason(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.reason = (JpaTypeImpl) type;
    }
}
