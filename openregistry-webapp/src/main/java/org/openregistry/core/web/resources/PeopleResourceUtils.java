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
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.web.resources.representations.PersonRequestRepresentation;
import org.openregistry.core.web.resources.representations.PersonModifyRepresentation;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.Assert;

/**
 * @since 1.0
 */
public final class PeopleResourceUtils {

    private PeopleResourceUtils() {
    }

    public static ReconciliationCriteria buildReconciliationCriteriaFrom(final PersonRequestRepresentation request,
                                                                         ObjectFactory<ReconciliationCriteria> factory,
                                                                         ReferenceRepository referenceRepository) {
        final ReconciliationCriteria rc = factory.getObject();
        rc.getSorPerson().setSourceSor(request.systemOfRecordId);
        rc.getSorPerson().setSorId(request.systemOfRecordPersonId);

        boolean hasLegalorFormalNameType = hasLegalorFormalNameType(request);
        for (final PersonRequestRepresentation.Name requestName : request.names) {
            final Name name = rc.getSorPerson().addName();
            name.setGiven(requestName.firstName);
            name.setFamily(requestName.lastName);
            name.setMiddle(requestName.middleName);
            name.setPrefix(requestName.prefix);
            name.setSuffix(requestName.suffix);
            Type type = processNameType(referenceRepository, hasLegalorFormalNameType, requestName.nameType);
            name.setType(type);
            if (type.equals(Type.NameTypes.FORMAL)) hasLegalorFormalNameType = true;
        }
        rc.getSorPerson().setDateOfBirth(request.dateOfBirth);
        rc.getSorPerson().setSsn(request.ssn);
        rc.getSorPerson().setGender(request.gender);
        
        if (request.reconciliation != null){
            if (request.reconciliation.address != null){
                rc.setAddressLine1(request.reconciliation.address.addressLine1);
                rc.setAddressLine2(request.reconciliation.address.addressLine2);
                rc.setCity(request.reconciliation.address.city);
                rc.setRegion(request.reconciliation.address.region);
                rc.setPostalCode(request.reconciliation.address.postalCode);
            }
            if (!request.reconciliation.emails.isEmpty())
                rc.setEmailAddress(request.reconciliation.emails.get(0).email);
            if (!request.reconciliation.phones.isEmpty())
                rc.setPhoneNumber(request.reconciliation.phones.get(0).phoneNumber);

            if (!request.reconciliation.identifiers.isEmpty()){
                for (final PersonRequestRepresentation.Reconciliation.Identifier identifier : request.reconciliation.identifiers) {
                    final IdentifierType identifierType = referenceRepository.findIdentifierType(identifier.identifierType);
                    Assert.notNull(identifierType);
                    rc.getIdentifiersByType().put(identifierType, identifier.identifierValue);
                }
            }
        }
        return rc;
    }

    public static Type processNameType(ReferenceRepository referenceRepository, boolean hasLegalorFormalNameType, String nameType){
        if (nameType != null && referenceRepository.findType(Type.DataTypes.NAME, nameType) != null) {
            return referenceRepository.findType(Type.DataTypes.NAME, nameType);
        }
        //TODO Default is Formal unless already have a name marked Formal or Legal?
        if (hasLegalorFormalNameType) return referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.AKA);
        return referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL);
    }

    protected static boolean hasLegalorFormalNameType(final PersonRequestRepresentation request) {
        for (final PersonRequestRepresentation.Name n : request.names)
            if (n.nameType != null && (n.nameType.equals(Type.NameTypes.LEGAL) || n.nameType.equals(Type.NameTypes.FORMAL))) return true;
        return false;
    }

    public static boolean hasLegalorFormalNameType(final PersonModifyRepresentation personRepresentation) {
        for (final PersonModifyRepresentation.Name n : personRepresentation.names)
            if (n.nameType != null && (n.nameType.equals(Type.NameTypes.LEGAL) || n.nameType.equals(Type.NameTypes.FORMAL))) return true;
        return false;
    }
}
