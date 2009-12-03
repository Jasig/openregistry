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
package org.openregistry.core.utils;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Various static validation helpers
 *
 * @since 1.0
 */
public final class ValidationUtils {

    //Prevents instantiation
    private ValidationUtils() {
    }

    public static List<String> buildValidationErrorsResponseAsList(final Set<ConstraintViolation> validationErrors) throws NullPointerException {
        if (validationErrors.isEmpty()) {
            return null;
        }
        final List<String> errs = new ArrayList<String>(validationErrors.size());
        for (final ConstraintViolation violation : validationErrors) {
            errs.add(String.format("The [%s] field value is invalid.", violation.getPropertyPath().toString()));
        }
        return errs;
    }
}
