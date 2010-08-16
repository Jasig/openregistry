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

package org.openregistry.core.web.resources;

import org.mockito.ArgumentMatcher;

import static org.mockito.Mockito.*;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.service.PersonService;
import org.springframework.beans.factory.FactoryBean;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'POST /people/{personIdType}/{personId}' scenarios of <code>PeopleResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class LinkSorPersonMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {
        //Stubbing Person
        final Person mockPerson = mock(Person.class);
        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        when(ps.findPersonByIdentifier(eq("TEST-ID-TYPE"), eq("NON-EXISTING-PERSON"))).thenReturn(null);
        when(ps.findPersonByIdentifier(eq("TEST-ID-TYPE"), eq("EXISTING-PERSON"))).thenReturn(mockPerson);
        //We don't care about the return value as the REST implementation doesn't use it
        when(ps.addPersonAndLink(argThat(new IsLinkSorPersonGoodMatch()), any(Person.class))).thenReturn(null);
        when(ps.addPersonAndLink(argThat(new IsLinkSorPersonBadMatch()), any(Person.class))).thenThrow(new IllegalStateException());

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

    private static class IsLinkSorPersonGoodMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "link-sor-good".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

    private static class IsLinkSorPersonBadMatch extends ArgumentMatcher<ReconciliationCriteria> {

        @Override
        public boolean matches(Object criteria) {
            return (criteria == null) ? false : "link-sor-bad".equals(((ReconciliationCriteria) criteria).getSorPerson().getSsn());
        }
    }

}