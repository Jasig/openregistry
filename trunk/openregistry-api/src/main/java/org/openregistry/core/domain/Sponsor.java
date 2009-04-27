package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Sponsor extends Serializable {
	
	/**
     * Defines the type of Sponsor this is, i.e. person, role, organizational unit, etc.
     * @return the type.  CANNOT be null.
     */
    Type getType();

    /**
     * The actual Sponsor ID value.
     * @return the Sponsor ID value.  CANNOT be null.
     */
    Long getSponsorId();

    /**
     * Sets the type of this Sponsor.
     *
     * @param type the type of the Sponsor.  Cannot be NULL.
     */
    void setType(Type type);

    /**
     * Sets the Sponsor ID value.
     * @param id the Sponsor ID value.  CANNOT be null.
     */
    void setSponsorId(Long id);
    
    List<Role> getRoles();
    
    void addRole(Role role);

}
