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
package org.openregistry.core.web.resources;

import org.springframework.beans.factory.FactoryBean;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatcher;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'POST /people' scenarios of <code>PeopleResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class AddNewPersonMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() {
        //Stubbing Person
        Person mockPerson = mock(Person.class);
        Set<Identifier> ids = buildMockIdentifiers();
        when(mockPerson.getIdentifiers()).thenReturn(ids);

        //Stubbing no people found result
        final ReconciliationResult mockNoPeopleFoundReconciliationResult = mock(ReconciliationResult.class);
        when(mockNoPeopleFoundReconciliationResult.noPeopleFound()).thenReturn(true);

        //Stubbing no people found service execution result
        final ServiceExecutionResult mockNoPeopleFoundServiceExecutionResult = mock(ServiceExecutionResult.class);
        when(mockNoPeopleFoundServiceExecutionResult.succeeded()).thenReturn(true);
        when(mockNoPeopleFoundServiceExecutionResult.getReconciliationResult()).thenReturn(mockNoPeopleFoundReconciliationResult);
        when(mockNoPeopleFoundServiceExecutionResult.getTargetObject()).thenReturn(mockPerson);

        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        when(ps.addPerson(argThat(new IsNewPerson()), (ReconciliationResult) isNull())).thenReturn(mockNoPeopleFoundServiceExecutionResult);
        this.mockPersonService = ps;
    }

    public PersonService getObject() throws Exception {
        return this.mockPersonService;
    }

    public Class<? extends PersonService> getObjectType() {
        return PersonService.class;
    }

    public boolean isSingleton() {
        return true;
    }

    private Set<Identifier> buildMockIdentifiers() {
        //Mock it all up:
        //NetID
        Identifier mockNetId = mock(Identifier.class);
        IdentifierType mockNetIdType = mock(IdentifierType.class);
        when(mockNetIdType.getName()).thenReturn("NETID");
        when(mockNetId.getType()).thenReturn(mockNetIdType);
        when(mockNetId.getValue()).thenReturn("test-netid");

        //RcpId
        Identifier mockRcpId = mock(Identifier.class);
        IdentifierType mockRcpIdType = mock(IdentifierType.class);
        when(mockRcpIdType.getName()).thenReturn("RCPID");
        when(mockRcpId.getType()).thenReturn(mockRcpIdType);
        when(mockRcpId.getValue()).thenReturn("test-rcpid");

        return new HashSet<Identifier>(Arrays.asList(mockNetId, mockRcpId));

    }


    private static class IsNewPerson extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return "new".equals(((ReconciliationCriteria) criteria).getPerson().getSsn());
        }
    }
}
