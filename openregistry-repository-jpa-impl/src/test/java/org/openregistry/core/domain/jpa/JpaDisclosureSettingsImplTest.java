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
package org.openregistry.core.domain.jpa;

import java.util.Map;
import java.util.HashMap;

import javax.inject.Inject;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:base-integration-tests.xml"})
public class JpaDisclosureSettingsImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
    private ReferenceRepository referenceRepository;
    
	private Type homeType = null;
	private Type officeType = null;
	private Type facultyType = null;
	private Type studentType = null;
	
	@Before
    public void createAffiliationTypes() throws Exception {
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(7, 'AFFILIATION', 'STUDENT')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(8, 'AFFILIATION', 'FACULTY')");
        homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
        officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
    }

	@Before
    public void createAddressTypes() throws Exception {
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(9, 'ADDRESS', 'HOME')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(10, 'ADDRESS', 'OFFICE')");
        facultyType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY);
        studentType = this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.STUDENT);
	}

	@Test
	public void testAddressMap() {
		JpaDisclosureSettingsImpl disclosure = new JpaDisclosureSettingsImpl(null);
		Map <DisclosureSettings.PropertyNames, Object> props = new HashMap <DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		disclosure.setAddressDisclousure(facultyType, officeType, props);

		props.put(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND, Boolean.FALSE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.FALSE);
		disclosure.setAddressDisclousure(studentType, homeType, props);

		Map<Type, DisclosureSettingsForAddress> studentDisclosureMap = disclosure.getAddressDisclosureSettingsForAffiliation(studentType.getDescription());
		assertEquals(1, studentDisclosureMap.entrySet().size());
		assertFalse(studentDisclosureMap.get(homeType).getAddressBuildingPublicInd());
		assertFalse(studentDisclosureMap.get(homeType).getAddressRegionPublicInd());
		assertFalse(studentDisclosureMap.get(homeType).getAddressLinesPublicInd());
		
		Map<Type, DisclosureSettingsForAddress> facultyDisclosureMap = disclosure.getAddressDisclosureSettingsForAffiliation(facultyType.getDescription());
		assertEquals(1, facultyDisclosureMap.entrySet().size());
		assertTrue(facultyDisclosureMap.get(officeType).getAddressBuildingPublicInd());
		assertTrue(facultyDisclosureMap.get(officeType).getAddressRegionPublicInd());
		assertTrue(facultyDisclosureMap.get(officeType).getAddressLinesPublicInd());
	}
	
	@Test
	public void testPhoneMap() {
		JpaDisclosureSettingsImpl disclosure = new JpaDisclosureSettingsImpl(null);
		disclosure.setPhoneDisclosure(studentType, homeType, homeType, true);
		disclosure.setPhoneDisclosure(facultyType, homeType, officeType, true);
		disclosure.setPhoneDisclosure(facultyType, homeType, homeType, true);
		disclosure.setPhoneDisclosure(facultyType, officeType, officeType,true);
		
		Map<Type, Map<Type, DisclosureSettingsForPhone>> phoneMapForStudent = 
			disclosure.getPhoneDisclosureSettingsForAffiliation(studentType.getDescription());
		assertEquals(1, phoneMapForStudent.entrySet().size());
		assertNull(phoneMapForStudent.get(officeType));
		assertNotNull(phoneMapForStudent.get(homeType).get(homeType));
		assertTrue(phoneMapForStudent.get(homeType).get(homeType).getPublicInd());

		Map<Type, Map<Type, DisclosureSettingsForPhone>> phoneMapForFaculty = 
			disclosure.getPhoneDisclosureSettingsForAffiliation(facultyType.getDescription());
		assertEquals(2, phoneMapForFaculty.entrySet().size());
		assertEquals(2, phoneMapForFaculty.get(homeType).entrySet().size());
		assertEquals(1, phoneMapForFaculty.get(officeType).entrySet().size());
		assertNull(phoneMapForFaculty.get(officeType).get(homeType));
		assertNotNull(phoneMapForFaculty.get(officeType).get(officeType));
		assertTrue(phoneMapForFaculty.get(homeType).get(homeType).getPublicInd());
	}
	
	@Test
	public void testEmailMap() {
		JpaDisclosureSettingsImpl disclosure = new JpaDisclosureSettingsImpl(null);
		disclosure.setEmailDisclosure(facultyType, officeType, true);
		disclosure.setEmailDisclosure(facultyType, homeType, false);
		disclosure.setEmailDisclosure(studentType, homeType, true);

		Map<Type, DisclosureSettingsForEmail> studentDisclosureMap = disclosure.getEmailDisclosureSettingsForAffiliation(studentType.getDescription());
		assertEquals(1, studentDisclosureMap.entrySet().size());
		assertNull(studentDisclosureMap.get(officeType));
		assertTrue(studentDisclosureMap.get(homeType).getPublicInd());
		
		Map<Type, DisclosureSettingsForEmail> facultyDisclosureMap = disclosure.getEmailDisclosureSettingsForAffiliation(facultyType.getDescription());
		assertEquals(2, facultyDisclosureMap.entrySet().size());
		assertTrue(facultyDisclosureMap.get(officeType).getPublicInd());
		assertFalse(facultyDisclosureMap.get(homeType).getPublicInd());
	}
	
	@Test
	public void testUrlMap() {
		JpaDisclosureSettingsImpl disclosure = new JpaDisclosureSettingsImpl(null);
		disclosure.setUrlDisclosure(studentType, homeType, true);
		disclosure.setUrlDisclosure(studentType, homeType, true);

		Map<Type, DisclosureSettingsForUrl> studentDisclosureMap = disclosure.getUrlDisclosureSettingsForAffiliation(studentType.getDescription());
		assertEquals(2, studentDisclosureMap.entrySet().size());
		assertNull(studentDisclosureMap.get(officeType));
		assertTrue(studentDisclosureMap.get(homeType).getPublicInd());
		assertTrue(studentDisclosureMap.get(homeType).getPublicInd());
		
		Map<Type, DisclosureSettingsForUrl> facultyDisclosureMap = disclosure.getUrlDisclosureSettingsForAffiliation(facultyType.getDescription());
		assertTrue(facultyDisclosureMap.isEmpty());
	}
}
