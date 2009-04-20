package org.openregistry.core.domain.jpa.sor;

import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.DateAfter;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorLeaveImpl;
import org.openregistry.core.domain.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 11:15:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name="sorRole")
@Table(name="prs_role_records")
@ValidateDefinition
public class JpaSorRoleImpl extends org.openregistry.core.domain.internal.Entity implements SorRole {

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_role_records_seq")
    @SequenceGenerator(name="prs_role_records_seq",sequenceName="prs_role_records_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    @NotEmpty
    @JvGroup
    private String sorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER)
    private Set<JpaSorUrlImpl> urls = new HashSet<JpaSorUrlImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER)
    private Set<JpaSorEmailAddressImpl> emailAddresses = new HashSet<JpaSorEmailAddressImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER)
    private Set<JpaSorPhoneImpl> phones = new HashSet<JpaSorPhoneImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER)
    private Set<JpaSorAddressImpl> addresses = new HashSet<JpaSorAddressImpl>();

    @Column(name="source_sor_id", nullable = false)
    @NotEmpty
    @JvGroup
    private String sourceSorIdentifier;

    @ManyToOne(optional = false)
    @JoinColumn(name="sor_person_id", nullable=false)
    private JpaSorPersonImpl sorPerson;

    @Column(name="percent_time",nullable=false)
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_status_t")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch=FetchType.EAGER)
    private Set<JpaSorLeaveImpl> leaves = new HashSet<JpaSorLeaveImpl>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_id")
    @NotNull (customCode="sponsorRequiredMsg")
    private JpaSorPersonImpl sponsor;

    @Column(name="affiliation_date",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull(customCode="startDateRequiredMsg")
    private Date start;

    @Column(name="termination_date")
    @Temporal(TemporalType.DATE)
    @DateAfter(after = "#{parent.start}", customCode = "startDateEndDateCompareMsg")
    private Date end;

    @ManyToOne()
    @JoinColumn(name="termination_t")
    private JpaTypeImpl terminationReason;

    protected Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public void setSorId(String sorId){
        this.sorId = sorId;
    }

    public String getSourceSorIdentifier() {
        return this.sourceSorIdentifier;
    }

    public void setSourceSorIdentifier(final String sorIdentifier) {
        this.sourceSorIdentifier = sorIdentifier;
    }

    public JpaSorRoleImpl() {
        // nothing to do
    }

    public JpaSorRoleImpl(JpaRoleInfoImpl roleInfo, JpaSorPersonImpl sorPerson) {
        this.roleInfo = roleInfo;
        this.sorPerson = sorPerson;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setPercentage(final int percentage) {
        this.percentage = percentage;
    }

    public Type getPersonStatus() {
        return this.personStatus;
    }

    public void setPersonStatus(final Type personStatus) {
        if (!(personStatus instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.personStatus = (JpaTypeImpl) personStatus;
    }

    public void setSponsor(final SorPerson sponsor) {
        if (!(sponsor instanceof JpaSorPersonImpl)) {
            throw new IllegalArgumentException("sponsor must be of type JpaSorPersonImpl.");
        }
        this.sponsor = (JpaSorPersonImpl) sponsor;
    }

    public SorPerson getSponsor() {
        return this.sponsor;
    }
    public Type getTerminationReason() {
        return this.terminationReason;
    }

    public void setTerminationReason(final Type reason) {
        if (!(reason instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }
        this.terminationReason = (JpaTypeImpl) reason;
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
