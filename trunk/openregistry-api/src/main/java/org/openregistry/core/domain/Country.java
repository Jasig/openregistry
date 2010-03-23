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

import java.util.List;
import java.io.Serializable;

/**
 * Refers to a nation-state.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Country extends Serializable {

    /**
     * The internal identifier for this country.
     *
     * @return the internal identifier.  CANNOT be NULL.
     */
    Long getId();

    /**
     * The code representing this country.
     * @return the code, CANNOT be NULL.
     */
    String getCode();

    /**
     * The internationalized name of the country.
     *
     * @return the name of the country. CANNOT be NULL.
     */
    String getName();

    /**
     * The list of regions associated with this country.
     * @return the list of regions associated with this country. CANNOT be NULL.  CAN be EMPTY.
     */
    List<Region> getRegions();
}
