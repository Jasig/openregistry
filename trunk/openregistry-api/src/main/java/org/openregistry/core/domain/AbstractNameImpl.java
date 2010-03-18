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

/**
 * Provides a common implementation of the sameAs method.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractNameImpl implements Name {

    public final boolean sameAs(final SorName name) {
        if (name == null) {
            return false;
        }

        if (compareEqualityOfTwoStrings(getPrefix(), name.getPrefix())) {
            if (compareEqualityOfTwoStrings(getGiven(), name.getGiven())) {
                if (compareEqualityOfTwoStrings(getMiddle(), name.getMiddle())) {
                    if (compareEqualityOfTwoStrings(getFamily(), name.getFamily())) {
                        if (compareEqualityOfTwoStrings(getSuffix(), name.getSuffix())) {
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
}
