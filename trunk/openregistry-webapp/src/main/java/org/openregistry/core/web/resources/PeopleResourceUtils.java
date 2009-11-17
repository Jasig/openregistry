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
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.Assert;

import javax.ws.rs.core.Response;

/**
 * @since 1.0
 */
public final class PeopleResourceUtils {

    private PeopleResourceUtils() {
    }

    public static Response validate(PersonRequestRepresentation personRequestRepresentation) {
        if (!personRequestRepresentation.checkRequiredData()) {
            //HTTP 400
            return Response.status(Response.Status.BAD_REQUEST).entity("The person entity payload is incomplete.").build();
        }
        //Returns null response indicating that the representation is valid
        return null;
    }

    public static ReconciliationCriteria buildReconciliationCriteriaFrom(final PersonRequestRepresentation request,
                                                                         ObjectFactory<ReconciliationCriteria> factory,
                                                                         ReferenceRepository referenceRepository) {
        final ReconciliationCriteria rc = factory.getObject();
        rc.getSorPerson().setSourceSor(request.systemOfRecordId);
        rc.getSorPerson().setSorId(request.systemOfRecordPersonId);
        final Name name = rc.getSorPerson().addName(referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setGiven(request.firstName);
        name.setFamily(request.lastName);
        rc.setEmailAddress(request.email);
        rc.setPhoneNumber(request.phoneNumber);
        rc.getSorPerson().setDateOfBirth(request.dateOfBirth);
        rc.getSorPerson().setSsn(request.ssn);
        rc.getSorPerson().setGender(request.gender);
        rc.setAddressLine1(request.addressLine1);
        rc.setAddressLine2(request.addressLine2);
        rc.setCity(request.city);
        rc.setRegion(request.region);
        rc.setPostalCode(request.postalCode);

        for (final PersonRequestRepresentation.Identifier identifier : request.identifiers) {
            final IdentifierType identifierType = referenceRepository.findIdentifierType(identifier.identifierType);
            Assert.notNull(identifierType);

            rc.getIdentifiersByType().put(identifierType, identifier.identifierValue);
        }
        return rc;
    }


}
