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
 * Represents the person who the permission will be applied to.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
public interface Subject {

    /**
     * These should be in the proper order of specifity 
     */
    enum SubjectType {EVERYONE, AUTHENTICATED, EXPRESSION, USER}

    /**
     * MUST be set if UserType == USER
     * @return the user identifier, or null.
     */
    String getUser();

    /**
     * Must be set if UserType = EXPRESSION.
     *
     * @return the expression, or NULL.
     */
    String getExpression();

    /**
     * Returns the type of permission this is.  I.e. for a user, for everyone, etc.
     *
     * @return the type of permission. CANNOT be NULL.
     */
    SubjectType getSubjectType();

    /**
     * Represents the system of record.  A NULL SoR indicates this permission applies to
     * @return the system of record or NULL, if the permission applies to none.
     */
    SystemOfRecord getSystemOfRecord();
}
