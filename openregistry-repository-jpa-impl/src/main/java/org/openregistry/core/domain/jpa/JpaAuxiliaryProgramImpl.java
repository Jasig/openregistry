package org.openregistry.core.domain.jpa;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/20/11
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="AUX_PROGRAMS")
@Table(name="AUX_PROGRAMS")
@Audited
@org.hibernate.annotations.Table(appliesTo = "AUX_PROGRAMS", indexes = {
        @Index(name = "AUTH_PROGRAM_NAME_IDX", columnNames = "PROGRAM_NAME")
})
public class JpaAuxiliaryProgramImpl extends Entity implements AuxiliaryProgram {
    protected static final Logger logger = LoggerFactory.getLogger(JpaAuxiliaryProgramImpl.class);
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AUX_PROGRAMS_SEQ")
    @SequenceGenerator(name="AUX_PROGRAMS_SEQ",sequenceName="AUX_PROGRAMS_SEQ",initialValue=1,allocationSize=50)

    private Long id;

    @Column(name="PROGRAM_NAME",nullable=false)
    private String programName;

    @Column(name="SPONSOR_ID", nullable = false)
    private Long sponsorId;

    @ManyToOne(optional = false, targetEntity = JpaTypeImpl.class)
    @JoinColumn(name="SPONSOR_T")
    private Type sponsorType;

    @Column(name = "AFFILIATION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date affiliationDate;

    @Column(name = "TERMINATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date terminationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program", fetch = FetchType.EAGER, targetEntity = JpaAuxiliaryIdentifierImpl.class)
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "program", fetch = FetchType.EAGER)
    //@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<AuxiliaryIdentifier> identifiers = new HashSet<AuxiliaryIdentifier>();



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getProgramName() {
        return programName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getSponsorId() {
        return sponsorId;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Type getSponsorType() {
        return sponsorType;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getAffiliationDate() {
        return affiliationDate;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getTerminationDate() {
        return terminationDate;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setProgramName(String auxiliaryProgramName) {
        this.programName = auxiliaryProgramName;
    }

    @Override
    public void setSponsorType(Type sponsorType) {
        this.sponsorType = sponsorType;
    }

    @Override
    public void setAffiliationDate(Date affiliationDate) {
        this.affiliationDate = affiliationDate;
    }

    @Override
    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    @Override
    public Set<AuxiliaryIdentifier> getIdentifiers() {
        return identifiers;
    }

    @Override
    public void addAuxiliaryIdentifier(AuxiliaryIdentifier identifier) {
        identifiers.add(identifier);
    }

    @Override
    public void removeAuxiliaryIdentifier(AuxiliaryIdentifier identifier) {
        identifiers.remove(identifier);
    }

    @Override
    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }
}
