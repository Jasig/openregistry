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

import org.openregistry.core.domain.Type;

/**
 * Represents a name as defined by an SoR.  These names are user-modifiable, thus why they expose
 * setters for all of their properties.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public interface SorName {

    void setType(Type type);

    void setPrefix(String prefix);

    void setGiven(String given);

    void setMiddle(String middle);

    void setFamily(String family);

    void setSuffix(String suffix);

    Long getId();

    Type getType();

    String getPrefix();

    String getGiven();

    String getMiddle();

    String getFamily();

    String getSuffix();

    String getFormattedName();

    String getLongFormattedName();

    String toString();
}
