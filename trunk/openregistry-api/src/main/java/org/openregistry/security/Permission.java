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
package org.openregistry.security;

/**
 * Represents a permission applied to a resource.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Permission {

    enum PermissionType {CREATE, READ, UPDATE, DELETE}

    /**
     * The actual resource we're working with.  In most cases this will be an expression.
     * TODO: detail more about permissions here
     * @return the permission, CANNOT be NULL.
     */
    String getResource();

    /**
     * An optional textual description to provide more information to anyone looking at what the intention of the rule was.
     * @return the description, or null.
     */
    String getDescription();

    /**
     * Determines whether you can create for this permission.
     * <p>
     * NOTE: Applying a "CREATE" permission to anything other than major components (i.e. person.addresses) doesn't make any sense.
     * </p>
     * @return true, if yes, false otherwise.
     */
    boolean canCreate();

    /**
     * Determines whether you can read for this permission.
     *
     * @return true if yes, false otherwise.
     */
    boolean canRead();

    /**
     * Determines whether you can update for this permission.
     *
     * @return true, if yes, false otherwise.
     */
    boolean canUpdate();

    /**
     * Determines whether you can delete or not.
     * <p>
     * For instances of particular attributes, this means making them NULL???.   For addresses and such, it means deleting them.
     *
     * @return true, if yes, false otherwise.
     */
    boolean canDelete();
    
}
