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
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.JaValidValidationError;

import java.util.*;

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
        Set<Identifier> ids = buildMockIdentifiers("-p1");
        ActivationKey key = mock(ActivationKey.class);
        when(key.asString()).thenReturn("mock***activation###key");
        when(mockPerson.getIdentifiers()).thenReturn(ids);
        when(mockPerson.getCurrentActivationKey()).thenReturn(key);

        //Stubbing 'no people' found result
        final ReconciliationResult mockNoPeopleFoundReconciliationResult = mock(ReconciliationResult.class);
        when(mockNoPeopleFoundReconciliationResult.noPeopleFound()).thenReturn(true);

        //Stubbing 'person exists' result
        final ReconciliationResult mockPersonAlreadyExistsReconciliationResult = mock(ReconciliationResult.class);
        when(mockPersonAlreadyExistsReconciliationResult.noPeopleFound()).thenReturn(false);
        when(mockPersonAlreadyExistsReconciliationResult.personAlreadyExists()).thenReturn(true);

        //Stubbing 'multiple people found' result
        final ReconciliationResult mockMultiplePeopleFoundReconciliationResult = mock(ReconciliationResult.class);
        when(mockMultiplePeopleFoundReconciliationResult.multiplePeopleFound()).thenReturn(true);
        List<PersonMatch> matches = buildMockPersonMatches();
        when(mockMultiplePeopleFoundReconciliationResult.getMatches()).thenReturn(matches);

        //Stubbing 'no people found' service execution result
        final ServiceExecutionResult mockNoPeopleFoundServiceExecutionResult = mock(ServiceExecutionResult.class);
        when(mockNoPeopleFoundServiceExecutionResult.succeeded()).thenReturn(true);
        when(mockNoPeopleFoundServiceExecutionResult.getReconciliationResult()).thenReturn(mockNoPeopleFoundReconciliationResult);
        when(mockNoPeopleFoundServiceExecutionResult.getTargetObject()).thenReturn(mockPerson);

        //Stubbing 'person exists' service execution result
        final ServiceExecutionResult mockPersonAlreadyExistsExecutionResult = mock(ServiceExecutionResult.class);
        when(mockPersonAlreadyExistsExecutionResult.succeeded()).thenReturn(true);
        when(mockPersonAlreadyExistsExecutionResult.getReconciliationResult()).thenReturn(mockPersonAlreadyExistsReconciliationResult);
        when(mockPersonAlreadyExistsExecutionResult.getTargetObject()).thenReturn(mockPerson);

        //Stubbing service execution result with validation errors
        final ServiceExecutionResult mockValidationErrorsExecutionResult = mock(ServiceExecutionResult.class);
        when(mockValidationErrorsExecutionResult.succeeded()).thenReturn(false);
        when(mockValidationErrorsExecutionResult.getValidationErrors())
                .thenReturn(new ArrayList<ValidationError>(Arrays.asList(new JaValidValidationError())));

        //Stubbing 'multiple people found' service execution result with validation errors
        final ServiceExecutionResult mockMultiplePeopleFoundExecutionResult = mock(ServiceExecutionResult.class);
        when(mockMultiplePeopleFoundExecutionResult.succeeded()).thenReturn(false);
        //No validation errors - empty list
        when(mockMultiplePeopleFoundExecutionResult.getValidationErrors()).thenReturn(Collections.<ValidationError>emptyList());
        when(mockMultiplePeopleFoundExecutionResult.getReconciliationResult()).thenReturn(mockMultiplePeopleFoundReconciliationResult);

        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        //stubbing different reconciliation scenarios
        when(ps.addPerson(argThat(new IsNewPersonMatch()), (ReconciliationResult) isNull())).thenReturn(mockNoPeopleFoundServiceExecutionResult);
        when(ps.addPerson(argThat(new IsExistingPersonMatch()), (ReconciliationResult) isNull())).thenReturn(mockPersonAlreadyExistsExecutionResult);
        when(ps.addPerson(argThat(new HasValidationErrors()), (ReconciliationResult) isNull())).thenReturn(mockValidationErrorsExecutionResult);
        when(ps.addPerson(argThat(new IsMultiplePeopleMatch()), (ReconciliationResult) isNull())).thenReturn(mockMultiplePeopleFoundExecutionResult);
        //Mocking 'force add' option
        when(ps.addPerson(argThat(new IsMultiplePeopleMatch()), (ReconciliationResult) isNotNull())).thenReturn(mockNoPeopleFoundServiceExecutionResult);


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

    private Set<Identifier> buildMockIdentifiers(String personSuffix) {
        //Mock it all up:
        //NetID
        Identifier mockNetId = mock(Identifier.class);
        IdentifierType mockNetIdType = mock(IdentifierType.class);
        when(mockNetIdType.getName()).thenReturn("NETID");
        when(mockNetId.getType()).thenReturn(mockNetIdType);
        when(mockNetId.getValue()).thenReturn("test-netid" + personSuffix);

        //RcpId
        Identifier mockRcpId = mock(Identifier.class);
        IdentifierType mockRcpIdType = mock(IdentifierType.class);
        when(mockRcpIdType.getName()).thenReturn("RCPID");
        when(mockRcpId.getType()).thenReturn(mockRcpIdType);
        when(mockRcpId.getValue()).thenReturn("test-rcpid" + personSuffix);

        return new HashSet<Identifier>(Arrays.asList(mockNetId, mockRcpId));
    }

    private List<PersonMatch> buildMockPersonMatches() {
        Person mockPerson1 = mock(Person.class);
        Set<Identifier> ids1 = buildMockIdentifiers("-p1");
        when(mockPerson1.getIdentifiers()).thenReturn(ids1);

        Person mockPerson2 = mock(Person.class);
        Set<Identifier> ids2 = buildMockIdentifiers("-p2");
        when(mockPerson2.getIdentifiers()).thenReturn(ids2);

        PersonMatch mockMatch1 = mock(PersonMatch.class);
        PersonMatch mockMatch2 = mock(PersonMatch.class);
        when(mockMatch1.getPerson()).thenReturn(mockPerson1);
        when(mockMatch2.getPerson()).thenReturn(mockPerson2);

        return Arrays.asList(mockMatch1, mockMatch2);
    }

    private static class IsNewPersonMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "new".equals(((ReconciliationCriteria) criteria).getPerson().getSsn());
        }
    }

    private static class IsExistingPersonMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "existing".equals(((ReconciliationCriteria) criteria).getPerson().getSsn());
        }
    }

    private static class IsMultiplePeopleMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "multiple".equals(((ReconciliationCriteria) criteria).getPerson().getSsn());
        }
    }

    private static class HasValidationErrors extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "errors".equals(((ReconciliationCriteria) criteria).getPerson().getSsn());
        }
    }
}
