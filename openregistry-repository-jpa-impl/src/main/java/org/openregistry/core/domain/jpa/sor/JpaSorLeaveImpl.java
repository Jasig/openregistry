package org.openregistry.core.domain.jpa.sor;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.internal.Entity;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.core.ValidateDefinition;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 4:05:24 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorLoa")
@Table(name="prs_leaves_of_absence")
@Audited
@ValidateDefinition
public final class JpaSorLeaveImpl extends Entity implements Leave {

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
