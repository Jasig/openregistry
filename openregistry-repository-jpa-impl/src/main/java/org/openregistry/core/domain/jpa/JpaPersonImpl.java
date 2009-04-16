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
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Set;

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
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_persons_seq")
    @SequenceGenerator(name="prc_persons_seq",sequenceName="prc_persons_seq",initialValue=1,allocationSize=50)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER)
    private Set<JpaNameImpl> names = new HashSet<JpaNameImpl>();
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER)    
    private Collection<JpaRoleImpl> roles = new ArrayList<JpaRoleImpl>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER)
    private Set<JpaIdentifierImpl> identifiers = new HashSet<JpaIdentifierImpl>();

    @Column(name="date_of_birth",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull (customCode="dateOfBirthRequiredMsg")
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=false)
    @NotEmpty(customCode="genderRequiredMsg")
    private String gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sponsor")
    private List<JpaRoleImpl> sponsoredRoles;

    public Long getId() {
        return this.id;
    }
    
    public Set<? extends Name> getNames() {
    	return this.names;
    }
    
    public Name addName() {
    	final JpaNameImpl name = new JpaNameImpl(this);
    	this.names.add(name);
    	return name;    	
    }

    public Name getOfficialName() {
    	Set<? extends Name> names = this.getNames();
    	for(Name name: names) {
    		if (name.isOfficialName()) {
    			return name;
    		}
    	}
    	return null;
    }

    // TODO this should check to see if we have one already
    public Name addOfficialName(){
        final JpaNameImpl name = new JpaNameImpl(this);
        this.names.add(name);
        name.setOfficialName();
        return name;
    }

    public String getFormattedNameAndID(){
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getOfficialName().getFormattedName());
        builder.append(" ID:");
        builder.append(this.id);
        return builder.toString();
    }

    public Name getPreferredName() {
       	Set<? extends Name> names = this.getNames();
    	for(Name name: names) {
    		if (name.isPreferredName()) {
    			return name;
    		}
    	}
    	return null;
    }

    // TODO this should check to see if we have one already
    public Name addPreferredName(){
        final JpaNameImpl name = new JpaNameImpl(this);
        this.names.add(name);
        name.setPreferredName();
        return name;
    }

    public String getGender() {
        return this.gender;
    }

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

    public Set<? extends Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public Identifier addIdentifier(){
        final JpaIdentifierImpl jpaIdentifier = new JpaIdentifierImpl(this);
        this.identifiers.add(jpaIdentifier);
        return jpaIdentifier;
    }
}
