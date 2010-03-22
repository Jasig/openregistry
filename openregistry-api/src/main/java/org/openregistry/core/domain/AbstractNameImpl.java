package org.openregistry.core.domain;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;
import org.openregistry.core.domain.sor.SorName;

/**
 * Abstract name class to support Soundex and Same As.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractNameImpl implements Name {

    protected final String generateSoundEx() {
        final DoubleMetaphone dmp = new DoubleMetaphone();
        return dmp.encode((getGiven() + " " + getFamily()).trim());
    }

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
