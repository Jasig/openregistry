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

import org.openregistry.core.service.ValidationError;
import org.springframework.validation.Errors;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts the OpenRegistry Validation Errors to the Spring Validation Errors.
 *
 * @author Scott Battaglia
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component(value = "springErrorValidationErrorConverter")
public final class SpringErrorValidationErrorConverter {

    /**
     * Converts the validation errors returned from the {@link org.openregistry.core.service.PersonService} into
     * Spring {@link org.springframework.validation.Errors}.
     *
     * @param validationErrors the errors provided by the {@link org.openregistry.core.service.PersonService}
     * @param errors an instance of Spring's {@link org.springframework.validation.Errors}
     */
    public void convertValidationErrors(final List<ValidationError> validationErrors, final Errors errors) {
        for (final ValidationError validationError : validationErrors) {
            if (validationError.getField() == null) {
                errors.reject(validationError.getCode(), validationError.getArguments(), null);
            } else {
                errors.rejectValue(validationError.getField(), validationError.getCode(), validationError.getArguments(), null);
            }
        }
    }

    /**
     * Converts the validation errors returned from the {@link org.openregistry.core.service.PersonService} into
     * Spring {@link org.springframework.binding.message.MessageContext} for Spring Web flow.
     *
     * @param validationErrors the errors provided by the {@link org.openregistry.core.service.PersonService}
     * @param messages an instance of Spring's {@link org.springframework.binding.message.MessageContext}
     */
    public void convertValidationErrors(final List<ValidationError> validationErrors, final MessageContext messages) {
        for (final ValidationError validationError : validationErrors) {
            if (validationError.getField() == null) {
                messages.addMessage(new MessageBuilder().error().args(validationError.getArguments()).code(validationError.getCode()).build());
            } else {
                messages.addMessage(new MessageBuilder().error().args(validationError.getArguments()).source(validationError.getField()).code(validationError.getCode()).build());
            }
        }
    }
}
