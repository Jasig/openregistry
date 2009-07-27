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

    /**
     * Returns true if the provided expression matches the provided resource.
     *
     * @param resource the resource to check (i.e. person[id=5].addresses[0])
     * @param resourceExpression the expression to see if it matches.
     *
     * @return true if it matches, false otherwise.
     */
    boolean matches(String resource, String resourceExpression);
}
