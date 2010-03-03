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
package org.openregistry.integration.support;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import org.openregistry.integration.IdentifierChangeEventNotificationService;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;

import javax.inject.Inject;

/**
 * Aspect to intercept identifier change service invocations and fire identifier change event messages
 * to an external integration endpoint
 * @since 1.0
 */
@Aspect
public class IdentifierChangeAspect {

    @Inject
    private IdentifierChangeEventNotificationService idChangeNotificationService;

    @AfterReturning("(execution (* org.openregistry.core.service.IdentifierChangeService.change(..)))")
    public void fireIdentifierChangeEvent(final JoinPoint joinPoint) {
        IdentifierType internalType = (IdentifierType)joinPoint.getArgs()[0];
        Identifier internalId = (Identifier)joinPoint.getArgs()[1];
        IdentifierType changedType = (IdentifierType)joinPoint.getArgs()[2];
        Identifier changedId = (Identifier)joinPoint.getArgs()[3];
        this.idChangeNotificationService.createAndSendEventMessageFor(internalType.getName(), internalId.getValue(), changedType.getName(), changedId.getValue());                
    }
}
