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
