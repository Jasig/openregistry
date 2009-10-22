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
package org.openregistry.core.service;

import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Date;
import java.util.Collections;

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

    private final List<ValidationError> validationErrors;

    private boolean succeeded;  

    public GeneralServiceExecutionResult(final T targetObject) {
        this(targetObject, null);
    }

    public GeneralServiceExecutionResult(final List<ValidationError> validationErrors) {
        this(null, validationErrors);
    }

    public GeneralServiceExecutionResult(final T targetObject, final List<ValidationError> validationErrors) {
        this.targetObject = targetObject;
        this.validationErrors = validationErrors != null ? Collections.unmodifiableList(validationErrors) : Collections.<ValidationError>emptyList();
        this.succeeded = this.validationErrors.isEmpty();
    }

    public Date getExecutionDate() {
        return new Date(this.serviceExecutionDate.getTime());
    }

    public T getTargetObject() {
        return this.targetObject;
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    public boolean succeeded() {
        return this.succeeded;
    }
}