package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.internal.*;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.ValidateList;

import javax.persistence.*;
import javax.persistence.Entity;

import java.util.*;

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

    @Column(name="person_id")
    private Long personId;

    @Column(name="date_of_birth",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull
    @JvGroup
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false)
    @NotEmpty
    private String gender;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER, targetEntity = JpaSorNameImpl.class)
    @ValidateList
    private Set<Name> names = new HashSet<Name>();

    @Column(name="ssn",nullable=true)
    private String ssn;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person",fetch = FetchType.EAGER, targetEntity = JpaSorRoleImpl.class)
    private List<SorRole> roles = new ArrayList<SorRole>();

    public List<SorRole> getRoles(){
        return this.roles;
    }

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

    public Set<Name> getNames() {
        return this.names;
    }

    public void setNames(Set<Name> names){
        this.names = names;
    }

    @Transient
    private List<Name> nameList;

    public List<Name> getNameList() {
        nameList = new ArrayList();
        Iterator iterator = getNames().iterator();
        while (iterator.hasNext()){
            Name name = (Name)iterator.next();
            nameList.add(name);
        }
        return this.nameList;
    }

    public void setNameList(List list){
        this.nameList=list;
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

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

    public synchronized SorRole removeRoleByRoleId(final Long id) {
        SorRole roleToDelete = null;
        for (final SorRole role : this.roles) {
            final Long roleId = role.getRoleId();
            if (roleId != null && roleId.equals(id)) {
                roleToDelete = role;
                break;
            }
        }

        if (roleToDelete != null) {
            this.roles.remove(roleToDelete);
            return roleToDelete;
        }
        
        return null;
    }
}
