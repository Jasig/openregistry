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

import static org.junit.Assert.*;

import java.util.HashMap;

import javax.inject.Inject;

import org.junit.Test;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaDisclosureSettingsImpl;
import org.openregistry.core.repository.DisclosureRecalculationStrategyRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:base-integration-tests.xml"})
public class DefaultDisclosureStrategyTests extends AbstractIntegrationTests {
    
	@Inject 
    private DisclosureRecalculationStrategyRepository disclosureRecalcRepository;
    
	@Inject
    private ReferenceRepository referenceRepository;

	private static final String DISCLOSURE_HIDE_ALL = "5";
	private static final String DISCLOSURE_HIDE_FACULTY_HOME = "2";
	private static final String DISCLOSURE_AS_SPECIFIED = "1";

	private void setAllFlags(DisclosureSettings disclosure, boolean value) {
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		Type facultyType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY);
		Type staffType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.STAFF);
		Type landlineType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.LANDLINE);
		Type cellType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.CELL);

		// set all field-level settings to display
		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, new Boolean(value));
		props.put(DisclosureSettings.PropertyNames.REGION_IND, new Boolean(value));
		props.put(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND, new Boolean(value));
 		
		disclosure.setAddressDisclousure(facultyType, homeType, props);
		disclosure.setAddressDisclousure(facultyType, officeType, props);
		disclosure.setAddressDisclousure(staffType, homeType, props);
		disclosure.setAddressDisclousure(staffType, officeType, props);
		
		disclosure.setEmailDisclosure(facultyType, homeType, value);
		disclosure.setEmailDisclosure(facultyType, officeType, value);
		disclosure.setEmailDisclosure(staffType, homeType, value);
		disclosure.setEmailDisclosure(staffType, officeType, value);

		disclosure.setPhoneDisclosure(facultyType, homeType, landlineType, value);
		disclosure.setPhoneDisclosure(facultyType, officeType, landlineType, value);
		disclosure.setPhoneDisclosure(facultyType, homeType, cellType, value);
		disclosure.setPhoneDisclosure(facultyType, officeType, cellType, value);
		disclosure.setPhoneDisclosure(staffType, homeType, cellType, value);
		disclosure.setPhoneDisclosure(staffType, officeType, cellType, value);
		disclosure.setPhoneDisclosure(staffType, homeType, landlineType, value);
		disclosure.setPhoneDisclosure(staffType, officeType, landlineType, value);
		
		disclosure.setUrlDisclosure(facultyType, homeType, value);
		disclosure.setUrlDisclosure(facultyType, officeType, value);
		disclosure.setUrlDisclosure(staffType, homeType, value);
		disclosure.setUrlDisclosure(staffType, officeType, value);
	}
	
	@Test
	public void testDisclosureStrategyHideAll() {
        
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		Type landlineType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.LANDLINE);
		Type cellType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.CELL);
		DisclosureRecalculationStrategy strategy = this.disclosureRecalcRepository.getDisclosureRecalculationStrategy();
		
		DisclosureSettings disclosure = new JpaDisclosureSettingsImpl(null);//this.personRepository.findByInternalId(personId).getDisclosureSettings();
 		this.setAllFlags(disclosure, true);
 		disclosure.setDisclosureCode(DISCLOSURE_HIDE_ALL);
 		disclosure.recalculate(strategy);
 		
 		// for this type, all flags must be set to false
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressRegionPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressRegionPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());

 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,landlineType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,landlineType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,landlineType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,landlineType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,cellType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,cellType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,cellType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,cellType).getPublicInd());

 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());
	}
	
	@Test
	public void testDisclosureStrategyAsSpecified() {
        
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		Type facultyType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY);
		Type staffType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.STAFF);
		Type landlineType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.LANDLINE);
		Type cellType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.CELL);
		DisclosureRecalculationStrategy strategy = this.disclosureRecalcRepository.getDisclosureRecalculationStrategy();
		
		DisclosureSettings disclosure = new JpaDisclosureSettingsImpl(null);//this.personRepository.findByInternalId(personId).getDisclosureSettings();
 		this.setAllFlags(disclosure, true);
 		disclosure.setDisclosureCode(DISCLOSURE_AS_SPECIFIED);
 		disclosure.recalculate(strategy);
 		
 		// Set some flags to false
		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.FALSE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND, Boolean.TRUE); 		
		disclosure.setAddressDisclousure(staffType, officeType, props);

 		disclosure.setEmailDisclosure(staffType, homeType, false);
 		
 		disclosure.setPhoneDisclosure(facultyType, officeType, cellType, false);
 		
 		disclosure.setUrlDisclosure(staffType, homeType, false);
 		
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressRegionPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressRegionPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());

 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,cellType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,cellType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,cellType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,cellType).getPublicInd());

 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());
	}
	@Test
	public void testDisclosureStrategyHideFacultyHome() {
        
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		Type landlineType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.LANDLINE);
		Type cellType = this.referenceRepository.findType(Type.DataTypes.PHONE, Type.PhoneTypes.CELL);
		DisclosureRecalculationStrategy strategy = this.disclosureRecalcRepository.getDisclosureRecalculationStrategy();
		
		DisclosureSettings disclosure = new JpaDisclosureSettingsImpl(null);//this.personRepository.findByInternalId(personId).getDisclosureSettings();
 		this.setAllFlags(disclosure, true);
 		disclosure.setDisclosureCode(DISCLOSURE_HIDE_FACULTY_HOME);
 		disclosure.recalculate(strategy);
 		
 		// for this type, home address flag must be set to false for faculty
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(homeType).getAddressRegionPublicInd());

 		// office address flag must be set to true for faculty
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		// Region is hidden for Faculty office address
 		assertFalse(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.FACULTY.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		
 		// all address flags must be set to true for staff
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(homeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(homeType).getAddressRegionPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 			.get(officeType).getAddressBuildingPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressLinesPublicInd());
 		assertTrue(disclosure.getAddressDisclosureSettingsForAffiliation(Type.AffiliationTypes.STAFF.toString())
 	 		.get(officeType).getAddressRegionPublicInd());
 		
 		// Home email must be hidden for faculty only
 		assertFalse(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getEmailDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());

 		// Home phone and phone for home address type must be hidden for faculty only
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,landlineType).getPublicInd());
 		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType,cellType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,landlineType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,landlineType).getPublicInd());
		assertFalse(disclosure.getPhoneDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType,cellType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType,cellType).getPublicInd());
 		assertTrue(disclosure.getPhoneDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType,cellType).getPublicInd());

 		// Home URL must be hidden for faculty only
 		assertFalse(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),homeType).getPublicInd());
 		assertTrue(disclosure.getUrlDisclosure(Type.AffiliationTypes.STAFF.toString(),officeType).getPublicInd());
	}
	@Test
	public void testDisclosureChangeWithPresetValues() {
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		Type facultyType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY);
		DisclosureRecalculationStrategy strategy = this.disclosureRecalcRepository.getDisclosureRecalculationStrategy();
		
		DisclosureSettings disclosure = new JpaDisclosureSettingsImpl(null);
 		this.setAllFlags(disclosure, true);
 		disclosure.setDisclosureCode(DISCLOSURE_AS_SPECIFIED);
 		// Hide some office values for Faculty
 		disclosure.setUrlDisclosure(facultyType, officeType, false);
 		disclosure.setEmailDisclosure(facultyType, officeType, false);
 		disclosure.recalculate(strategy);
 		
 		assertFalse("Office email must be hidden as specified", disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse("Office Url must be hidden as specified", disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd()); 		
 		assertTrue("Home email must be public by default", disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertTrue("Home Url must be public by default", disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd()); 		
		
 		// Change disclosure flag to hide home addresses
 		disclosure.setDisclosureCode(DISCLOSURE_HIDE_FACULTY_HOME);
 		disclosure.recalculate(strategy);
 		assertFalse("Office email must be hidden as specified", disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd());
 		assertFalse("Office Url must be hidden as specified", disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),officeType).getPublicInd()); 		
 		assertFalse("Home email must be hidden based on strategy", disclosure.getEmailDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd());
 		assertFalse("Home Url must be hidden based on strategy", disclosure.getUrlDisclosure(Type.AffiliationTypes.FACULTY.toString(),homeType).getPublicInd()); 		
	}
}
