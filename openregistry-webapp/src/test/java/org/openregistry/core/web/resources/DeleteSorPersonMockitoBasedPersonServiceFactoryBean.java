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

import org.mockito.ArgumentMatcher;
import static org.mockito.Mockito.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.service.JaValidValidationError;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.springframework.beans.factory.FactoryBean;

import java.util.*;

/**
 * FactoryBean to create Mockito-based mocks of <code>PersonService</code> and related collaborators needed to test
 * 'DELETE /people/sor/{sorSource}/{sorId}' scenarios of <code>PeopleResource</code>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class DeleteSorPersonMockitoBasedPersonServiceFactoryBean implements FactoryBean<PersonService> {

    PersonService mockPersonService;

    public void init() throws Exception {
        //Stubbing PersonService
        final PersonService ps = mock(PersonService.class);
        //stubbing different deletion scenarios
        when(ps.deleteSystemOfRecordPerson(eq("TEST-SOR"), eq("NON-EXISTING-PERSON"), eq(false), eq(Type.TerminationTypes.UNSPECIFIED.name()))).thenThrow(new PersonNotFoundException());
        when(ps.deleteSystemOfRecordPerson(eq("TEST-SOR"), eq("EXISTING-PERSON"), eq(false), eq(Type.TerminationTypes.UNSPECIFIED.name()))).thenReturn(true);
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
}