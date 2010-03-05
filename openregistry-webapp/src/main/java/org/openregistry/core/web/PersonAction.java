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

import org.openregistry.core.service.PersonService;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public final class PersonAction extends AbstractPersonServiceAction {

    @Inject
    public PersonAction(final PersonService personService) {
        super(personService);
    }

    // TODO this class erroneously used the OpenRegistryMessageSourceAccessor which it should not have.  That class
    // no longer exists, and thus this class needs to be rewritten.

    public String moveSystemOfRecordPerson(final Person fromPerson, final Person toPerson, final SorPerson sorPerson) {
        if (getPersonService().findByPersonIdAndSorIdentifier(toPerson.getId(), sorPerson.getSourceSor()) != null){
            return "matchingSorFound";
        }
        if (getPersonService().moveSystemOfRecordPerson(fromPerson, toPerson, sorPerson)){
            return "splitSuccess";
        } else {
            return "splitFailure";
        }
    }

    public String moveSystemOfRecordPersonToNewPerson(final Person fromPerson, final SorPerson sorPerson) {
        if (getPersonService().moveSystemOfRecordPersonToNewPerson(fromPerson, sorPerson)){
            return "splitSuccess";
        } else {
            return "splitFailure";
        }
    }

    public String moveAllSystemOfRecordPerson(final Person fromPerson, final Person toPerson){
        List<SorPerson> sorPersonListFrom =  getPersonService().getSorPersonsFor(fromPerson);

        for (final SorPerson sorPersonFrom : sorPersonListFrom) {
            if (getPersonService().findByPersonIdAndSorIdentifier(toPerson.getId(), sorPersonFrom.getSourceSor()) != null){
                logger.info("PersonAction: MoveAllSystemOfRecordPersons: matchingSorFound"+ sorPersonFrom.getSourceSor());
                return "matchingSorFound";
            }
        }

        logger.info("PersonAction: MoveAllSystemOfRecordPersons: Proceeding to do moveAllSystemOfRecord");

        if (getPersonService().moveAllSystemOfRecordPerson(fromPerson, toPerson)){
            return "joinSuccess";
        } else {
            return "joinFailure";
        }
    }
}