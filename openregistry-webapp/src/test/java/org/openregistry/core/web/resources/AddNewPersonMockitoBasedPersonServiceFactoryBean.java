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

import org.jasig.openregistry.test.util.MockitoUtils;
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
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'POST /sor/{sorSourceId}/people' scenarios of <code>SystemOfRecordPeopleResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class AddNewPersonMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {
        //Stubbing Person
        Person mockPerson = mock(Person.class);
        Set ids = buildMockIdentifiers("-p1");
        ActivationKey key = mock(ActivationKey.class);
        when(key.asString()).thenReturn("mock***activation###key");
        when(mockPerson.getIdentifiers()).thenReturn(ids);
        when(mockPerson.pickOutIdentifier("NETID")).thenReturn((Identifier) ids.iterator().next());
        when(mockPerson.getCurrentActivationKey()).thenReturn(key);

        //Stubbing 'person exists' result
        final ReconciliationResult mockPersonAlreadyExistsReconciliationResult = mock(ReconciliationResult.class);
        when(mockPersonAlreadyExistsReconciliationResult.getReconciliationType()).thenReturn(ReconciliationResult.ReconciliationType.EXACT);
        List<PersonMatch> match = buildMockPersonMatchesWithOneMatch();
        when(mockPersonAlreadyExistsReconciliationResult.getMatches()).thenReturn(match);

        //Stubbing 'multiple people found' result
        final ReconciliationResult mockMultiplePeopleFoundReconciliationResult = mock(ReconciliationResult.class);
        when(mockMultiplePeopleFoundReconciliationResult.getReconciliationType()).thenReturn(ReconciliationResult.ReconciliationType.MAYBE);
        List<PersonMatch> matches = buildMockPersonMatches();
        when(mockMultiplePeopleFoundReconciliationResult.getMatches()).thenReturn(matches);

        //Stubbing 'no people found' service execution result
        final ServiceExecutionResult<Person> mockNoPeopleFoundServiceExecutionResult = mock(ServiceExecutionResult.class);
        when(mockNoPeopleFoundServiceExecutionResult.succeeded()).thenReturn(true);
        when(mockNoPeopleFoundServiceExecutionResult.getTargetObject()).thenReturn(mockPerson);

        //Stubbing 'person exists' service execution result
        final ServiceExecutionResult mockPersonAlreadyExistsExecutionResult = mock(ServiceExecutionResult.class);
        when(mockPersonAlreadyExistsExecutionResult.succeeded()).thenReturn(true);
        when(mockPersonAlreadyExistsExecutionResult.getTargetObject()).thenReturn(mockPerson);

        //Stubbing service execution result with validation errors
        final ServiceExecutionResult<Person> mockValidationErrorsExecutionResult = mock(ServiceExecutionResult.class);
        Set<ConstraintViolation> mockValidationErrors = MockitoUtils.oneMinimalisticMockConstraintViolation();
        when(mockValidationErrorsExecutionResult.getValidationErrors()).thenReturn(mockValidationErrors);

        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        //stubbing different reconciliation scenarios
        when(ps.addPerson(argThat(new IsNewPersonMatch()))).thenReturn(mockNoPeopleFoundServiceExecutionResult);
        when(ps.addPerson(argThat(new IsExistingPersonMatch()))).thenThrow(new ReconciliationException(mockPersonAlreadyExistsReconciliationResult));
         when(ps.addPerson(argThat(new IsExistingErrorPersonMatch()))).thenThrow(new IllegalStateException());
        when(ps.addPerson(argThat(new HasValidationErrors()))).thenReturn(mockValidationErrorsExecutionResult);
        when(ps.addPerson(argThat(new IsMultiplePeopleMatch()))).thenThrow(new ReconciliationException(mockMultiplePeopleFoundReconciliationResult));
        //Mocking 'force add' option
        when(ps.forceAddPerson(argThat(new IsMultiplePeopleMatch()))).thenReturn(mockNoPeopleFoundServiceExecutionResult);

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
        Set ids1 = buildMockIdentifiers("-p1");
        when(mockPerson1.getIdentifiers()).thenReturn(ids1);
        when(mockPerson1.pickOutIdentifier("NETID")).thenReturn((Identifier) ids1.iterator().next());

        Person mockPerson2 = mock(Person.class);
        Set ids2 = buildMockIdentifiers("-p2");
        when(mockPerson2.getIdentifiers()).thenReturn(ids2);
        when(mockPerson2.pickOutIdentifier("NETID")).thenReturn((Identifier) ids2.iterator().next());

        PersonMatch mockMatch1 = mock(PersonMatch.class);
        PersonMatch mockMatch2 = mock(PersonMatch.class);
        when(mockMatch1.getPerson()).thenReturn(mockPerson1);
        when(mockMatch2.getPerson()).thenReturn(mockPerson2);

        return Arrays.asList(mockMatch1, mockMatch2);
    }

    private List<PersonMatch> buildMockPersonMatchesWithOneMatch() {
        Person mockPerson1 = mock(Person.class);
        Set ids1 = buildMockIdentifiers("-p1");
        when(mockPerson1.getIdentifiers()).thenReturn(ids1);
        when(mockPerson1.pickOutIdentifier("NETID")).thenReturn((Identifier) ids1.iterator().next());

        PersonMatch mockMatch1 = mock(PersonMatch.class);
        when(mockMatch1.getPerson()).thenReturn(mockPerson1);

        return Arrays.asList(mockMatch1);
    }

    private static class IsNewPersonMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "new".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

    private static class IsExistingPersonMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "existing".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

    private static class IsExistingErrorPersonMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "existingError".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

    private static class IsMultiplePeopleMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "multiple".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

    private static class HasValidationErrors extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "errors".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }
}
