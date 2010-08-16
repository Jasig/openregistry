package org.openregistry.core.service;

import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class DefaultPreferredEmailContactFieldSelector implements FieldElector<EmailAddress> {

    @Override
    public EmailAddress elect(final SorPerson newestPerson, final List<SorPerson> sorPersons, boolean deletion) {
        final List<SorPerson> people = new ArrayList<SorPerson>();

        if (newestPerson != null) {
            people.add(newestPerson);
        }

        people.addAll(sorPersons);

        
        for (final SorPerson sorPerson : people) {
            for (final SorRole role: sorPerson.getRoles()) {
                if (!role.getEmailAddresses().isEmpty()) {
                    return role.getEmailAddresses().iterator().next();
                }
            }
        }

        return null;
    }
}
