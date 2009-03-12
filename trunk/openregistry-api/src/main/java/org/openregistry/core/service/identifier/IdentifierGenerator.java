package org.openregistry.core.service.identifier;

/**
 * This interface is used when the Identifier is not necessarily dependent on anything provided by the person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierGenerator {

    long generateNextLong();

    String generateNextString();

    int generateNextInt();
}
