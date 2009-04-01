package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.internal.*;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.ValidateList;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

/**
 * Implementation of the SoR Person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="sorPerson")
@Table(name="prs_sor_persons")
@ValidateDefinition
public class JpaSorPersonImpl extends org.openregistry.core.domain.internal.Entity implements SorPerson {

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sor_persons_seq")
    @SequenceGenerator(name="prs_sor_persons_seq",sequenceName="prs_sor_persons_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    @NotEmpty
    @JvGroup
    private String sorId;

    @Column(name="source_sor_id", nullable = false)
    @NotEmpty
    @JvGroup
    private String sourceSorIdentifier;

    @Column(name="date_of_birth",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @JvGroup
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false)
    @NotEmpty
    private String gender;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person")
    @ValidateList
    private Set<JpaSorNameImpl> names = new HashSet<JpaSorNameImpl>();

    @Column(name="ssn",nullable=true)
    private String ssn;

    public String getSsn() {
        return this.ssn;
    }

    public void setSsn(final String ssn) {
        this.ssn = ssn;
    }

    protected Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public String getSourceSorIdentifier() {
        return this.sourceSorIdentifier;
    }

    public void setSourceSorIdentifier(final String sorIdentifier) {
        this.sourceSorIdentifier = sorIdentifier;
    }

    public Set<? extends Name> getNames() {
        return this.names;
    }

    @Transient
    private JpaSorNameImpl firstAddedName;

    public JpaSorNameImpl getFirstAddedName(){
        return firstAddedName;
    }


    public void setSorId(final String id) {
        this.sorId = id;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final Date date) {
        this.dateOfBirth = date;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Name addName() {
        final JpaSorNameImpl jpaSorName = new JpaSorNameImpl(this);
        this.names.add(jpaSorName);
        if (names.size() == 1) firstAddedName = jpaSorName;
        
        return jpaSorName;
    }
}
