package org.openregistry.core.service.identifier;

/**
 * This interface is used when the Identifier is not necessarily dependent on anything provided by the person.
 * <p>
 * Calling each one will always increment the value.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierGenerator {

    /**
     * Generates the next value in the generator and returns it as a long.
     *
     * @return the next value as a long.
     */
    long generateNextLong();

    /**
     * Generates the next value as a String.
     *
     * @return the next value as a string.
     */
    String generateNextString();

    /**
     * Generates the next value as an integer.
     *
     * @return the next value as an integer.
     */
    int generateNextInt();
}
