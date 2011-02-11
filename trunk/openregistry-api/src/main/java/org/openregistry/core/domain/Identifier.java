/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Identifier extends Serializable {

    IdentifierType getType();

    String getValue();

    /**
     * Defaults to true.
     *
     * @return whether its the primary version of this identifier, or one that exists for historical reasons.
     */
    boolean isPrimary();

    /**
     * Defaults to false.
     *
     * @return whether the identifier is valid or not.
     */
    boolean isDeleted();

    /**
     * The date the identifier was created.  CANNOT be null.
     * <p>
     * This *should* be set at creation time and not actually set.
     * @return a copy of the creation date
     */
    Date getCreationDate();

    /**
     * The date the identifier was created.  CANNOT be null.
     * <p>
     * This *should* be set at creation time when registry is going to create it..
     * it can be set if we are importing data from legacy system and want to keep the origianl creation date and not actually creating identifier
     */
    void setCreationDate(Date originalCreationDate);

    /**
     * The date the identifier was deleted.  CANNOT be null if deleted is true.
     * @return a copy of the deletion date, if deleted is true.  This should be set when isDeleted is called, and not separately.
     */
    Date getDeletedDate();
        
	/**
     * The date the Person was notified about this identifier.  
     * Will be null if the Person has not been notified.
     * This is only applicable to identifiers which have an IdentifierType with isNotifiable() == true
     * @return a copy of the notification date.
     * @throws IllegalStateException if this identifier is not of a notifiable type
     */
    Date getNotificationDate () throws IllegalStateException;

   
    void setPrimary(boolean value);

    /**
     * Sets whether the identifier is deleted or not.  Setting this to true should set the deletion date.  Unsetting it
     * should remove the deletion date.
     *
     * @param value whether it was deleted or not.
     */
    void setDeleted(boolean value);
    
    /**
     * Sets the date on which the notification about this identifier was sent
     * @param date
     * @throws IllegalStateException if trying to set the date on an identifier that is not of a "notifiable" type
     */
    void setNotificationDate(Date date) throws IllegalStateException;
}
