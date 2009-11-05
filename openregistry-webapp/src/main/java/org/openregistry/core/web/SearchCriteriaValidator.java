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
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;

/**
 * Bridges the Spring Validation code with the JaValid validation code.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component("searchCriteriaValidator")
public final class SearchCriteriaValidator implements Validator {

    private SpringValidatorAdapter validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());

    public final void validate(final Object o, final Errors errors) {
        this.validator.validate(o, errors);
    }

    public boolean supports(final Class aClass) {
        return true;
    }
}
