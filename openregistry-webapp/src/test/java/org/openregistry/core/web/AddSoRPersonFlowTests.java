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
import org.springframework.webflow.test.MockRequestContext;
import org.springframework.webflow.test.MockFlowExecutionContext;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.execution.View;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionContext;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.beans.factory.ObjectFactory;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.MockPerson;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.DefaultPersonService;
import org.openregistry.core.service.identifier.NoOpIdentifierGenerator;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.service.reconciliation.MockReconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.repository.MockPersonRepository;
import org.openregistry.core.repository.MockReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.aspect.OpenRegistryMessageSourceAccessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;

/**
 * @author Nancy Mond
 * @since 1.0
 */
public class AddSoRPersonFlowTests extends AbstractXmlFlowExecutionTests {

    private final String OR_WEBAPP_IDENTIFIER = "or-webapp";
	private final String REGISTRAR_IDENTIFIER = "registrar";

	private static final String EMAIL_ADDRESS = "test@test.edu";
	private static final String PHONE_NUMBER = "555-555-5555";
	private static final String RUDYARD = "Rudyard";
	private static final String KIPLING = "Kipling";
	private static final String RUDY = "Rudy";
	private static final String KIPSTEIN = "Kipstein";

    private PersonSearchAction personSearchAction;
    private PersonRepository personRepository;
    private DefaultPersonService personService;
    private ObjectFactory<Person> objectFactory;
    private ServiceExecutionResult serviceExecutionResult;
    private SpringErrorValidationErrorConverter converter;
    private ApplicationContext applicationContext;

    protected void setUp(){
        //personService = EasyMock.createMock(PersonService.class);
        this.personRepository = new MockPersonRepository(new MockPerson());
        this.objectFactory = new ObjectFactory<Person>() {
            public Person getObject() {
                return new MockPerson();
            }
        };
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationResult.ReconciliationType.NONE));
        this.personService.setPersonObjectFactory(this.objectFactory);
        this.converter = new SpringErrorValidationErrorConverter();
        personSearchAction = new PersonSearchAction();
        personSearchAction.setPersonService(this.personService);
        personSearchAction.setSpringErrorValidationErrorConverter(converter);
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
            return resourceFactory.createFileResource("src/main/webapp/WEB-INF/flows/addPerson.xml");
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
        builderContext.registerBean("personSearchAction", personSearchAction);
    }
    protected ReconciliationCriteria constructReconciliationCriteria(){
        ReconciliationCriteria criteria = new JpaReconciliationCriteriaImpl();
        criteria.getPerson().addName();
        return criteria;
    }

    public void testStartFlow() {
        ReconciliationCriteria criteria = constructReconciliationCriteria();
        MutableAttributeMap input = new LocalAttributeMap();
	    input.put("personSearch", criteria);
        ExternalContext context = new MockExternalContext();

        startFlow(context);
        assertCurrentStateEquals("addPerson");
    }

    public void testCriteriaSubmitError() {
        ReconciliationCriteria criteria = constructReconciliationCriteria();
        setCurrentState("addPerson");
        getFlowScope().put("personSearch", criteria);

        MockExternalContext context = new MockExternalContext();

        //submit with no input
        context.setEventId("submitAddPerson");
        resumeFlow(context);
        assertCurrentStateEquals("addPerson");
    }


}