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

package org.openregistry.core.domain.sor;

/**
 * Exception to be thrown if a problem is encountered when adding an SorPerson.  This will happen when an SoR tries to add more than one SorPerson.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class SorPersonAlreadyExistsException extends Exception {

    private final SorPerson sorPerson;

    public SorPersonAlreadyExistsException(final SorPerson sorPerson) {
        super("An error occurred. A person already exists from this SoR linked to this ID!");
        this.sorPerson = sorPerson;
    }

    public SorPerson getSorPerson() {
        return this.sorPerson;
    }

}
