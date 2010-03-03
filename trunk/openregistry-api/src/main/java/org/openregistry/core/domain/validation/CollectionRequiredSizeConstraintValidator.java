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

import org.openregistry.core.domain.annotation.RequiredSize;

import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * Validates the @RequiredSize annotation.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class CollectionRequiredSizeConstraintValidator extends AbstractSystemOfRecordConstraintValidator<RequiredSize, Collection> {

    private String property;

    public void initialize(final RequiredSize required) {
        this.property = required.property();
    }

    public boolean isValid(final Collection o, final ConstraintValidatorContext constraintValidatorContext) {
        return o == null || getSoRSpecification().isWithinRequiredSize(this.property, o);
    }
}
