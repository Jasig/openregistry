package org.openregistry.core.domain;

import org.openregistry.core.domain.internal.Entity;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractNameImpl implements Name {

    public boolean sameAs(final Name name) {
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
