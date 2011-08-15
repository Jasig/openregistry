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

package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="loa")
@Table(name="prc_leaves_of_absence")
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_leaves_of_absence", indexes = {
        @Index(name = "PRC_LEAVE_OF_ABSENCE_LEAVE_IDX", columnNames = "LEAVE_T"),
        @Index(name = "PRC_LEAVE_OF_ABS_ROLE_REC_IDX", columnNames = "ROLE_RECORD_ID")

})
public class JpaLeaveImpl extends Entity implements Leave {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_leaves_of_absence_seq")
    @SequenceGenerator(name="prc_leaves_of_absence_seq",sequenceName="prc_leaves_of_absence_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="start_date")
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name="end_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne(optional = false)
    @JoinColumn(name="leave_t")
    private JpaTypeImpl reason;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaLeaveImpl() {

    }

    public JpaLeaveImpl(final JpaRoleImpl role, final Leave leave) {
        this.start = leave.getStart();
        this.end = leave.getEnd();
        this.reason = (JpaTypeImpl) leave.getReason();
        this.role = role;
    }

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
        Assert.isInstanceOf(JpaRoleImpl.class, type, "Must be of class JpaTypeImpl");
        this.reason = (JpaTypeImpl) type;
    }
}
