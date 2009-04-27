package org.openregistry.core.domain;

import java.util.Set;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

import org.openregistry.core.domain.sor.SorRole;

/**
 * Entity representing canonical persons and their identity in the Open Registry system.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Person extends Serializable {

    /**
     * Unique identifier for this person.
     *
     * @return the unique identifier for this person.
     */
    Long getId();

    /**
     * Returns the set of names the System of Record knows about the Person.
     * <p/>
     * There MUST be at least ONE name returned.
     *
     * @return the names, minimum of one.  CANNOT be null.
     */
    Set<? extends Name> getNames();

    /**
     * Adds an EMPTY name to the collection of names.  These names should then be populated.
     * 
     * @return an EMPTY name.  CANNOT return NULL.
     */
    Name addName();

    /**
     * Get a collection of roles associated with this person
     *
     * @return a collection of roles the person currently has or an empty collection.
     *         Note to implementers: this method should never return null.
     */
    List<Role> getRoles();

    /**
     * Add a new role to a collection of roles this person holds.
     *
     * @param roleInfo the basic role information about this role (not customizable)
     * @return role new role to add to this person instance.  NEVER returns null.
     */
    Role addRole(RoleInfo roleInfo);
 
    /**
     * Add a new role to a collection of roles this person holds and populate it with the data from the given sorRole.
     *
     * @param roleInfo the basic role information about this role (not customizable)
     * @param sorRole the sor role to populate the data from
     * @return role new role to add to this person instance.  NEVER returns null.
     */
    Role addRole(RoleInfo roleInfo, SorRole sorRole);

    /**
     * Get identifiers associated with this person.
     *
     * @return PersonIdentifiers. Please note that this method should never return null.
     */
    Set<Identifier> getIdentifiers();

    /**
     * Get the name of the person.
     *
     * @return the name of the person, never null.
     */
    Name getPreferredName();

    Name getOfficialName();

    /**
     * The gender of the person.  Current restrictions include only M or F.
     *
     * @return the gender, never null.
     */
    String getGender();

    /**
     * The date of birth, never null.
     *
     * @return the date of birth.
     */
    Date getDateOfBirth();

    /**
     * The date of birth, never null.
     *
     * @param dateOfBirth the date of birth.
     */
    void setDateOfBirth(Date dateOfBirth);

    void setGender(String gender);

    Identifier addIdentifier();

    Name addOfficialName();

    Name addPreferredName();

    /**
     * Pick out the specific <code>Role</code> identified by provided role identifier
     * from the collection of this person's roles
     *
     * @return Role of this person for the provided identifier or a null if this person does not have such a role
     */
    Role pickOutRoleByIdentifier(String roleIdentifier);


}
