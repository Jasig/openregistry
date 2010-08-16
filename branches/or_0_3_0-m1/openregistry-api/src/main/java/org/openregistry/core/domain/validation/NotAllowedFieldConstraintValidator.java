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

package org.openregistry.core.domain.validation;

import org.openregistry.core.domain.annotation.NotAllowed;

import javax.validation.ConstraintValidatorContext;

/**
 * Determines whether this field is allowed for a particular SoR or not.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class NotAllowedFieldConstraintValidator extends AbstractSystemOfRecordConstraintValidator<NotAllowed,Object> {

    private String property;

    public void initialize(final NotAllowed required) {
        this.property = required.property();
    }

    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        final boolean disallowed = getSoRSpecification().isDisallowedProperty(this.property);

        return !disallowed || o == null;
    }
}
