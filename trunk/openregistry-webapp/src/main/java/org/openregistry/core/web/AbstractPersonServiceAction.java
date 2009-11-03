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

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required=false)
    private SpringErrorValidationErrorConverter converter = new SpringErrorValidationErrorConverter();

    private final PersonService personService;

    protected AbstractPersonServiceAction(final PersonService personService) {
        this.personService = personService;
    }

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
