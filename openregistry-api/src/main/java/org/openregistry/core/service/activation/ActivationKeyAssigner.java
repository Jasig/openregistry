package org.openregistry.core.service.activation;

import org.openregistry.core.domain.Person;

/**
 * Assigns an activation key to a person.   The activation key is used to activate the person.
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ActivationKeyAssigner {

    /**
     * Adds the activation key to the particular person.
     *
     * @param person the activation key is used to activate this person.
     */
    void addActivationKeyTo(Person person);

}