package org.openregistry.activation.web;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ActivationService;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class IdentifierAction {

    @Autowired(required=true)
    private ActivationService activationService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final String identifierType = "NETID";

    public boolean verifyActivationKey(Identifier identifier, String activationKey, MessageContext context) {
        //verify parameters entered
        logger.info("netid: "+ identifier.getValue() + "activationKey: " + activationKey);
        //Verify activation key
        ServiceExecutionResult result = activationService.verifyActivationKey(identifierType, identifier.getValue(), activationKey);

        if (result.getValidationErrors() != null && !result.getValidationErrors().isEmpty()) {
            convertErrors(result.getValidationErrors(), context);
            return false;
        }
        
        return true;
    }

    public boolean activate(Identifier identifier, String password, MessageContext context){
        //TODO where is the password stored?

        logger.info("IdentifierAction: activateNetID: netid: " + identifier.getValue() + " password: " + password);
        ServiceExecutionResult result = activationService.activateNetID(identifierType, identifier, password);

        if (result.getValidationErrors() != null && !result.getValidationErrors().isEmpty()) {
            convertErrors(result.getValidationErrors(), context);
            return false;
        }
        if (result.succeeded()){
            context.addMessage(new MessageBuilder().info().code("netIDActivationConfirmation").build());
            return true;
        }
        context.addMessage(new MessageBuilder().error().code("netIDActivationError").build());
        return false;
    }

    protected void convertErrors(List<ValidationError> validationErrors, MessageContext context){
        for (final ValidationError validationError : validationErrors) {
            if (validationError.getField() == null) {
                context.addMessage(new MessageBuilder().error().args(validationError.getArguments()).code(validationError.getCode()).build());
            } else {
                context.addMessage(new MessageBuilder().error().args(validationError.getArguments()).source(validationError.getField()).code(validationError.getCode()).build());
            }
        }
    }

}