package org.openregistry.core.web;

import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.Message;

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

    //TODO don't hardcode.
    private final String SOR_INDENTIFIER = "or-webapp";

    private final SpringErrorValidationErrorConverter converter = new SpringErrorValidationErrorConverter();

    public ServiceExecutionResult addSorPerson(PersonSearch personSearch, ReconciliationResult oldResult, MessageContext context) {
        personSearch.getPerson().setSourceSorIdentifier(SOR_INDENTIFIER);
        ServiceExecutionResult result = personService.addPerson(personSearch, oldResult);

        if (result.getValidationErrors() != null && !result.getValidationErrors().isEmpty()) {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return result;
        }

        ReconciliationResult reconciliationResult = result.getReconciliationResult();

        if (result.succeeded() && reconciliationResult == null){
            context.addMessage(new MessageBuilder().info().code("personAdded").build());
            return result;
        }

        if (result.succeeded() && reconciliationResult != null){
            ReconciliationResult.ReconciliationType resultType = reconciliationResult.getReconciliationType();

            if (resultType == ReconciliationResult.ReconciliationType.NONE)
                context.addMessage(new MessageBuilder().info().code("personAdded").build());
            else if (resultType == ReconciliationResult.ReconciliationType.EXACT)
                context.addMessage(new MessageBuilder().info().code("sorPersonAdded").build());
        }

        return result;
    }

    public boolean updateSorPerson(PersonSearch personSearch, MessageContext context) {
        ServiceExecutionResult result = personService.updateSorPerson(personSearch);
        if (result.succeeded()) {
            return true;
        }
        else {
            converter.convertValidationErrors(result.getValidationErrors(), context);
            return false;
        }

    }

}