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

/**
 * Represents a region within a nation state (country). Some countries/nation states may not have regions.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Region extends Serializable {

    /**
     * The name of the region.
     *
     * @return the name, cannot be NULL.
     */
    String getName();

    /**
     * The code for the region.
     *
     * @return the code for the region.  CANNOT be NULL.
     */
    String getCode();

    /**
     * Refers to the country this region is valid for.
     *
     * @return the country this region is valid for.  CANNOT be NULL.
     */
    Country getCountry();
}
