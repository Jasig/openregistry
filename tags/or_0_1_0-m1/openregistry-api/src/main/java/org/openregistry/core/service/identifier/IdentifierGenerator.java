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
package org.openregistry.core.service.identifier;

/**
 * This interface is used when the Identifier is not necessarily dependent on anything provided by the person.
 * <p>
 * Calling each one will always increment the value.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierGenerator {

    /**
     * Generates the next value in the generator and returns it as a long.
     *
     * @return the next value as a long.
     */
    long generateNextLong();

    /**
     * Generates the next value as a String.
     *
     * @return the next value as a string.
     */
    String generateNextString();

    /**
     * Generates the next value as an integer.
     *
     * @return the next value as an integer.
     */
    int generateNextInt();
}
