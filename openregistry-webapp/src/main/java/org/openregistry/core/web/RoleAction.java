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
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.domain.*;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.repository.ReferenceRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Named("roleAction")
public final class RoleAction extends AbstractPersonServiceAction {

    private final ReferenceRepository referenceRepository;

    @Inject
    public RoleAction(final PersonService personService, final ReferenceRepository referenceRepository) {
        super(personService);
        this.referenceRepository = referenceRepository;
    }

    public SorRole initSorRole(final SorPerson sorPerson, final String roleInfoCode) {
        final RoleInfo roleInfo = referenceRepository.getRoleInfoByCode(roleInfoCode);
        return addRole(sorPerson, roleInfo);
    }

     /**
     * Add and initialize new role.
     * @param sorPerson
     * @param roleInfo
     * @return sorRole
     */
    protected SorRole addRole(final SorPerson sorPerson, final RoleInfo roleInfo){
        final SorRole sorRole = sorPerson.addRole(roleInfo);
        sorRole.setSourceSorIdentifier(AbstractPersonServiceAction.STATIC_SOR_NAME);
        sorRole.setPersonStatus(referenceRepository.findType(Type.DataTypes.STATUS, Type.PersonStatusTypes.ACTIVE));
        final EmailAddress emailAddress = sorRole.addEmailAddress();
        emailAddress.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.CAMPUS));
        final Phone phone = sorRole.addPhone();
        phone.setPhoneType(referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.CELL));
        phone.setAddressType(referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.CAMPUS));
        final Address address = sorRole.addAddress();
        address.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.CAMPUS));
        final SorSponsor sponsor = sorRole.getSponsor();
        sponsor.setType(referenceRepository.findType(Type.DataTypes.SPONSOR, Type.SponsorTypes.PERSON));  // TODO handle other types OR-57

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
        final Person person = getPersonService().findPersonById(sorPerson.getPersonId());
        if (person.pickOutRole(roleInfoCode) != null){
            context.addMessage(new MessageBuilder().error().code("roleAlreadyExists").build());
            return false;
        }
        return true;
    }

    public boolean addSorRole(final SorPerson sorPerson, final SorRole sorRole, final MessageContext context) {
        final ServiceExecutionResult<SorRole> result = getPersonService().validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        return convertAndReturnStatus(result, context, null);
    }

    public boolean updateSorRole(final SorPerson sorPerson, final SorRole role, final MessageContext context) {
        final ServiceExecutionResult<SorRole> result = getPersonService().updateSorRole(sorPerson, role);
        return convertAndReturnStatus(result, context, "roleUpdated");
    }

    public boolean expireRole(final SorRole role, final MessageContext context) {
        boolean result = this.getPersonService().expireRole(role);
        return true;
    }

    public boolean renewRole(final SorRole role, final MessageContext context) {
        boolean result = this.getPersonService().renewRole(role);
        return true;
    }
}
