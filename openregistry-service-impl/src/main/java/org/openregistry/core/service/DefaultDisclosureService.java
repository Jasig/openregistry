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

import java.util.Map;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.DisclosureSettings.PropertyNames;
import org.openregistry.core.repository.DisclosureRecalculationStrategyRepository;
import org.openregistry.core.repository.DisclosureRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.dao.EmptyResultDataAccessException;

public class DefaultDisclosureService implements DisclosureService {

	private DisclosureRepository disclosureRepository;
	private PersonRepository personRepository;
	private ReferenceRepository referenceRepository;
	private DisclosureRecalculationStrategyRepository strategyRepository;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Inject
	public DefaultDisclosureService 
		(DisclosureRepository disclosureRepository, 
		PersonRepository personRepository, 
		ReferenceRepository referenceRepository,
		DisclosureRecalculationStrategyRepository strategyRepository)  throws Exception {
		this.disclosureRepository = disclosureRepository;
		this.personRepository = personRepository;
		this.referenceRepository = referenceRepository;
		this.strategyRepository = strategyRepository;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getAddressDisclosureSettingsForAffiliation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForAddress> getAddressDisclosureSettingsForAffiliation(
			String identifierType, String identifierValue,
			String affiliationType) throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getAddressDisclosureSettingsForAffiliation(affiliationType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getAddressDisclosureSettingsForRoleAndType(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForAddress getAddressDisclosureSettingsForRoleAndType(
			String identifierType, String identifierValue,
			String affiliationType, Type addressType) {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getAddressDisclosure(affiliationType, addressType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getEmailDisclosureSettingsForAffiliation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForEmail> getEmailDisclosureSettingsForAffiliation(
			String identifierType, String identifierValue,
			String affiliationType) throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getEmailDisclosureSettingsForAffiliation(affiliationType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getEmailDisclosureSettingsForRoleAndType(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForEmail getEmailDisclosureSettingsForRoleAndType(
			String identifierType, String identifierValue,
			String affiliationType, Type emailType)
			throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getEmailDisclosure(affiliationType, emailType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getPhoneDisclosureSettingsForAffiliation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Type, Map<Type, DisclosureSettingsForPhone>> getPhoneDisclosureSettingsForAffiliation(
			String identifierType, String identifierValue,
			String affiliationType) throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getPhoneDisclosureSettingsForAffiliation(affiliationType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getPhoneDisclosureSettingsForRoleAndType(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForPhone getPhoneDisclosureSettingsForRoleAndType(
			String identifierType, String identifierValue,
			String affiliationType, Type addressType, Type phoneType)
			throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getPhoneDisclosure(affiliationType, addressType, phoneType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getUrlDisclosureSettingsForAffiliation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForUrl> getUrlDisclosureSettingsForAffiliation(
			String identifierType, String identifierValue,
			String affiliationType) throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getUrlDisclosureSettingsForAffiliation(affiliationType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#getUrlDisclosureSettingsForRoleAndType(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForUrl getUrlDisclosureSettingsForRoleAndType(
			String identifierType, String identifierValue,
			String affiliationType, Type urlType)
			throws PersonNotFoundException {
		Person person = personRepository.findByIdentifier(identifierType, identifierValue);
		if (person == null) {
			throw new PersonNotFoundException();
		} else {
			return person.getDisclosureSettings().getUrlDisclosure(affiliationType, urlType);
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#setAddressDisclosureSettings(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type, java.util.Map)
	 */
	public ServiceExecutionResult<DisclosureSettingsForAddress> setAddressDisclosureSettings(
			String identifierType, String identifierValue, String affiliation,
			Type addressType, Map<PropertyNames, Object> addressDisclosureProps) {
		Person person = null;
		try {
			person = personRepository.findByIdentifier(identifierType, identifierValue);
		} catch (EmptyResultDataAccessException e) {
			throw new PersonNotFoundException(e);
		}
		
		// TODO: validate
		DisclosureSettings disclosure = person.getDisclosureSettings();
		if (disclosure != null) {
			disclosure.setAddressDisclousure
				(referenceRepository.findType(Type.DataTypes.AFFILIATION, affiliation), addressType, addressDisclosureProps);
			disclosure.recalculate(this.strategyRepository.getDisclosureRecalculationStrategy());
			disclosure = this.disclosureRepository.saveDisclosureSettings(person.getDisclosureSettings());
			return new GeneralServiceExecutionResult<DisclosureSettingsForAddress>(disclosure.getAddressDisclosure(affiliation, addressType));
		} else {
			throw new IllegalStateException("Can't set address disclosure because disclosure object is not initialized");
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#setEmailDisclosureSettings(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type, boolean)
	 */
	public ServiceExecutionResult<DisclosureSettingsForEmail> setEmailDisclosureSettings(
			String identifierType, String identifierValue, String affiliation,
			Type emailType, boolean isPublic) throws PersonNotFoundException {
		Person person = null;
		try {
			person = personRepository.findByIdentifier(identifierType, identifierValue);
		} catch (EmptyResultDataAccessException e) {
			throw new PersonNotFoundException(e);
		}
		
		// TODO: validate
		DisclosureSettings disclosure = person.getDisclosureSettings();
		if (disclosure != null) {
			disclosure.setEmailDisclosure
				(referenceRepository.findType(Type.DataTypes.AFFILIATION, affiliation), emailType, isPublic);
			disclosure.recalculate(this.strategyRepository.getDisclosureRecalculationStrategy());
			disclosure = this.disclosureRepository.saveDisclosureSettings(person.getDisclosureSettings());
			return new GeneralServiceExecutionResult<DisclosureSettingsForEmail>(disclosure.getEmailDisclosure(affiliation, emailType));
		} else {
			throw new IllegalStateException("Can't set email disclosure because disclosure object is not initialized");
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#setPhoneDisclosureSettings(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean)
	 */
	public ServiceExecutionResult<DisclosureSettingsForPhone> setPhoneDisclosureSettings(
			String identifierType, String identifierValue, String affiliation,
			Type addressType, Type phoneType, boolean isPublic)
			throws PersonNotFoundException {
		Person person = null;
		try {
			person = personRepository.findByIdentifier(identifierType, identifierValue);
		} catch (EmptyResultDataAccessException e) {
			throw new PersonNotFoundException(e);
		}
		
		// TODO: validate
		DisclosureSettings disclosure = person.getDisclosureSettings();
		if (disclosure != null) {
			disclosure.setPhoneDisclosure(referenceRepository.findType(Type.DataTypes.AFFILIATION, affiliation), 
				addressType, phoneType, isPublic);
			disclosure.recalculate(this.strategyRepository.getDisclosureRecalculationStrategy());
			disclosure = this.disclosureRepository.saveDisclosureSettings(person.getDisclosureSettings());
			return new GeneralServiceExecutionResult<DisclosureSettingsForPhone>(disclosure.getPhoneDisclosure(affiliation, addressType, phoneType));
		} else {
			throw new IllegalStateException("Can't set phone disclosure because disclosure object is not initialized");
		}
	}

	/**
	 * @see org.openregistry.core.service.DisclosureService#setUrlDisclosureSettings(java.lang.String, java.lang.String, java.lang.String, org.openregistry.core.domain.Type, boolean)
	 */
	public ServiceExecutionResult<DisclosureSettingsForUrl> setUrlDisclosureSettings(
			String identifierType, String identifierValue, String affiliation,
			Type urlType, boolean isPublic) throws PersonNotFoundException {
		Person person = null;
		try {
			person = personRepository.findByIdentifier(identifierType, identifierValue);
		} catch (EmptyResultDataAccessException e) {
			throw new PersonNotFoundException(e);
		}
		
		// TODO: validate
		DisclosureSettings disclosure = person.getDisclosureSettings();
		if (disclosure != null) {
			disclosure.setUrlDisclosure
				(referenceRepository.findType(Type.DataTypes.AFFILIATION, affiliation), urlType, isPublic);
			disclosure.recalculate(this.strategyRepository.getDisclosureRecalculationStrategy());
			disclosure = this.disclosureRepository.saveDisclosureSettings(person.getDisclosureSettings());
			return new GeneralServiceExecutionResult<DisclosureSettingsForUrl>(disclosure.getUrlDisclosure(affiliation, urlType));
		} else {
			throw new IllegalStateException("Can't set URL disclosure because disclosure object is not initialized");
		}
	}
}