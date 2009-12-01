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
package org.openregistry.core.domain.sor;

/**
 * Represents a System of Record in the system.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface SystemOfRecord {

    enum Interfaces {BATCH, INTERACTIVE, REALTIME}

    /**
     * The String identifier, i.e. "registrar"
     * @return the String identifier, cannot be null.
     */
    String getStringId();

    Interfaces[] allowedInterfaces();
}
