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
package org.openregistry.core.web.resources;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 1.0
 */
public final class PeopleResourceUtils {

    private PeopleResourceUtils() {
    }

    public static ReconciliationCriteria buildReconciliationCriteriaFrom(final PersonRequestRepresentation request, final ObjectFactory<ReconciliationCriteria> factory, final ReferenceRepository referenceRepository) {
        final ReconciliationCriteria rc = factory.getObject();
        rc.getSorPerson().setSourceSor(request.systemOfRecordId);
        rc.getSorPerson().setSorId(request.systemOfRecordPersonId);

        for (final PersonRequestRepresentation.Name requestName : request.names) {
            final Name name = rc.getSorPerson().addName();
            updateName(name, requestName, referenceRepository);
        }
        rc.getSorPerson().setDateOfBirth(request.dateOfBirth);
        rc.getSorPerson().setSsn(request.ssn);
        rc.getSorPerson().setGender(request.gender);
        
        if (request.reconciliation != null) {
            if (request.reconciliation.address != null){
                rc.setAddressLine1(request.reconciliation.address.addressLine1);
                rc.setAddressLine2(request.reconciliation.address.addressLine2);
                rc.setCity(request.reconciliation.address.city);
                rc.setRegion(request.reconciliation.address.region);
                rc.setPostalCode(request.reconciliation.address.postalCode);
            }
            if (!request.reconciliation.emails.isEmpty()) {
                rc.setEmailAddress(request.reconciliation.emails.get(0).email);
            }
            if (!request.reconciliation.phones.isEmpty()) {
                rc.setPhoneNumber(request.reconciliation.phones.get(0).phoneNumber);
            }

            if (!request.reconciliation.identifiers.isEmpty()) {
                for (final PersonRequestRepresentation.Reconciliation.Identifier identifier : request.reconciliation.identifiers) {
                    final IdentifierType identifierType = referenceRepository.findIdentifierType(identifier.identifierType);
                    Assert.notNull(identifierType);
                    rc.getIdentifiersByType().put(identifierType, identifier.identifierValue);
                }
            }
        }
        return rc;
    }

    private static void updateName(final Name name, final PersonRequestRepresentation.Name n, final ReferenceRepository referenceRepository) {
        name.setFamily(n.lastName);
        name.setGiven(n.firstName);
        name.setMiddle(n.middleName);
        name.setSuffix(n.suffix);
        name.setPrefix(n.prefix);
        final Type type = processNameType(referenceRepository, n.nameType);
        name.setType(type);
    }

    public static void buildModifiedSorPerson(final PersonRequestRepresentation request, final SorPerson sorPerson, final ReferenceRepository referenceRepository) {
        sorPerson.setDateOfBirth(request.dateOfBirth);
        sorPerson.setSsn(request.ssn);
        sorPerson.setGender(request.gender);
        sorPerson.getNames().clear();

        for (final PersonRequestRepresentation.Name n : request.names) {
            final Name name = sorPerson.addName();
            updateName(name, n, referenceRepository);
        }

    }

    protected static Type processNameType(final ReferenceRepository referenceRepository, final String nameType) {
        if (nameType != null) {
            final Type type = referenceRepository.findType(Type.DataTypes.NAME, nameType);
            if (type != null) {
                return type;
            }
        }
  
        //Simplifying this. Default is Formal.  If more than one name is specified without a type, error will be dealt with
        //during validation.
        return referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL);
    }

}
