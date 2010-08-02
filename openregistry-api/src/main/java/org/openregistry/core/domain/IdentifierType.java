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
import java.util.regex.Pattern;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierType extends Serializable {

    /**
     * The short name of the identifier.
     *
     * @return the short name of the identifier.  Cannot be NULL.
     */
    String getName();

    /**
     * A text description that can be used for tool tips, etc.
     *
     * @return the text description.  Cannot be null.
     */
    String getDescription();

    /**
     * The format of the identifier.  Should be a Java-compatible regular expression.
     *
     * @return the format.  Cannot be null.
     */
    String getFormatAsString();

    /**
     * Returns the format as a Pattern.
     *
     * @return the pattern.  CANNOT be NULL.
     */
    Pattern getFormatAsPattern();


    /**
     * Determines whether this values is for internal consumption or if it should be published in a public directory, etc.
     *
     * @return true if it should be, false otherwise.
     */
    boolean isPrivate();

    /**
     * Determines whether one is allowed to change the identifier for any reason other than administrative mistake.
     *
     * @return true if you can, false otherwise.
     */
    boolean isModifiable();

    /**
     * Determines whether this type of identifier has been deprecated and deleted.
     *
     * @return true if it was, false otherwise.  If its deleted, no new values should be assigned from it.
     */
    boolean isDeleted();

    
    /**
     * Determines whether this type of identifier supports notifying the Person of its creation.
     *
     * @return true if notifications are supported, false otherwise.
     */
    boolean isNotifiable();
    
}
