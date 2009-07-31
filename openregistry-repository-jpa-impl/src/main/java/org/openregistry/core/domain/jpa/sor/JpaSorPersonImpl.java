package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.ValidateList;
import org.javalid.annotations.validation.DateCheck;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

import java.util.*;

/**
 * Implementation of the SoR Person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="sorPerson")
@Table(name="prs_sor_persons")
@Audited
@ValidateDefinition
public class JpaSorPersonImpl extends Entity implements SorPerson {

    protected static final Logger logger = LoggerFactory.getLogger(JpaSorPersonImpl.class);

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sor_persons_seq")
    @SequenceGenerator(name="prs_sor_persons_seq",sequenceName="prs_sor_persons_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    private String sorId;

    @Column(name="source_sor_id", nullable = false)
    @NotEmpty
    private String sourceSorIdentifier;

    @Column(name="person_id")
    private Long personId;

    @Column(name="date_of_birth",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull(customCode="dateOfBirthRequiredMsg")
    @DateCheck(today = true, mode=DateCheck.MODE_LESS_THAN)
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false)
    @NotEmpty(customCode="genderRequiredMsg")
    private String gender;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER, targetEntity = JpaSorNameImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Name> names = new ArrayList<Name>();

    @Column(name="ssn",nullable=true)
    private String ssn;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person",fetch = FetchType.EAGER, targetEntity = JpaSorRoleImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT) 
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

    public Long getId() {
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

    public List<Name> getNames() {
        return this.names;
    }

    public void setNames(List<Name> names){
        this.names = names;
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
        return jpaSorName;
    }

    public void addName(Name name) {
        this.names.add(name);
        Assert.isInstanceOf(JpaSorNameImpl.class, name);
        ((JpaSorNameImpl)name).moveToPerson(this);
    }

    public synchronized Name findNameByNameId(final Long id) {
        Name nameToFind = null;
        for (final Name name : this.names) {
            final Long nameId = name.getId();
            if (nameId != null && nameId.equals(id)) {
                nameToFind = name;
                break;
            }
        }
        return nameToFind;
    }

    public synchronized void removeName(Name name) {
        this.names.remove(name);
    }

    public synchronized void removeAllNames(){
        this.names.clear();
    }

    // TODO not sure if this should be here
    public String getFormattedNameAndID(){
        final StringBuilder builder = new StringBuilder();
        // TODO fix this next line
        builder.append(this.getNames().iterator().next().getFormattedName());
        builder.append(" ID:");
        builder.append(this.recordId);
        return builder.toString();
    }

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public SorRole addRole(final RoleInfo roleInfo) {
        Assert.isInstanceOf(JpaRoleInfoImpl.class, roleInfo);
        final JpaSorRoleImpl jpaRole = new JpaSorRoleImpl((JpaRoleInfoImpl) roleInfo, this);
        this.roles.add(jpaRole);
        return jpaRole;
    }

    public void addRole(final SorRole role){
        this.roles.add(role);
        Assert.isInstanceOf(JpaSorRoleImpl.class, role);
        ((JpaSorRoleImpl)role).moveToPerson(this);
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

    public synchronized void removeRole(final SorRole role){
        this.roles.remove(role);
    }

    public synchronized void removeAllRoles(){
        this.roles.clear();
    }

}
