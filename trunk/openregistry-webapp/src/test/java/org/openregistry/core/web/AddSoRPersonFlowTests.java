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
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.MockSorPerson;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.context.support.StaticMessageSource;
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
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.web.factory.MockReconciliationCriteriaFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.binding.mapping.Mapper;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import static org.mockito.Mockito.*;

import java.util.*;

/**
 * @author Nancy Mond
 * @since 1.0
 */
public final class AddSoRPersonFlowTests extends AbstractXmlFlowExecutionTests {

    private final String OR_WEBAPP_IDENTIFIER = "or-webapp";
	private static final String EMAIL_ADDRESS = "test@test.edu";
	private static final String PHONE_NUMBER = "555-555-5555";
	private static final String RUDYARD = "Rudyard";
	private static final String KIPLING = "Kipling";

    private PersonSearchAction personSearchAction;
    private ReferenceRepository referenceRepository;
    private ObjectFactory<ReconciliationCriteria> reconciliationCriteriaFactory;

    private DefaultMessageContext messageContext;

    private PersonService personService;

    protected void setUp() {

        this.personService = mock(PersonService.class);

        final StaticMessageSource staticMessageSource = new StaticMessageSource();
        staticMessageSource.addMessage("personAddedFinalConfirm", Locale.getDefault(), "test");
        staticMessageSource.addMessage("roleAdded", Locale.getDefault(), "test");
        staticMessageSource.addMessage("errorCode", Locale.getDefault(), "test");

        this.messageContext = new DefaultMessageContext(staticMessageSource);
        this.personSearchAction = new PersonSearchAction(this.personService);
        this.referenceRepository = new MockReferenceRepository();
        this.reconciliationCriteriaFactory = new MockReconciliationCriteriaFactory();
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
        return resourceFactory.createFileResource("src/main/webapp/WEB-INF/flows/addPerson.xml");
    }

    @Override
    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        builderContext.registerBean("personSearchAction", personSearchAction);
        builderContext.registerBean("referenceRepository", referenceRepository);
        builderContext.registerBean("reconciliationCriteriaFactory", reconciliationCriteriaFactory);
        builderContext.registerBean("messageContext", this.messageContext);
        builderContext.registerBean("personService", this.personService);
    }
    
    protected ReconciliationCriteria constructReconciliationCriteria(final String firstName, final String lastName, final String ssn, final String emailAddress, final String phoneNumber, Date birthDate, final String sor){
        final ReconciliationCriteria reconciliationCriteria = reconciliationCriteriaFactory.getObject();
        reconciliationCriteria.setEmailAddress(emailAddress);
        reconciliationCriteria.setPhoneNumber(phoneNumber);

        final SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        sorPerson.setDateOfBirth(birthDate);
        sorPerson.setSourceSor(sor);
        sorPerson.setSsn(ssn);

        final Name name = sorPerson.addName(this.referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setFamily(lastName);
        name.setGiven(firstName);
        name.setOfficialName(true);

       
        return reconciliationCriteria;
    }

    @Test
    public void testStartFlow() {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
        MutableAttributeMap input = new LocalAttributeMap();
	    input.put("personSearch", criteria);
        ExternalContext context = new MockExternalContext();

        startFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    //validation errors found
    @Test
    public void testCriteriaSubmitError() throws ReconciliationException {
        final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, RUDYARD, "INVALID_SSN", null, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
        final ServiceExecutionResult<Person> serviceExecutionResult = mock(ServiceExecutionResult.class, RETURNS_SMART_NULLS);
        final Set<ConstraintViolation> violations = mock(Set.class, RETURNS_SMART_NULLS);
        final Iterator iter = mock(Iterator.class);
        final ConstraintViolation constraintViolation = mock(ConstraintViolation.class, RETURNS_SMART_NULLS);
        final Path path = mock(Path.class);
        final ConstraintDescriptor constraintDescriptor = mock(ConstraintDescriptor.class, RETURNS_SMART_NULLS);
        final Map map = mock(Map.class, RETURNS_SMART_NULLS);

        when(constraintViolation.getMessage()).thenReturn("errorCode");
        when(constraintViolation.getConstraintDescriptor()).thenReturn(constraintDescriptor);
        when(constraintDescriptor.getAttributes()).thenReturn(map);
        when(map.values()).thenReturn(new ArrayList());
        when(constraintDescriptor.getAnnotation()).thenReturn(new TestAnnotation());

        when(iter.next()).thenReturn(constraintViolation);
        when(iter.hasNext()).thenReturn(true).thenReturn(false);
        when(violations.iterator()).thenReturn(iter);
        when(violations.isEmpty()).thenReturn(false);
        when(serviceExecutionResult.succeeded()).thenReturn(false);
        when(serviceExecutionResult.getValidationErrors()).thenReturn(violations);
        when(personService.addPerson(criteria)).thenReturn(serviceExecutionResult);


        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        MockExternalContext context = new MockExternalContext();

        //submit with invalid input
        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    //reconciliation returns NONE.  Adds the new person.  Adds the role.
    @Test
    public void testAddNewPerson() throws ReconciliationException {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "UNIQUE_SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        final ServiceExecutionResult<Person> serviceExecutionResult = mock(ServiceExecutionResult.class, RETURNS_SMART_NULLS);
        final Person person = mock(Person.class, RETURNS_SMART_NULLS);
        when(serviceExecutionResult.succeeded()).thenReturn(true);
        when(serviceExecutionResult.getTargetObject()).thenReturn(person);

        when(person.getId()).thenReturn(1L);
        final Identifier identifier = mock(Identifier.class, RETURNS_SMART_NULLS);
        when(person.pickOutIdentifier("NETID")).thenReturn(identifier);

        when (identifier.getValue()).thenReturn("FOOBAR");
        final ActivationKey activationKey = mock(ActivationKey.class, RETURNS_SMART_NULLS);
        when(person.getCurrentActivationKey()).thenReturn(activationKey);

        when(activationKey.asString()).thenReturn("foobar");

        when(this.personService.findByPersonIdAndSorIdentifier(1L, "or-webapp")).thenReturn(criteria.getSorPerson());
        when(this.personService.addPerson(criteria)).thenReturn(serviceExecutionResult);

        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockAddRoleSubflow());

        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("roleAddedSucceeded");
    }

    //reconciliation returns reconciliation (view matches)
    public void testAddPersonMatchesFound() throws ReconciliationException {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        final ReconciliationException reconciliationException = mock(ReconciliationException.class);
        when(reconciliationException.multiplePeopleFound()).thenReturn(true);
        when(this.personService.addPerson(criteria)).thenThrow(reconciliationException);

        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("viewMatches");
    }

    //test case to start at viewMatches and continue to force add
    public void testForceAdd() throws ReconciliationException {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        final ServiceExecutionResult<Person> serviceExecutionResult = mock(ServiceExecutionResult.class, RETURNS_SMART_NULLS);
        final Person person = mock(Person.class, RETURNS_SMART_NULLS);
        when(serviceExecutionResult.succeeded()).thenReturn(true);
        when(serviceExecutionResult.getTargetObject()).thenReturn(person);

        when(person.getId()).thenReturn(1L);
        final Identifier identifier = mock(Identifier.class, RETURNS_SMART_NULLS);
        when(person.pickOutIdentifier("NETID")).thenReturn(identifier);

        when (identifier.getValue()).thenReturn("FOOBAR");
        final ActivationKey activationKey = mock(ActivationKey.class, RETURNS_SMART_NULLS);
        when(person.getCurrentActivationKey()).thenReturn(activationKey);

        when(activationKey.asString()).thenReturn("foobar");
        when(this.personService.findByPersonIdAndSorIdentifier(1L, "or-webapp")).thenReturn(criteria.getSorPerson());

        final ReconciliationException reconciliationException = mock(ReconciliationException.class, RETURNS_SMART_NULLS);
        final List personMatchList = mock(List.class);
        when(reconciliationException.getMatches()).thenReturn(personMatchList);

        when(this.personService.addPerson(criteria)).thenThrow(reconciliationException);
        when(this.personService.forceAddPerson(criteria)).thenReturn(serviceExecutionResult);

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
     public void testSelectMatchAddRole() throws ReconciliationException {
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        final ReconciliationException reconciliationException = mock(ReconciliationException.class);
        when(reconciliationException.multiplePeopleFound()).thenReturn(true);
        when(reconciliationException.getReconciliationType()).thenReturn(ReconciliationResult.ReconciliationType.EXACT);
        when(this.personService.addPerson(criteria)).thenThrow(reconciliationException);
        when(this.personService.findByPersonIdAndSorIdentifier(1L, "or-webapp")).thenReturn(criteria.getSorPerson());


        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);
//        getFlowScope().put("reconciliationResult", reconciliationException);

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
        Flow mockAddRoleFlow = new Flow("add-role");
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