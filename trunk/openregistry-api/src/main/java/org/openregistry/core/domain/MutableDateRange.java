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

import java.util.Date;

/**
 * Extends the date range to allow for the range to be immutable.
 * <p>
 * Impementers should ensure that START is always BEFORE END.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface MutableDateRange extends DateRange {

    /**
     * Sets the start date.
     *
     * @param date the start date.
     */
    void setStart(Date date);

    /**
     * Sets the end date.
     *
     * @param date the end date
     */
    void setEnd(Date date);
}
