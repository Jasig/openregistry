package org.openregistry.activation.web;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.PersonNotFoundException;
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

    public boolean verifyActivationKey(Identifier identifier, String activationKey, String password, MessageContext context) {
        try {
            final ActivationKey oActivationKey = this.activationService.getActivationKey(identifierType, identifier.getValue(), activationKey);

            if (oActivationKey.isValid()) {
                if (activate(identifier, password, context)){
                    this.activationService.invalidateActivationKey(identifierType, identifier.getValue(), activationKey);
                    return true;
                }
            }

            if (oActivationKey.isNotYetValid()){
                context.addMessage(new MessageBuilder().error().code("activationKeyNotYetValid").build());
            }

            if (oActivationKey.isExpired()){
                context.addMessage(new MessageBuilder().error().code("activationKeyExpired").build());
            }

        }
        catch(PersonNotFoundException e) {
            context.addMessage(new MessageBuilder().error().code("personNotFound").build());
        }
        catch (IllegalArgumentException e) {
            context.addMessage(new MessageBuilder().error().code("activationKeyNotFound").arg(activationKey).build());
        }
        catch (IllegalStateException e) {
            context.addMessage(new MessageBuilder().error().code("activationKeyNotFound").arg(activationKey).build());
        }

        return false;
    }

    protected boolean activate(Identifier identifier, String password, MessageContext context){
        //TODO where is the password stored?

        context.addMessage(new MessageBuilder().info().code("activationConfirmation").build());
        return true;
    }
}