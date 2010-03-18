package org.openregistry.core.service;

import org.openregistry.core.domain.sor.SorPerson;

import java.util.Date;
import java.util.List;

/**
 * Example Date Field Selector for Date of Birth.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public final class DefaultBirthDateFieldElector implements FieldElector<Date> {

    @Override
    public Date elect(final SorPerson newestPerson, final List<SorPerson> sorPersons, final boolean deleted) {
        if (newestPerson != null && newestPerson.getDateOfBirth() != null) {
            return newestPerson.getDateOfBirth();
        }

        if (sorPersons.isEmpty()) {
            return null;
        }

        return sorPersons.get(0).getDateOfBirth();
    }
}
