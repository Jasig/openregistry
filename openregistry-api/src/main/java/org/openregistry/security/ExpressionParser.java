package org.openregistry.security;

import org.openregistry.core.domain.Person;

/**
 * Parses the Security Expressions.
 *
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface ExpressionParser {

    /**
     * Returns true if the provided expression matches the provided person.
     *
     * @param person the person to check.  CANNOT be NULL.
     * @param expression the expression to execute.  CANNOT be NULL.
     * @return true if it matches, false otherwise.
     */
    boolean matches(Person person, String expression);
}
