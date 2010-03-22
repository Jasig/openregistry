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
package org.openregistry.core.web;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.service.MutableSearchCriteriaImpl;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
@Controller
public final class AjaxController {

    @Inject
    private PersonService personService;

    @RequestMapping(value="/nameSearch.htm", method = RequestMethod.GET)
    public void nameSearch(@RequestParam("term") final String term, final Writer writer) throws IOException {
        final MutableSearchCriteriaImpl searchCriteria = new MutableSearchCriteriaImpl();
        searchCriteria.setName(term);

        final Set<String> names = new HashSet<String>();

        final List<PersonMatch> personMatches = this.personService.searchForPersonBy(searchCriteria);

        for (final PersonMatch personMatch : personMatches) {
            final Person person = personMatch.getPerson();
            final Name name = person.getOfficialName();
            names.add(name.getGiven() + " " + name.getFamily());
        }

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        boolean pastFirst = false;
        for (final String name : names) {
            if (pastFirst) {
                stringBuilder.append(",");
            }

            pastFirst = true;

            stringBuilder.append("\"");
            stringBuilder.append(name);
            stringBuilder.append("\"");
        }
        stringBuilder.append("]");

        writer.write(stringBuilder.toString());
    }

}
