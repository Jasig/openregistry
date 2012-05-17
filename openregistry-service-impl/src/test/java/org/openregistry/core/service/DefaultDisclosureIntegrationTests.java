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

import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorPersonAlreadyExistsException;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SystemOfRecord;
import org.openregistry.core.repository.DisclosureRecalculationStrategyRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:test-disclosureService-context.xml"})
public class DefaultDisclosureIntegrationTests extends AbstractIntegrationTests {

	@Inject 
	DisclosureService disclosureService;
   
	@Inject
    private PersonService personService;
	
	@Inject
    private PersonRepository personRepository;

    @Inject
    private ReferenceRepository referenceRepository;
	
    @Inject 
    private DisclosureRecalculationStrategyRepository disclosureRecalcRepository;
    
	@PersistenceContext
    private EntityManager entityManager;
	
	private Long createSorPerson (String sorName, String lastName, String [] affiliations, long orgUnitId) {
		Long validPersonId = null;
		int personCount = countRowsInTable("prs_sor_persons");
		int roleCount = countRowsInTable("prs_role_records");
		int identifierCount = countRowsInTable("prc_identifiers");
		int newRoleCount = 0;

        SystemOfRecord sor = referenceRepository.findSystemOfRecord(sorName);
        
		ReconciliationCriteria reconciliationCriteria = new JpaReconciliationCriteriaImpl();
		reconciliationCriteria.setEmailAddress(lastName+"@test.tst");
        reconciliationCriteria.setPhoneNumber("1234567890");

        try {
			SorPerson sorPerson = reconciliationCriteria.getSorPerson();
	        sorPerson.setDateOfBirth(new Date(new Date(0).getTime()+(long)(Math.random()*10000000000L)));
	        sorPerson.setGender("M");
	        sorPerson.setSorId(lastName+sorName);
	        sorPerson.setSourceSor(sor.getSorId());

	        SorName name = sorPerson.addName(this.referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
	        name.setFamily(lastName);
	        name.setGiven("First");

	        ServiceExecutionResult<Person> result = personService.addPerson(reconciliationCriteria);
			entityManager.flush();

			Person person = result.getTargetObject();
			person.addIdentifier(this.referenceRepository.findIdentifierType("NETID"), lastName);
			validPersonId = person.getId();
			sorPerson = personService.findByPersonIdAndSorIdentifier(validPersonId, sorName);
			
			for (String affiliation:affiliations) {
			    newRoleCount++;
		        SorRole sorRole = sorPerson.createRole(referenceRepository.findType(Type.DataTypes.AFFILIATION, affiliation));
		        sorRole.setSystemOfRecord(sor);
				sorRole.setSorId(newRoleCount+"-"+sorPerson.getSorId());
				sorRole.setStart(new Date());
			    sorRole.setPersonStatus(this.referenceRepository.getTypeById(1L));
			    sorRole.setSponsorId(1L);
			    sorRole.setSponsorType(this.referenceRepository.getTypeById(1L));
		        sorRole.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(orgUnitId)));
			    sorRole.setTitle("Professor");
			    sorPerson.addRole(sorRole);

			    personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
			    entityManager.flush();
        	}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ReconciliationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (SorPersonAlreadyExistsException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull("The id of created person must not be null before test", validPersonId);
	    assertTrue("The id of created person must be positive before test", validPersonId.longValue()>0);
        assertEquals("A valid SOR person must be created", personCount+1, countRowsInTable("prs_sor_persons"));
        assertEquals("A valid SOR role must be created", roleCount+newRoleCount, countRowsInTable("prs_role_records"));
        assertEquals("An identifier must have been created", identifierCount+1, countRowsInTable("prc_identifiers"));
        return validPersonId;
	}
	
	
	@Test
	public void testCreateFieldDisclosurePersonDoesNotExist() {
		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		try {
			this.disclosureService.setAddressDisclosureSettings("NETID", "Doesnotexist", Type.AffiliationTypes.FACULTY.toString(), homeType, props);
			fail("Exception should be thrown");
		} catch (PersonNotFoundException e) {
			// expected behavior
		} 
	}
	
	@Test
	public void testCreateFieldDisclosureNoDisclosure() {

		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
		String [] affiliations = new String[] {Type.AffiliationTypes.FACULTY.toString()};
		String lastName = "Test";
		
		this.createSorPerson(SOR_ID1, lastName, affiliations, 1);
  		
        Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		
        try {
			this.disclosureService.setAddressDisclosureSettings("NETID", lastName, affiliations[0], homeType, props);
			fail("Exception should be thrown");
		} catch (IllegalStateException e) {
			// expected behavior
		} 
	}
	
	@Test
	public void testCreateSingleAddressDisclosure () {
        assertEquals(0, countRowsInTable("prc_disclosure_address"));

		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
		String [] affiliations = new String[] {Type.AffiliationTypes.FACULTY.toString()};
		String lastName = "Test";
		
		Long personId = this.createSorPerson(SOR_ID1, lastName, affiliations, 1);
 		SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(personId, SOR_ID1);  		
 		sorPerson.setDisclosureSettingInfo("1", true, new Date());
 		this.personService.updateSorPerson(sorPerson);
 		
        Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		
		ServiceExecutionResult<DisclosureSettingsForAddress> result =
			this.disclosureService.setAddressDisclosureSettings("NETID", lastName, affiliations[0], homeType, props);
		assertNotNull("Service execution result should not be null", result);

        assertEquals(1, countRowsInTable("prc_disclosure_address"));
		
        DisclosureSettingsForAddress savedObject = (DisclosureSettingsForAddress)result.getTargetObject();
        assertNotNull("A non-null address object must be returned", savedObject);
        assertTrue("The saved object should have correct affiliation and type", savedObject.matchesTypeAndAffiliation(homeType, affiliations[0]));
	}
	
	@Test
	public void testCreateEmailDisclosureWithoutRecalculation () {
        assertEquals(0, countRowsInTable("prc_disclosure_email"));

		String [] affiliations = new String[] {Type.AffiliationTypes.FACULTY.toString()};
		String lastName = "Test";
		
		Long personId = this.createSorPerson(SOR_ID1, lastName, affiliations, 1);
 		SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(personId, SOR_ID1);  		
 		sorPerson.setDisclosureSettingInfo("1", true, new Date());
 		this.personService.updateSorPerson(sorPerson);
 		
        Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		
        Person person = this.personService.findPersonById(personId);
        
        // Add new disclosure setting without recalculating and try to save
        person.getDisclosureSettings().setEmailDisclosure
        	(this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY), homeType, true);
        
        try {
        	this.personRepository.savePerson(person);
        	this.entityManager.flush();
        	fail("Exception should be thrown");
        } catch (Exception e) {
        	// expected behavior
        }
        assertEquals("Email disclosure must not be saved without recalculation", 0, countRowsInTable("prc_disclosure_email"));

        person.getDisclosureSettings().recalculate(disclosureRecalcRepository.getDisclosureRecalculationStrategy());
        this.personRepository.savePerson(person);

        assertEquals("Email disclosure must be successfully saved after recalculation", 1, countRowsInTable("prc_disclosure_email"));
        
        // Change existing disclosure setting without recalculating and try to save
        person.getDisclosureSettings().setEmailDisclosure
        	(this.referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY), homeType, false);
        
        try {
        	this.personRepository.savePerson(person);
        	this.entityManager.flush();
        	fail("Exception should be thrown");
        } catch (Exception e) {
        	// expected behavior
        }
	}

	@Test 
	public void testCreateAndUpdateAddressDisclosures() {
        assertEquals(0, countRowsInTable("prc_disclosure_address"));

        String [] affiliations = new String[] {Type.AffiliationTypes.FACULTY.toString(), Type.AffiliationTypes.STAFF.toString()};
		Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);
		Type officeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE);
		String lastName = "Test";
		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
		
		Long personId = this.createSorPerson(SOR_ID1, lastName, affiliations, 1);
 		SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(personId, SOR_ID1);  		
 		sorPerson.setDisclosureSettingInfo("1", true, new Date());
 		this.personService.updateSorPerson(sorPerson);	
 		this.entityManager.flush();
		
		ServiceExecutionResult<DisclosureSettingsForAddress> result =
			this.disclosureService.setAddressDisclosureSettings("NETID", lastName, affiliations[0], homeType, props);
		assertNotNull("Service execution result should not be null", result);
		
        assertEquals(1, countRowsInTable("prc_disclosure_address"));

        DisclosureSettingsForAddress savedObject = (DisclosureSettingsForAddress)result.getTargetObject();
        assertEquals("The saved address disclosure must have the same building flag as the properties", props.get(DisclosureSettings.PropertyNames.BUILDING_IND), savedObject.getAddressBuildingPublicInd());
        assertEquals("The saved address disclosure must have the same region flag as the properties", props.get(DisclosureSettings.PropertyNames.REGION_IND), savedObject.getAddressRegionPublicInd());
        assertTrue("The saved address disclosure must have the address lines flag set to true by default", savedObject.getAddressLinesPublicInd());
	
        // add the same settings for an office address
		result = this.disclosureService.setAddressDisclosureSettings
			("NETID", lastName, affiliations[0], officeType, props);
		assertNotNull("Service execution result should not be null", result);
		
        assertEquals("A second row should be inserted for different type", 2, countRowsInTable("prc_disclosure_address"));

		savedObject = (DisclosureSettingsForAddress)result.getTargetObject();
		assertTrue("The created settings should be for an office address and "+affiliations[0]+" affiliation", 
			savedObject.matchesTypeAndAffiliation(officeType, affiliations[0]));
		
		// Add home address settings for a different role
		result = this.disclosureService.setAddressDisclosureSettings
			("NETID", lastName, affiliations[1], homeType, props);
		entityManager.flush();
	
	    assertEquals("A third row should be inserted for different role", 3, countRowsInTable("prc_disclosure_address"));
	
		savedObject = (DisclosureSettingsForAddress)result.getTargetObject();
		assertTrue("The created settings should be for a home address and "+affiliations[1]+" affiliation", 
			savedObject.matchesTypeAndAffiliation(homeType, affiliations[1]));

        // update one of the existing objects
		// create an object with different values
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.FALSE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND, Boolean.TRUE);
        
		result = this.disclosureService.setAddressDisclosureSettings
			("NETID", lastName, affiliations[0], homeType, props);
		entityManager.flush();
		
		// it is an update, so there should still be only one row
        assertEquals("When updating disclosure settings, a row should not be inserted", 3, countRowsInTable("prc_disclosure_address"));
       
        savedObject = (DisclosureSettingsForAddress)result.getTargetObject();
        assertTrue("The saved address disclosure must have address type "+homeType.getDescription()+" and "+affiliations[0]+" affiliation", savedObject.matchesTypeAndAffiliation(homeType, affiliations[0]));
        assertEquals("The saved address disclosure must have the same building flag as the properties", props.get(DisclosureSettings.PropertyNames.BUILDING_IND), savedObject.getAddressBuildingPublicInd());
        assertEquals("The saved address disclosure must have the same region flag as the properties", props.get(DisclosureSettings.PropertyNames.REGION_IND), savedObject.getAddressRegionPublicInd());
        assertEquals("The saved address disclosure must have the same address lines flag as the properties", props.get(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND), savedObject.getAddressLinesPublicInd());
 	}
	
	
	@Test
	public void testDeletePerson () {
		String [] affiliations = new String[] {Type.AffiliationTypes.FACULTY.toString()};
		String lastName = "Lastname";
		this.createSorPerson(SOR_ID1, "Test", affiliations, 1);

		Long personId = this.createSorPerson(SOR_ID1, lastName, affiliations, 1);
		SorPerson sorPerson = personService.findByPersonIdAndSorIdentifier(personId, SOR_ID1);		
 		sorPerson.setDisclosureSettingInfo("1", true, new Date());
 		this.personService.updateSorPerson(sorPerson);	
 		this.entityManager.flush();
        
		HashMap<DisclosureSettings.PropertyNames, Object> props = new HashMap<DisclosureSettings.PropertyNames, Object>();
		props.put(DisclosureSettings.PropertyNames.BUILDING_IND, Boolean.TRUE);
		props.put(DisclosureSettings.PropertyNames.REGION_IND, Boolean.FALSE);
        Type homeType = this.referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.HOME);

        this.disclosureService.setAddressDisclosureSettings ("NETID", lastName, affiliations[0], homeType, props);
        
        assertEquals(1, countRowsInTable("prs_disclosure"));
        assertEquals(1, countRowsInTable("prc_disclosure"));
        assertEquals(1, countRowsInTable("prc_disclosure_address"));
        
        // deleting the only SoR person should result in the deletion of calculated person
        boolean deleteStatus = this.personService.deleteSystemOfRecordPerson(sorPerson, true, "UNSPECIFIED");		
        assertTrue("Delete status must be true", deleteStatus);
        entityManager.flush();
        
        assertEquals("There must be no calculated disclosure after deleting the only SoR person", 0, countRowsInTable("prc_disclosure_address"));
	}
}
