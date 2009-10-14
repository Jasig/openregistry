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

import static org.junit.Assert.*;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.sor.MockSorPerson;
import org.openregistry.core.domain.sor.SorPerson;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.beans.factory.ObjectFactory;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.MockPerson;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.DefaultPersonService;
import org.openregistry.core.service.identifier.NoOpIdentifierGenerator;
import org.openregistry.core.service.reconciliation.MockReconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.repository.MockPersonRepository;
import org.openregistry.core.repository.MockReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.context.ApplicationContext;

import java.util.Map;

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
        final ReconciliationCriteria reconiliationCriteria = new ReconciliationCriteria() {

            private MockSorPerson mockSorPerson = new MockSorPerson();
            public SorPerson getPerson() {
                return mockSorPerson;
            }

            public String getRegion() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getAddressLine2() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getEmailAddress() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setAddressLine1(String addressLine1) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getAddressLine1() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPostalCode() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setRegion(String region) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setCity(String city) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public Map<IdentifierType, String> getIdentifiersByType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setEmailAddress(String emailAddress) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setPhoneNumber(String phoneNumber) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setPostalCode(String postalCode) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getCity() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setAddressLine2(String addressLine2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPhoneNumber() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        reconiliationCriteria.getPerson().addName();
        return reconiliationCriteria;
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
        //context.setEventId("submitAddPerson");
        //resumeFlow(context);
        //assertCurrentStateEquals("addPerson");
    }


}