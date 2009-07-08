package org.openregistry.core.web;

import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PersonSearchAction {

    @Autowired(required=true)
    private PersonService personService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    //TODO don't hardcode. OR-55
    private final String SOR_INDENTIFIER = "or-webapp";

    private final String identifierType = "NETID";

    private final String EXACT = "EXACT";

    private final SpringErrorValidationErrorConverter converter = new SpringErrorValidationErrorConverter();

    public ServiceExecutionResult addSorPerson(ReconciliationCriteria reconciliationCriteria, ReconciliationResult oldResult, MessageContext context) {
        reconciliationCriteria.getPerson().setSourceSorIdentifier(SOR_INDENTIFIER);
        ServiceExecutionResult result = personService.addPerson(reconciliationCriteria, oldResult);

        if (result.getValidationErrors() != null && !result.getValidationErrors().isEmpty()) {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return result;
        }

        ReconciliationResult reconciliationResult = result.getReconciliationResult();
        if (result.succeeded()){
            if (reconciliationResult != null){
                ReconciliationResult.ReconciliationType resultType = reconciliationResult.getReconciliationType();
                if (resultType == ReconciliationResult.ReconciliationType.EXACT)
                    context.addMessage(new MessageBuilder().info().code("sorPersonFound").build());
            }
        }

        return result;
    }

    public void setConfirmationMessage(Person person, ReconciliationResult reconciliationResult, MessageContext context){
        if (reconciliationResult != null){
            if (reconciliationResult.getReconciliationType() == ReconciliationResult.ReconciliationType.EXACT) {
                context.addMessage(new MessageBuilder().info().code("roleAdded").build());
                return;
            }
        }

        Identifier netId = person.pickOutIdentifier(identifierType);
        context.addMessage(new MessageBuilder().info().code("personAddedFinalConfirm").arg(netId.getValue()).arg(person.getActivationKey().getKeyAsString()).build());
    }

    public boolean updateSorPerson(SorPerson sorPerson, MessageContext context) {
        ServiceExecutionResult result = personService.updateSorPerson(sorPerson);
        if (result.succeeded()) {
            return true;
        }
        else {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return false;
        }

    }

}