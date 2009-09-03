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

    @Autowired(required=true)
    private PersonService personService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    //private final String IDENTIFIER_TYPE = "NETID";

    public boolean generateActivationKey(Identifier identifier, MessageContext context) {

        if (identifier.getValue() == null || identifier.getValue().trim().equals("")){
            context.addMessage(new MessageBuilder().error().code("identifierValueRequired").build());
            return false;
        }

        if (identifier.getType() == null || identifier.getType().getName() == null || identifier.getType().getName().trim().equals("")){
            context.addMessage(new MessageBuilder().error().code("identifierTypeRequired").build());
            if (identifier.getType() == null) logger.info("generateActivationKey: no type");
            if (identifier.getType().getName() == null) logger.info("generateActivationKey: no type value given");
            return false;
        }

        try {
            //ActivationKey key = activationService.generateActivationKey(IDENTIFIER_TYPE, identifier.getValue());
            ActivationKey key = activationService.generateActivationKey(identifier.getType().getName(), identifier.getValue());
            context.addMessage(new MessageBuilder().info().code("newActivationKey").arg(key.asString()).build());
        }
        catch(PersonNotFoundException e) {
            context.addMessage(new MessageBuilder().error().code("personNotFound").build());
            return false;
        }
        catch (IllegalArgumentException e) {
            context.addMessage(new MessageBuilder().error().code("generateActivationKeyError").build());
            return false;
        }

        return true;
    }

}