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

package org.openregistry.core.service.reconciliation;

/**
 * Explains how a particular field was matched for reconciliation.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface FieldMatch {

    /**
     * The type of matching that was applied to this field.
     */
    enum MatchType {EXACT, FUZZY, TRANSPOSED}

    /**
     * Returns the name of the field, in particular the path to the field (i.e. names[0].given).
     * @return the path to the field. CANNOT be NULL.
     */
    String getFieldName();

    /**
     * The type of matching, as defined above.
     * @return the type of matching.  CANNOT be NULL.
     */
    MatchType getMatchType();
}
