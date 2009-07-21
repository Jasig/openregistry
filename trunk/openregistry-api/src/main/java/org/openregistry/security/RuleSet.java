package org.openregistry.security;

import java.util.Set;

/**
 * Represents a set of rules and the person or thing they apply to.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface RuleSet extends PermissionSet, Privilege {

    /**
     * Consolidates the permissions held by a ruleset into the set of rules.
     * @return the set of rules. CANNOT be NULL.  CAN be EMPTY, though that doesn't make much sense.
     */
    Set<Rule> getRules();
}
