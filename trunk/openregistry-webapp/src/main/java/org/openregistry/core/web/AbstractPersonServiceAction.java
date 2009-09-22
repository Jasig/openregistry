package org.openregistry.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;

import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractPersonServiceAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required=true)
    private SpringErrorValidationErrorConverter converter;

    @Autowired(required=true)
    private PersonService personService;

    protected final PersonService getPersonService() {
        return this.personService;
    }

    protected final SpringErrorValidationErrorConverter getSpringErrorValidationErrorConverter() {
        return this.converter;
    }

    public final boolean convertAndReturnStatus(final ServiceExecutionResult<?> serviceExecutionResult, final MessageContext messageContext, final String successMessageCode) {
        this.converter.convertValidationErrors(serviceExecutionResult.getValidationErrors(), messageContext);

        if (serviceExecutionResult.succeeded() && successMessageCode != null) {
            messageContext.addMessage(new MessageBuilder().info().code(successMessageCode).build());
        }
        return serviceExecutionResult.succeeded();
    }
}
