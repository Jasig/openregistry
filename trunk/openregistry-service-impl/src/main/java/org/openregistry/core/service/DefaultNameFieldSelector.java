package org.openregistry.core.service;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Lame implementation that just selects a name.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public final class DefaultNameFieldSelector implements FieldElector<Name> {

    @Override
    public Name elect(SorPerson newestPerson, List<SorPerson> sorPersons, final boolean deleted) {
        if (newestPerson != null) {
            return getName(newestPerson);
        }

        return getName(sorPersons.get(0));
    }

    protected Name getName(final SorPerson sorPerson) {
        for (final Name name : sorPerson.getNames()) {
            if (name.getType().getDataType().equals(Type.NameTypes.FORMAL.name()) || name.getType().getDataType().equals(Type.NameTypes.LEGAL.name())) {
                return name;
            }
        }

        if (!sorPerson.getNames().isEmpty()) {
            return sorPerson.getNames().get(0);
        }

        throw new IllegalStateException("Person has no names. That's really weird.");
    }
}
