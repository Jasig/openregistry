package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Active;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Embeddable
public class JpaActiveImpl implements Active {

    @Column(name="leave_start_date",table="prs_sor_role_records")
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name="leave_stop_date",table="prs_sor_role_records")
    @Temporal(TemporalType.DATE)
    private Date end;

    // TODO map this
    private JpaTypeImpl reason;

    public Type getTerminationReason() {
        return this.reason;
    }

    public void setTerminationReason(final Type reason) {
        if (!(reason instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }
        this.reason = (JpaTypeImpl) reason;
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
}
