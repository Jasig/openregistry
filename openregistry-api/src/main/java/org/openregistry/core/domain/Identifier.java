/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
     * @return the creation date.
     */
    Date getCreationDate();

    /**
     * The date the identifier was deleted.  CANNOT be null if deleted is true.
     * @return the deletion date, if deleted is true.  This should be set when isDeleted is called, and not separately.
     */
    Date getDeletedDate();
   
    void setPrimary(boolean value);

    /**
     * Sets whether the identifier is deleted or not.  Setting this to true should set the deletion date.  Unsetting it
     * should remove the deletion date.
     *
     * @param value whether it was deleted or not.
     */
    void setDeleted(boolean value);
}
