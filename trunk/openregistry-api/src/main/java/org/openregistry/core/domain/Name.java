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

import org.openregistry.core.domain.sor.SorName;

import java.io.Serializable;

/**
 * Represents a calculated name.  With a calculated name, there is very little that can change, thus the minimum
 * set of setters.
 * <p>
 * Also, shares a link back to the parent SoR Name.
 * 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Name extends Serializable {

    Long getId();
    
    Type getType();

    String getPrefix();

    String getGiven();

    String getMiddle();

    String getFamily();

    String getSuffix();
    
    void setOfficialName(boolean officialName);
    
    boolean isOfficialName();

    void setPreferredName(boolean preferredName);
    
    boolean isPreferredName();
    
    String getFormattedName();

    String getLongFormattedName();
    
    String toString();

    boolean sameAs(SorName name);

    Long getSourceNameId();

    void setSourceNameId(Long sourceId);

    /**
     * Returns a value to determine similarities.  One example is the SoundEx algorithm is defined here:
     *
     * http://en.wikipedia.org/wiki/Soundex
     *
     * Other options include:
     * http://en.wikipedia.org/wiki/Double_Metaphone
     * 
     * @return the value.  CAN ONLY be NULL if neither Given or Family name is not set.
     */
    String getComparisonValue();
}
