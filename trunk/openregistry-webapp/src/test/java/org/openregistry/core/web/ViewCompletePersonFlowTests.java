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

import org.jasig.openregistry.test.repository.MockReferenceRepository;
import org.jasig.openregistry.test.domain.MockPerson;
import org.jasig.openregistry.test.repository.MockPersonRepository;
import org.junit.Test;
import org.openregistry.core.domain.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.PersonService;
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
import org.openregistry.core.domain.sor.SorPerson;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.binding.mapping.Mapper;

import static org.mockito.Mockito.*;

/**
 * @author Nancy Mond
 * @since 1.0
 */
public final class ViewCompletePersonFlowTests extends AbstractXmlFlowExecutionTests {

    private PersonSearchAction personSearchAction;
    private ReferenceRepository referenceRepository;
    private PersonRepository personRepository;
    private PersonService personService;

    protected void setUp() {

        this.personService = mock(PersonService.class);

        this.personSearchAction = new PersonSearchAction(this.personService);
        this.referenceRepository = new MockReferenceRepository();
        this.personRepository = new MockPersonRepository(new MockPerson());
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
        return resourceFactory.createFileResource("src/main/webapp/WEB-INF/flows/view-complete-person.xml");
    }

    @Override
    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        builderContext.registerBean("personSearchAction", personSearchAction);
        builderContext.registerBean("referenceRepository", referenceRepository);
        builderContext.registerBean("personService", this.personService);
        builderContext.registerBean("personRepository", this.personRepository);
        builderContext.registerSubflow(createMockSearchSubflow());
    }

    @Test
    public void testStartFlow() {
        ExternalContext context = new MockExternalContext();
        startFlow(context);
        assertCurrentStateEquals("viewCompletePerson");
    }

    @Test
    public void testViewCompletePerson(){
        Person person = new MockPerson();
        setCurrentState("viewCompletePerson");
        getFlowScope().put("person", person);

        MockExternalContext context = new MockExternalContext();

        resumeFlow(context);
        assertCurrentStateEquals("viewCompletePerson");
    }

    /*
    @Test
    public void testViewCompletePersonRole(){
        Person person = new MockPerson();
        Role role = new MockRole();

        Long id=1L;
        when(personService.findPersonById(id)).thenReturn(person);
        setCurrentState("viewCompletePerson");
        getFlowScope().put("person", person);
        getFlowScope().put("role", role);

        MockExternalContext context = new MockExternalContext();

        context.setEventId("submitViewRole");
        resumeFlow(context);
        assertCurrentStateEquals("viewRole");
    }
*/

    // mock search subflow
    public Flow createMockSearchSubflow(){
        Flow mockSearchFlow = new Flow("searchForPerson");

        new EndState(mockSearchFlow, "personSelected");
        return mockSearchFlow;
    }
}