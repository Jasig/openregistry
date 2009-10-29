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

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.EndState;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;
import org.openregistry.core.domain.sor.MockReconciliationCriteria;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.MockPerson;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.MockPersonRepository;
import org.openregistry.core.repository.MockReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.web.factory.MockReconciliationCriteriaFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.binding.mapping.Mapper;


import java.util.Date;

/**
 * @author Nancy Mond
 * @since 1.0
 */
public class AddSoRPersonFlowTests extends AbstractXmlFlowExecutionTests {

    private final String OR_WEBAPP_IDENTIFIER = "or-webapp";
	private static final String EMAIL_ADDRESS = "test@test.edu";
	private static final String PHONE_NUMBER = "555-555-5555";
	private static final String RUDYARD = "Rudyard";
	private static final String KIPLING = "Kipling";

    private MockPersonSearchAction personSearchAction;
    private PersonRepository personRepository;
    private ReferenceRepository referenceRepository;
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaFactory;

    protected void setUp(){
        this.personRepository = new MockPersonRepository(new MockPerson());
        personSearchAction = new MockPersonSearchAction();
        referenceRepository = new MockReferenceRepository();
        reconciliationCriteriaFactory = new MockReconciliationCriteriaFactory();
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
            return resourceFactory.createFileResource("src/main/webapp/WEB-INF/flows/addPerson.xml");
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
        builderContext.registerBean("personSearchAction", personSearchAction);
        builderContext.registerBean("referenceRepository", referenceRepository);
        builderContext.registerBean("reconciliationCriteriaFactory", reconciliationCriteriaFactory);
    }
    
    protected ReconciliationCriteria constructReconciliationCriteria(final String firstName, final String lastName, final String ssn, final String emailAddress, final String phoneNumber, Date birthDate, final String sor){
        final ReconciliationCriteria reconciliationCriteria = reconciliationCriteriaFactory.getObject();
        reconciliationCriteria.setEmailAddress(emailAddress);
        reconciliationCriteria.setPhoneNumber(phoneNumber);

        final SorPerson sorPerson = reconciliationCriteria.getPerson();
        sorPerson.setDateOfBirth(birthDate);
        sorPerson.setSourceSor(sor);
        sorPerson.setSsn(ssn);

        final Name name = sorPerson.addName(this.referenceRepository.findType(Type.DataTypes.NAME, "Formal"));
        name.setFamily(lastName);
        name.setGiven(firstName);
        name.setOfficialName();

       
        return reconciliationCriteria;
    }

    public void testStartFlow() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
        MutableAttributeMap input = new LocalAttributeMap();
	    input.put("personSearch", criteria);
        ExternalContext context = new MockExternalContext();

        startFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    //validation errors found
    public void testCriteriaSubmitError() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, RUDYARD, "INVALID_SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        MockExternalContext context = new MockExternalContext();

        //submit with invalid input
        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    //reconciliation returns NONE.  Adds the new person.  Adds the role.
    public void testAddNewPerson() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "UNIQUE_SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
 
        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockAddRoleSubflow());

        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("roleAddedSucceeded");
    }

    //reconciliation returns reconciliation (view matches)
    public void testAddPersonMatchesFound() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("viewMatches");
    }

    //test case to start at viewMatches and continue to force add
    public void testForceAdd() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockAddRoleSubflow());
        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("viewMatches");
        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("roleAddedSucceeded");        
    }
    

    // test case to start at viewMatches and continue to selecting a match and ending at addRole.
     public void testSelectMatchAddRole() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockAddRoleSubflow());
        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("viewMatches");
        context.setEventId("submitAddRole");
        context.putRequestParameter("personId","1");
        resumeFlow(context);
        assertCurrentStateEquals("roleAddedSucceeded"); 
    }

    // mock add role subflow
    public Flow createMockAddRoleSubflow(){
        Flow mockAddRoleFlow = new Flow("addRole");
        mockAddRoleFlow.setInputMapper(new Mapper() {
            public MappingResults map(Object source, Object target)  {
                assertNotNull(((AttributeMap)source).get("sorPerson"));
                return null;
            }
        });
        new EndState(mockAddRoleFlow, "roleWasAdded");
        return mockAddRoleFlow;
    }
}