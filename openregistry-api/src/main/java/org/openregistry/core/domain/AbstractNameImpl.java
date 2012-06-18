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

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang.StringUtils;
import org.openregistry.core.domain.sor.SorName;

/**
 * Abstract name class to support Soundex and Same As.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractNameImpl implements Name {

    protected final String generateSoundEx(final String comparison) {
        final DoubleMetaphone dmp = new DoubleMetaphone();
        return dmp.encode(comparison);
    }

   public final boolean sameAs(final SorName name) {
        if (name == null) {
            return false;
        }

        if (compareEqualityOfTwoStrings(convertEmptyToNull(getPrefix()), convertEmptyToNull(name.getPrefix()))) {
            if (compareEqualityOfTwoStrings(convertEmptyToNull(getGiven()),convertEmptyToNull( name.getGiven()))) {
                if (compareEqualityOfTwoStrings(convertEmptyToNull(getMiddle()), convertEmptyToNull(name.getMiddle()))) {
                    if (compareEqualityOfTwoStrings(convertEmptyToNull(getFamily()),convertEmptyToNull( name.getFamily()))) {
                        if (compareEqualityOfTwoStrings(convertEmptyToNull(getSuffix()),convertEmptyToNull( name.getSuffix()))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean compareEqualityOfTwoStrings(final String string1, final String string2) {
        if (string1 == null && string2 == null) {
            return true;
        }

        if (string1 == null || string2 == null) {
            return false;
        }

        return string1.equals(string2);
    }
    public String convertEmptyToNull(String s){
       if( StringUtils.isNotBlank(s))
           return s;
        else
           return null;

    }
}
