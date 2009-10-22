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

import org.openregistry.core.service.GeneralServiceExecutionResult;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.MockSorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.MockPerson;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.MockMaybeReconciliationResult;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public final class MockPersonSearchAction extends AbstractPersonServiceAction {

    private final String AddServiceName = "PersonService.addPerson";

    public String addSorPerson(final ReconciliationCriteria reconciliationCriteria, final RequestContext context) {

        if (reconciliationCriteria.getPerson().getSsn().equals("INVALID_SSN")){
            return "validationError";
        }

        ServiceExecutionResult<Person> result = new GeneralServiceExecutionResult<Person>(new MockPerson());

        if (reconciliationCriteria.getPerson().getSsn().equals("UNIQUE_SSN")){
            context.getFlowScope().put("serviceExecutionResult", result);
            return "success";
        } else {
            //not currently getting populated with matches.
            MockMaybeReconciliationResult reconciliationResult = new MockMaybeReconciliationResult();
            context.getFlowScope().put("reconciliationResult", reconciliationResult);
            return "reconciliation";
        }
    }

     public ServiceExecutionResult addSorPerson(final ReconciliationCriteria reconciliationCriteria, final MessageContext context) {
        ServiceExecutionResult<Person> result = new GeneralServiceExecutionResult<Person>(new MockPerson());
        return result;
    }

    public void setConfirmationMessage(final ServiceExecutionResult<Person> serviceExecutionResult, final ReconciliationResult reconciliationResult, final MessageContext context) {

    }

    public boolean updateSorPerson(SorPerson sorPerson, MessageContext context) {
        return true;
    }

    public boolean hasSorPersonRecord(final Person p, final String sourceSorId) {
        return true;
    }

    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSourceIdentifier){
        return new MockSorPerson();
    }

}