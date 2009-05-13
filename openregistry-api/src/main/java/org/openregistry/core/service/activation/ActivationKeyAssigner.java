package org.openregistry.core.service.activation;

import org.openregistry.core.domain.Identifier;

/**
 * Assigns an activation key to an identifier.   The activation key is used to activate the identifier (i.e. netid)
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ActivationKeyAssigner {

    /**
     * Adds the identifier to the particular person.  This is not necessarily saved until an explicit save call is made
     * so identifier creation systems should not rely on the identifier table in OR to check whether a value
     * was used or not.
     *
     * @param identifier the activation key is used to activiate this identifier.
     */
    void addActivationKeyTo(Identifier identifier);

    /**
     * Returns the type of activation key. Activation keys are associated with a particular identifier.
     * @return identifer type
     */
    String getActivationKeyType();
}