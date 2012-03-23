/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service;


import org.apache.commons.lang.StringUtils;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.Date;
import java.util.Map;

import static java.lang.String.format;

/**
 * Default implementation of <code>IdentifierChangeService</code>
 * @since 1.0
 */
@Named("identifierChangeService")
public class DefaultIdentifierChangeService implements IdentifierChangeService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
     private  PersonRepository personRepository;

    @Transactional(propagation = Propagation.REQUIRED,noRollbackFor = IllegalArgumentException.class)
    public boolean change(Identifier internalId, String changedId) {
        //check if both identifier are of the same type

        if (internalId==null || StringUtils.isEmpty(changedId)){
           throw new IllegalArgumentException("new or old identifer couldn't be null");
        }
        if (internalId.getValue().equals(changedId)) {
            logger.debug("new identifier is the same no need to change existing identifier");
            return false;
        }

         final Person person = this.findPersonByIdentifier(internalId.getType().getName(), internalId.getValue());
        if (person == null)
            throw new IllegalArgumentException(format("The person with the provided identifier [%s] does not exist", internalId.getValue()));

        final Person person2 = this.findPersonByIdentifier(internalId.getType().getName(), changedId);
        if (person2 != null && person.getId() != person2.getId()) {
            throw new IllegalArgumentException(format("The person with the proposed new identifier [%s] already exists.", changedId));
        }
        Map<String, Identifier> primaryIds = person.getPrimaryIdentifiersByType();
        Identifier currId = primaryIds.get(internalId.getType().getName());
        if(currId==null)
            throw new IllegalStateException("Provided Id doesnt exist as primary identifier ");
        if (currId.getValue().equals(changedId)) {
            logger.debug(format("The provided new primary identifier [%s] already assigned to the person.", changedId));
        }
        else if(!currId.getValue().equals(internalId.getValue())) {
            throw new IllegalArgumentException(format("The provided primary identifier [%s] does not match the current primary identifier", internalId.getValue()));
        }
        //check if the provided new identifier is already there, and if so, do the update, otherwise - do the insert.
        Identifier providedId = person.findIdentifierByValue(internalId.getType().getName(), changedId);
        if(providedId == null) {

            providedId = person.addIdentifier(internalId.getType(), changedId);
        }
        providedId.setPrimary(true);
        providedId.setDeleted(false);
        currId.setPrimary(false);
        currId.setDeleted(true);
        return true;
    }
    public Person findPersonByIdentifier(final String identifierType, final String identifierValue) {
        try {
            return this.personRepository.findByIdentifier(identifierType, identifierValue);
        } catch (final Exception e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = IllegalArgumentException.class)
    public void updateChangeable(final Identifier identifierToUpdate,  boolean changeable) {
        final Person person = this.findPersonByIdentifier(identifierToUpdate.getType().getName(), identifierToUpdate.getValue());
        if (person == null)
            throw new IllegalArgumentException(format("The person with the provided identifier [%s] does not exist", identifierToUpdate.getValue()));
        Identifier identifier = person.findIdentifierByValue(identifierToUpdate.getType().getName(), identifierToUpdate.getValue());
        identifier.setChangeable(changeable);
    }

    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = IllegalArgumentException.class)
    public void updateChangeExpDate(final Identifier identifierToUpdate, final Date changeExpDate) {
        final Person person = this.findPersonByIdentifier(identifierToUpdate.getType().getName(), identifierToUpdate.getValue());
        if (person == null)
            throw new IllegalArgumentException(format("The person with the provided identifier [%s] does not exist", identifierToUpdate.getValue()));
        Identifier identifier = person.findIdentifierByValue(identifierToUpdate.getType().getName(), identifierToUpdate.getValue());
        identifier.setChangeExpirationDate(changeExpDate);
    }
}
