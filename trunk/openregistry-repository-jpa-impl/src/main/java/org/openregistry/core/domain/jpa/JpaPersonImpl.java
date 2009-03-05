package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.*;
import org.hibernate.envers.Audited;
import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

/**
 * Person entity mapped to a persistence store with JPA annotations.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="person")
@Table(name="prc_persons")
@Audited
@ValidateDefinition
public class JpaPersonImpl extends Entity implements Person {

    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_person_seq")
    @SequenceGenerator(name="prs_person_seq",sequenceName="prs_person_seq",initialValue=1,allocationSize=50)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person",fetch = FetchType.EAGER)    
    private Collection<JpaRoleImpl> roles = new ArrayList<JpaRoleImpl>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person")
    private List<JpaIdentifierImpl> identifiers = new ArrayList<JpaIdentifierImpl>();

    @OneToOne(optional=false)
    @JoinColumn(name="preferred_name_id")
    private JpaNameImpl preferredName;

    @OneToOne(optional=false)
    @JoinColumn(name="official_name_id")
    private JpaNameImpl officialName;

    @Column(name="date_of_birth",nullable=false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false)
    private String gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sponsor")
    private List<JpaRoleImpl> sponsoredRoles;

    public Long getId() {
        return this.id;
    }

    public Name getOfficialName() {
        return this.officialName;
    }

    public Name addOfficialName(){
        this.officialName = new JpaNameImpl();
        this.officialName.setOfficialPerson(this);
        return this.officialName;
    }

    public String getFormattedNameAndID(){
        final StringBuilder builder = new StringBuilder();
        builder.append(this.officialName.getFormattedName());
        builder.append(" ID:");
        builder.append(this.id);
        return builder.toString();
    }

    public Name getPreferredName() {
        return this.preferredName;
    }

    public Name addPreferredName(){
        this.preferredName = new JpaNameImpl();
        this.preferredName.setPreferredNamePerson(this);
        return this.preferredName;
    }

    @JvGroup
    @NotEmpty(customCode="genderRequiredMsg")
    public String getGender() {
        return this.gender;
    }

    @JvGroup
    @NotNull (customCode="dateOfBirthRequiredMsg")
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public Role addRole(final RoleInfo roleInfo) {
        if (!(roleInfo instanceof JpaRoleInfoImpl)) {
            throw new IllegalArgumentException("roleInfo of type JpaRoleInfoImpl required.");
        }
        final JpaRoleImpl jpaRole = new JpaRoleImpl((JpaRoleInfoImpl) roleInfo, this);
        this.roles.add(jpaRole);

        return jpaRole;
    }

    public Collection<? extends Role> getRoles() {
        return this.roles;
    }

    public List<? extends Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public Identifier addIdentifier(){
        final JpaIdentifierImpl jpaIdentifier = new JpaIdentifierImpl(this);
        this.identifiers.add(jpaIdentifier);
        return jpaIdentifier;
    }

}
