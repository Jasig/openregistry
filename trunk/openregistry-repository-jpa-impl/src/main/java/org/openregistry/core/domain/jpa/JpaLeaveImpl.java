package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Type;

import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaLeaveImpl implements Leave {

    private Date start;

    private Date end;

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
