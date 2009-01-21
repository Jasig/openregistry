package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 * @since 1.0
 * @TODO: define all the public APIs pertaining to Role's exposed properties and relationships with other entities
 */
public interface Role extends Serializable {

    String getTitle();
    
}
