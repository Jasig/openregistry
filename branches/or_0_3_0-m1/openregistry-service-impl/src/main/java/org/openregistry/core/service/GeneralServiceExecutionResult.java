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

package org.openregistry.core.service;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Collections;
import java.util.Set;

/**
 * Implementation of a <code>ServiceExecutionResult </code>
 *
 * @since 1.0
 * @see org.openregistry.core.service.ServiceExecutionResult
 *
 */
public class GeneralServiceExecutionResult<T> implements ServiceExecutionResult<T> {

    private final Date serviceExecutionDate = new Date();

    private final T targetObject;

    private final Set<ConstraintViolation> validationErrors;

    private boolean succeeded;  

    public GeneralServiceExecutionResult(final T targetObject) {
        this(targetObject, null);
    }

    public GeneralServiceExecutionResult(final Set<ConstraintViolation> validationErrors) {
        this(null, validationErrors);
    }

    public GeneralServiceExecutionResult(final T targetObject, final Set<ConstraintViolation> validationErrors) {
        this.targetObject = targetObject;
        this.validationErrors = validationErrors != null ? Collections.unmodifiableSet(validationErrors) : Collections.<ConstraintViolation>emptySet();
        this.succeeded = this.validationErrors.isEmpty();
    }

    public Date getExecutionDate() {
        return new Date(this.serviceExecutionDate.getTime());
    }

    public T getTargetObject() {
        return this.targetObject;
    }

    public Set<ConstraintViolation> getValidationErrors() {
        return this.validationErrors;
    }

    public boolean succeeded() {
        return this.succeeded;
    }
}