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

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.validation.Errors;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Converts the OpenRegistry Validation Errors to the Spring Validation Errors.
 *
 * @author Scott Battaglia
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Named("springErrorValidationErrorConverter")
public final class SpringErrorValidationErrorConverter {

    /**
     * Converts the validation errors returned from the {@link org.openregistry.core.service.PersonService} into
     * Spring {@link org.springframework.validation.Errors}.
     *
     * @param validationErrors the errors provided by the {@link org.openregistry.core.service.PersonService}
     * @param errors an instance of Spring's {@link org.springframework.validation.Errors}
     */
    public void convertValidationErrors(final Set<ConstraintViolation> validationErrors, final Errors errors) {
		for (final ConstraintViolation violation : validationErrors) {
			errors.rejectValue(violation.getPropertyPath().toString(),
					violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
					violation.getConstraintDescriptor().getAttributes().values().toArray(),
					violation.getMessage());
		}
    }

    /**
     * Converts the validation errors returned from the {@link org.openregistry.core.service.PersonService} into
     * Spring {@link org.springframework.binding.message.MessageContext} for Spring Web flow.
     *
     * @param validationErrors the errors provided by the {@link org.openregistry.core.service.PersonService}
     * @param messages an instance of Spring's {@link org.springframework.binding.message.MessageContext}
     */
    public void convertValidationErrors(final Set<ConstraintViolation> validationErrors, final MessageContext messages) {
        for (final ConstraintViolation violation : validationErrors) {
            messages.addMessage(new MessageBuilder().error().args(violation.getConstraintDescriptor().getAttributes().values().toArray())
                    .source(violation.getPropertyPath().toString()).code(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()).defaultText(violation.getMessage()).build());
        }
    }
}
