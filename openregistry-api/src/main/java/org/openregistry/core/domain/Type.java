package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * Represents the various "types" that can be in the system.  Examples include "Termination", which would include
 * descriptions such as "Fired", "Retired", "Terminated", etc. or "Address" such as "Campus", "Home", "Office", etc.
 * <p>
 * The System defines particular types, enumerated in the {@link org.openregistry.core.domain.Type.DataTypes} enumeration.
 * However, the system is flexible enough that other types can be created and used.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Type extends Serializable {

    /**
     * The default list of enumerations that the system understands.
     */
    enum DataTypes {ADDRESS, AFFILIATION, CAMPUS, EMAIL, LEAVE, NAME, ORGANIZATIONAL_UNIT, PERSON_STATUS, 
    	PHONE, REGION, SOR, SPONSOR, STATUS, TERMINATION, TEST}

    /**
     * The internal identifier of the type.
     *
     * @return the internal identifier of the type.  CANNOT be NULL.
     */
    Long getId();

    /**
     * The specific data type.  Should be one of the enumerated values, but does not have to be.
     *
     * @return the data type. CANNOT be NULL.
     */
    String getDataType();

    /**
     * The textual description, for human readability.
     * 
     * @return the textual description. CANNOT be NULL.
     */
    String getDescription();

    
}
