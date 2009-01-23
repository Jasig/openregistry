package org.openregistry.core.domain;

import java.util.Set;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

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
     * Get a collection of roles associated with this person
     *
     * @return a collection of rolles the person currently has or an empty collection.
     *         Note to implementers: this method should never return null.
     */
    Collection<? extends Role> getRoles();

    /**
     * Add a new role to a collection of role this person holds.
     *
     * @return role new role to add to this person instance.  NEVER returns null.
     */
    Role addRole();

    /**
     * Get identifiers associated with this person.
     *
     * @return PersonIdentifiers. Please note that this method should never return null.
     */
    List<? extends Identifier> getIdentifiers();

    /**
     * Get the name of the person.
     *
     * @return the name of the person, never null.
     */
    Name getName();

    /**
     * The gender of the person.  Current restrictions include only M or F.
     * @return the gender, never null.
     */
    String getGender();

    /**
     * The date of birth, never null.
     * @return the date of birth.
     */
    Date getDateOfBirth();
}
