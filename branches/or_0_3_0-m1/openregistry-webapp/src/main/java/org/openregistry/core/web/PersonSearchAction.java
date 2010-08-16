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

package org.openregistry.core.web;

import org.openregistry.core.service.PersonService;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageResolver;
import org.springframework.webflow.execution.RequestContext;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.ReconciliationException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Named("personSearchAction")
public final class PersonSearchAction extends AbstractPersonServiceAction {

    @Resource
    private String preferredPersonIdentifierType;

    @Inject
    public PersonSearchAction(final PersonService personService) {
        super(personService);
    }

    public String addSorPerson(final ReconciliationCriteria reconciliationCriteria, final RequestContext context) {
        reconciliationCriteria.getSorPerson().setSourceSor(AbstractPersonServiceAction.STATIC_SOR_NAME);
        try {
            final ServiceExecutionResult<Person> result = getPersonService().addPerson(reconciliationCriteria);
            
            getSpringErrorValidationErrorConverter().convertValidationErrors(result.getValidationErrors(), context.getMessageContext());

            if (context.getMessageContext().hasErrorMessages()) {
                return "validationError";
            }

            context.getFlowScope().put("serviceExecutionResult", result);
            return "success";
        } catch (final ReconciliationException e) {
            context.getFlowScope().put("reconciliationResult", e);
            return "reconciliation";
        }
    }

     public ServiceExecutionResult addSorPerson(final ReconciliationCriteria reconciliationCriteria, final MessageContext context) {
        reconciliationCriteria.getSorPerson().setSourceSor(AbstractPersonServiceAction.STATIC_SOR_NAME);
        final ServiceExecutionResult<Person> result = getPersonService().forceAddPerson(reconciliationCriteria);
        getSpringErrorValidationErrorConverter().convertValidationErrors(result.getValidationErrors(), context);

        return result;
    }

    public void setConfirmationMessage(final ServiceExecutionResult<Person> serviceExecutionResult, final ReconciliationResult reconciliationResult, final MessageContext context) {
        //if reconciliation result is EXACT or MAYBE then only a role was added, not a new person.
        //a force add, would result in no reconciliationResult.

        if (reconciliationResult != null){
            if (reconciliationResult.getReconciliationType() == ReconciliationResult.ReconciliationType.EXACT ||
                    reconciliationResult.getReconciliationType() == ReconciliationResult.ReconciliationType.MAYBE) {
                context.addMessage(new MessageBuilder().info().code("roleAdded").build());
                return;
            }
        }

        final Person person = serviceExecutionResult.getTargetObject();
        final Identifier netId = person.getPrimaryIdentifiersByType().get(this.preferredPersonIdentifierType);

        if (person.getCurrentActivationKey() != null) {
            final MessageResolver message = new MessageBuilder().info().code("personAddedFinalConfirm").arg(netId.getValue()).arg(person.getCurrentActivationKey().asString()).build();
            context.addMessage(message);
        } else {
            context.addMessage(new MessageBuilder().info().code("personAddedFinalConfirm").arg(netId.getValue()).arg("TempKey").build());
        }
    }
    
    public boolean updateSorPerson(SorPerson sorPerson, MessageContext context) {
        ServiceExecutionResult<SorPerson> result = getPersonService().updateSorPerson(sorPerson);
        return convertAndReturnStatus(result, context, null);
    }

    public boolean hasSorPersonRecord(final Person p, final String sourceSorId) {
        return getPersonService().findByPersonIdAndSorIdentifier(p.getId(), sourceSorId) != null;
    }

    public void setPreferredPersonIdentifierType(final String preferredPersonIdentifierType) {
        this.preferredPersonIdentifierType = preferredPersonIdentifierType;
    }
}