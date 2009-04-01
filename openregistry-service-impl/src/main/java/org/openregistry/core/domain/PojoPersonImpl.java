package org.openregistry.core.domain;

import java.util.Collection;
import java.util.List;
import java.util.Date;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class PojoPersonImpl implements Person {

    private Long id;

    private List<? extends Role> roles;

    private String gender;

    private Date dateOfBirth;

    private List<? extends Identifier> identifiers;

    private Name preferredName = new PojoNameImpl();

    public Long getId() {
        return this.id;
    }

    public Collection<? extends Role> getRoles() {
        return this.roles;
    }

    public Role addRole(final RoleInfo roleInfo) {
        throw new UnsupportedOperationException("addRole is currently not supported.");
    }

    public List<? extends Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public Name getPreferredName() {
        return this.preferredName;
    }

    public Name getOfficialName() {
        return null;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Identifier addIdentifier() {
        throw new UnsupportedOperationException("currently unsupported");
    }

    public Name addOfficialName() {
        throw new UnsupportedOperationException("currently unsupported");
    }

    public Name addPreferredName() {
        throw new UnsupportedOperationException("currently unsupported");
    }
}
