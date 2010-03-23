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

package org.openregistry.core.domain.annotation;

import org.openregistry.core.domain.validation.RequiredFieldConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

/**
 * Constraint that determines whether a field is not allowed for a specific SoR.
 * <p>
 * Similar to the Null check.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Retention(RUNTIME)
@Target({FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Constraint(validatedBy = RequiredFieldConstraintValidator.class)
public @interface NotAllowed {

    String property();

    String message() default "{org.openregistry.core.domain.annotation.NotAllowed.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
