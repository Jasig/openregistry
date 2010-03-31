package org.openregistry.core.service;

import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class DefaultPreferredPhoneContactFieldSelector implements FieldElector<Phone> {

    @Override
    public Phone elect(final SorPerson newestPerson, final List<SorPerson> sorPersons, boolean deletion) {
        final List<SorPerson> people = new ArrayList<SorPerson>();

        if (newestPerson != null) {
            people.add(newestPerson);
        }

        people.addAll(sorPersons);


        for (final SorPerson sorPerson : people) {
            for (final SorRole role: sorPerson.getRoles()) {
                if (!role.getPhones().isEmpty()) {
                    return role.getPhones().iterator().next();
                }
            }
        }

        return null;
    }
}
