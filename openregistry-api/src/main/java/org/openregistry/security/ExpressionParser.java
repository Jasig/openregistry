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

import org.openregistry.core.domain.Person;

/**
 * Parses the Security Expressions.
 *
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface ExpressionParser {

    /**
     * Returns true if the provided expression matches the provided person.
     *
     * @param person the person to check.  CANNOT be NULL.
     * @param expression the expression to execute.  CANNOT be NULL.
     * @return true if it matches, false otherwise.
     */
    boolean matches(Person person, String expression);

    /**
     * Returns true if the provided expression matches the provided resource.
     *
     * @param resource the resource to check (i.e. person[id=5].addresses[0])
     * @param resourceExpression the expression to see if it matches.
     *
     * @return true if it matches, false otherwise.
     */
    boolean matches(String resource, String resourceExpression);
}
