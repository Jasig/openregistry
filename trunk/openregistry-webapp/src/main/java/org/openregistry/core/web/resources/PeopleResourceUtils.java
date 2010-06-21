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

package org.openregistry.core.web.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorName;
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
            final SorName name = rc.getSorPerson().addName();
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
            	updatePhone(rc, request.reconciliation.phones.get(0).phoneNumber);
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

	private static void updateName(final SorName name, final PersonRequestRepresentation.Name n, final ReferenceRepository referenceRepository) {
        name.setFamily(n.lastName);
        name.setGiven(n.firstName);
        name.setMiddle(n.middleName);
        name.setSuffix(n.suffix);
        name.setPrefix(n.prefix);
        final Type type = processNameType(referenceRepository, n.nameType);
        name.setType(type);
    }
	
	private static void updatePhone(final ReconciliationCriteria rc,
			final String phoneNumber) {
		String countryCode = null;
		String areaCode = null;
		String number = null;
		String extension = null;

		/*
		 * Phone Number formats excepted:
		 * Examples: (NNN) NNN-NNNN, NNNNNNNNNN, NNN-NNN-NNNN
		 * 
		 * Dashes or spaces are optional, parenthesis around area code are optional.
		 * The number can be prefaced with the Country code which is a plus sign and 1-3 digits.
		 * This needs to be followed by dash or space, e.g. +NNN NNN NNN-NNNN, +N-NNN-NNN-NNNN
		 * The number can also be followed by an extension which is an 'x' followed by any
		 * number of digits. There can be an optional space before the 'x'.
		 * E.g. (NNN)-NNN-NNNNxNNNN, NNN-NNNN xNNN
		 * 
		 * Full examples: +1 (234)-567-8901 x234, +1-234-567-8901x234, +1 234 567 8901 x234
		 */
		String expression = "^(\\+(\\d{1,3})[- ])?(\\(?(\\d{3})\\)?[- ]?)?((\\d{3})[- ]?(\\d{4}))([ ]?x(\\d+))?$";
		Matcher matcher = Pattern.compile(expression).matcher(phoneNumber);
		if (matcher.matches()) {
			countryCode = matcher.group(2);
			areaCode = matcher.group(4);
			number = matcher.group(6) + matcher.group(7);
			extension = matcher.group(9);
		}

		rc.setPhoneCountryCode(countryCode);
		rc.setPhoneAreaCode(areaCode);
		rc.setPhoneNumber(number);
		rc.setPhoneExtension(extension);
	}

    public static void buildModifiedSorPerson(final PersonRequestRepresentation request, final SorPerson sorPerson, final ReferenceRepository referenceRepository) {
        sorPerson.setDateOfBirth(request.dateOfBirth);
        sorPerson.setSsn(request.ssn);
        sorPerson.setGender(request.gender);
        sorPerson.getNames().clear();

        for (final PersonRequestRepresentation.Name n : request.names) {
            final SorName name = sorPerson.addName();
            updateName(name, n, referenceRepository);
        }

    }

    public static Type processNameType(final ReferenceRepository referenceRepository, final String nameType) {
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
