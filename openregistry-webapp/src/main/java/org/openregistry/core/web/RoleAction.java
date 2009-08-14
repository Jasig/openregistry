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

import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.domain.*;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RoleAction {

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required=true)
    private ReferenceRepository referenceRepository;

    @Autowired(required=true)
    private PersonRepository personRepository;

    protected final String ACTIVE_STATUS = "Active";
    protected final String CAMPUS = "Campus";
    protected final String PERSON = "Person";
    protected final String CELL = "Cell";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final SpringErrorValidationErrorConverter converter = new SpringErrorValidationErrorConverter();

    public SorRole initSorRole(SorPerson sorPerson, String roleInfoCode) {

        RoleInfo roleInfo = referenceRepository.getRoleInfoByCode(roleInfoCode);
        final SorRole sorRole = addRole(sorPerson, roleInfo);
        return sorRole;
    }

     /**
     * Add and initialize new role.
     * @param sorPerson
     * @param roleInfo
     * @return sorRole
     */
    protected SorRole addRole(final SorPerson sorPerson, final RoleInfo roleInfo){
        final SorRole sorRole = sorPerson.addRole(roleInfo);
        sorRole.setSorId("1");  // TODO Don't hardcode OR-56
        sorRole.setSourceSorIdentifier("or-webapp"); // TODO Don't hardcode OR-55
        logger.info("status: "+Type.DataTypes.STATUS.name());
        sorRole.setPersonStatus(referenceRepository.findType(Type.DataTypes.STATUS, ACTIVE_STATUS));
        final EmailAddress emailAddress = sorRole.addEmailAddress();
        emailAddress.setAddressType(referenceRepository.findType(Type.DataTypes.EMAIL, CAMPUS));
        final Phone phone = sorRole.addPhone();
        phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, CELL));
        phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, CAMPUS));
        final Address address = sorRole.addAddress();
        address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, CAMPUS));
        final SorSponsor sponsor = sorRole.setSponsor();
        sponsor.setType(referenceRepository.findType(Type.DataTypes.SPONSOR, PERSON));  // TODO handle other types OR-57

        //provide default values for start and end date of role
        final Calendar cal = Calendar.getInstance();
        sorRole.setStart(cal.getTime());
        cal.add(Calendar.MONTH, 6);
        sorRole.setEnd(cal.getTime());
        return sorRole;
    }

    public boolean isRoleNewForPerson(SorPerson sorPerson, String roleInfoCode, MessageContext context) {
        //check if person already has the role to be added.
        logger.info("IsRoleNewForPerson: code:"+ roleInfoCode);
        Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        if (person.pickOutRole(roleInfoCode) != null){
            context.addMessage(new MessageBuilder().error().code("roleAlreadyExists").build());
            return false;
        }
        return true;
    }

    public boolean addSorRole(SorPerson sorPerson, SorRole sorRole, MessageContext context) {

        ServiceExecutionResult result = personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        if (result.succeeded()) {
            return true;
        }
        else {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return false;
        }

    }

    public boolean updateSorRole(SorRole role, MessageContext context) {
        ServiceExecutionResult result = personService.updateSorRole(role);
        if (result.succeeded()) {
            context.addMessage(new MessageBuilder().info().code("roleUpdated").build());
            return true;
        }
        else {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return false;
        }

    }

}
