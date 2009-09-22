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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.support.MessageSourceAccessor;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.*;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.aspect.OpenRegistryMessageSourceAccessor;

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

    @Autowired(required=true)
    private PersonRepository personRepository;

    // TODO this should not be used
    protected final MessageSourceAccessor msa = OpenRegistryMessageSourceAccessor.getMessageSourceAccessor();

    public String moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson sorPerson) {
        if (getPersonService().findByPersonIdAndSorIdentifier(toPerson.getId(), sorPerson.getSourceSor()) != null){
            return msa.getMessage("matchingSorFound");
        }
        if (getPersonService().moveSystemOfRecordPerson(fromPerson, toPerson, sorPerson)){
            return msa.getMessage("splitSuccess");
        } else {
            return msa.getMessage("splitFailure");
        }
    }

    public String moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson sorPerson) {
        if (getPersonService().moveSystemOfRecordPersonToNewPerson(fromPerson, sorPerson)){
            return msa.getMessage("splitSuccess");
        } else {
            return msa.getMessage("splitFailure");
        }
    }

    public String moveAllSystemOfRecordPerson(Person fromPerson, Person toPerson){
        List<SorPerson> sorPersonListFrom =  personRepository.getSoRRecordsForPerson(fromPerson);

        for (final SorPerson sorPersonFrom : sorPersonListFrom) {
            if (getPersonService().findByPersonIdAndSorIdentifier(toPerson.getId(), sorPersonFrom.getSourceSor()) != null){
                logger.info("PersonAction: MoveAllSystemOfRecordPersons: matchingSorFound"+ sorPersonFrom.getSourceSor());
                return msa.getMessage("matchingSorFound");
            }
        }

        logger.info("PersonAction: MoveAllSystemOfRecordPersons: Proceeding to do moveAllSystemOfRecord");

        if (getPersonService().moveAllSystemOfRecordPerson(fromPerson, toPerson)){
            return msa.getMessage("joinSuccess");
        } else {
            return msa.getMessage("joinFailure");
        }
      }
}