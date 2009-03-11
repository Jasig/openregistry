package org.openregistry.core.service.reconciliation;

/**
 * Explains how a particular field was matched for reconciliation.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface FieldMatch {

    enum MatchType {EXACT, FUZZY, TRANSPOSED}

    String getFieldName();

    MatchType getMatchType();
}
