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

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Component;
import org.javalid.external.spring.SpringValidator;
import org.javalid.external.spring.SpringMessageConverter;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidatorParams;
import org.javalid.core.ValidationMessage;
import org.javalid.core.resource.MessageCodeResourceBundleResolverImpl;
import org.javalid.core.config.JvConfiguration;
import org.javalid.annotations.core.JvGroup;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

/**
 * Bridges the Spring Validation code with the JaValid validation code.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component("searchCriteriaValidator")
public final class SearchCriteriaValidator implements Validator {

    private final SpringMessageConverter springMessageConverter = new SpringMessageConverter();

    private final AnnotationValidatorImpl v = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    public final void validate(final Object o, final Errors errors) {
        final List<ValidationMessage> validationMessageList = this.v.validateObject(o, JvGroup.DEFAULT_GROUP, "", true, -1);
        this.springMessageConverter.convertMessages(validationMessageList, errors);
    }

    public boolean supports(final Class aClass) {
        return true;
    }
}
