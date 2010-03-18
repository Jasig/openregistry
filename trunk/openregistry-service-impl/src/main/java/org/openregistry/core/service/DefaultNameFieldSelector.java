/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.service;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Lame implementation that just selects a name.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public final class DefaultNameFieldSelector implements FieldElector<SorName> {

    @Override
    public SorName elect(SorPerson newestPerson, List<SorPerson> sorPersons, final boolean deleted) {
        if (newestPerson != null) {
            return getName(newestPerson);
        }

        return getName(sorPersons.get(0));
    }

    protected SorName getName(final SorPerson sorPerson) {
        for (final SorName name : sorPerson.getNames()) {
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
