package org.openregistry.core.service;

import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Simple implementation of a way to select gender.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public final class DefaultGenderFieldElector implements FieldElector<String> {

    @Override
    public String elect(final SorPerson newestPerson, final List<SorPerson> sorPersons, final boolean deleted) {
        if (newestPerson != null && newestPerson.getGender() != null) {
            return newestPerson.getGender();
        }

        if (sorPersons.isEmpty()) {
            return null;
        }

        return sorPersons.get(0).getGender();
    }
}
