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

import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="loa")

// TODO does this table need UniqueConstraints?  A role could have multiple LOA entries if the start and end dates don't overlap.

@Table(name="prc_leaves_of_absence")
@Audited
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
        if (!(type instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.reason = (JpaTypeImpl) type;
    }
}
