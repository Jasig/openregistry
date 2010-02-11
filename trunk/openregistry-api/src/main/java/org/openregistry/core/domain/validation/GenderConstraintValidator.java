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
package org.openregistry.core.domain.validation;

import org.openregistry.core.domain.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Validates the gender supplied against a list of allowed values.
 *
 * @version $Revision$ $Date$
 * @since 1.0
 */
public final class GenderConstraintValidator implements ConstraintValidator<Gender,String> {

    private String[] acceptableValues = new String[] {"M", "F"};

    @Override
    public void initialize(final Gender gender) {
        // nothing to do
    }

    @Override
    public boolean isValid(final String string, final ConstraintValidatorContext constraintValidatorContext) {       
        return string == null || Arrays.asList(acceptableValues).contains(string);
    }
}
