package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Type;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Embeddable
public class JpaLeaveImpl implements Leave {

    @Column(name="affiliation_date",table="prs_sor_role_records")
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name="termination_date",table="prs_sor_role_records")
    @Temporal(TemporalType.DATE)
    private Date end;

    // TODO map reason
    private Type reason;

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
        this.reason = type;
    }
}
