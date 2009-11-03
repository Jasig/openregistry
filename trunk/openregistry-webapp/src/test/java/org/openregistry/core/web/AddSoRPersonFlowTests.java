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

import org.javalid.core.AnnotationValidator;
import org.javalid.core.JavalidValidationCallbackHandler;
import org.javalid.core.ValidationMessage;
import org.javalid.core.resource.LocaleResolver;
import org.javalid.core.resource.MessageCodeResolver;
import org.junit.Test;
import org.openregistry.core.domain.MockPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.MockSorPerson;
import org.openregistry.core.repository.MockPersonRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.service.DefaultPersonService;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.identifier.NoOpIdentifierGenerator;
import org.openregistry.core.service.reconciliation.MockReconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.springframework.beans.BeansException;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.binding.message.MessageBuilder;
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
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.MockReferenceRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.web.factory.MockReconciliationCriteriaFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.binding.mapping.Mapper;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private PersonRepository personRepository = new MockPersonRepository();

    private DefaultPersonService personService;

    private MockReconciler mockReconciler;

    private DefaultMessageContext messageContext;

    protected void setUp() {
        this.mockReconciler = new MockReconciler();

        final StaticMessageSource staticMessageSource = new StaticMessageSource();
        staticMessageSource.addMessage("personAddedFinalConfirm", Locale.getDefault(), "test");
        staticMessageSource.addMessage("roleAdded", Locale.getDefault(), "test");
        staticMessageSource.addMessage("errorCode", Locale.getDefault(), "test");

        this.messageContext = new DefaultMessageContext(staticMessageSource);
        this.personService = new DefaultPersonService(personRepository,referenceRepository, new NoOpIdentifierGenerator(), this.mockReconciler);
        personSearchAction = new PersonSearchAction(this.personService);
        referenceRepository = new MockReferenceRepository();
        reconciliationCriteriaFactory = new MockReconciliationCriteriaFactory();

        this.personService.setPersonObjectFactory(new ObjectFactory<Person>() {
            public Person getObject() throws BeansException {
                return new MockPerson();
            }
        });
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
        builderContext.registerBean("messageContext", this.messageContext);
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
        name.setOfficialName(true);

       
        return reconciliationCriteria;
    }

    @Test
    public void testStartFlow() {
        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.NONE);
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);
        MutableAttributeMap input = new LocalAttributeMap();
	    input.put("personSearch", criteria);
        ExternalContext context = new MockExternalContext();

        startFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    //validation errors found
    @Test
    public void testCriteriaSubmitError() {
        this.messageContext.addMessage(new MessageBuilder().error().code("errorCode").build());
        this.personService.setAnnotationValidator(new AnnotationValidator<Object>() {
            public List<ValidationMessage> validateObject(Object o) {
                return validateObject(o, "group");
            }

            public List<ValidationMessage> validateObject(Object o, String s) {
                final ValidationMessage validationMessage = new ValidationMessage("", "errorCode", new Object[0], true);
                return Arrays.asList(validationMessage);
            }

            public List<ValidationMessage> validateObject(Object o, String s, JavalidValidationCallbackHandler javalidValidationCallbackHandler) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, boolean b) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, boolean b, JavalidValidationCallbackHandler javalidValidationCallbackHandler) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1, JavalidValidationCallbackHandler javalidValidationCallbackHandler) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1, boolean b) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1, boolean b, JavalidValidationCallbackHandler javalidValidationCallbackHandler) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1, boolean b, int i) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateObject(Object o, String s, String s1, boolean b, int i, JavalidValidationCallbackHandler javalidValidationCallbackHandler) {
                return validateObject(o);
            }

            public List<ValidationMessage> validateProperty(Object o, String s, Object o1, String s1, String s2) {
                return null;
            }

            public LocaleResolver getLocaleResolver() {
                return null;
            }

            public void setLocaleResolver(LocaleResolver localeResolver) {
            }

            public MessageCodeResolver getMessageCodeResolver() {
                return null;
            }

            public void setMessageCodeResolver(MessageCodeResolver messageCodeResolver) {
            }

            public boolean isMessageResolvingEnabled() {
                return false;
            }
        });

        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.NONE);
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
        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.NONE);
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
        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.MAYBE);
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
        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.MAYBE);
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
        this.mockReconciler.setReconciliationType(ReconciliationResult.ReconciliationType.MAYBE);
        ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, "SSN", EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER);

        final MockSorPerson mockSorPerson = new MockSorPerson();
        mockSorPerson.setPersonId(1L);
        mockSorPerson.setSourceSor("or-webapp");

        final MockPerson mockPerson = new MockPerson();
        mockPerson.setId(1L);

        this.personRepository.saveSorPerson(mockSorPerson);
        this.personRepository.savePerson(mockPerson);

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