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

package org.openregistry.core.service;

import java.util.Date;

/**
 * Constructs new dates based on an implementation-specific algorithm.  This is generally useful for instances
 * where you need a configurable future date.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface DateGenerator {

    /**
     * Gets a new date based on the implementation specific algorithm.  The starting point for the date is the current
     * date/time.
     *
     * @return the new date.  CANNOT be NULL.
     */
    Date getNewDate();

    /**
     * Gets a new date based on the implementation specific algorithm, but uses the futureDate as the starting point
     * for the calculation.
     *
     * @param futureDate the date to start from.
     * @return the new date CANNOT be NULL.
     */
    Date getNewDate(Date futureDate);
}
