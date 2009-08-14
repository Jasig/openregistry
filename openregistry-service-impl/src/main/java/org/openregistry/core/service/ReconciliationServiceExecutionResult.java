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

import org.openregistry.core.domain.Person;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;

import java.util.List;

/**
 * Implementation of a <code>ServiceExecutionResult </code>
 *
 * @since 1.0
 * @see org.openregistry.core.service.ServiceExecutionResult
 *
 */
public class ReconciliationServiceExecutionResult extends GeneralServiceExecutionResult {

    private final ReconciliationResult reconciliationResult;

    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject) {
        this(serviceName, targetObject, null, null);
    }

    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors) {
        this(serviceName, targetObject, validationErrors, null);
    }
    
    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject, final ReconciliationResult reconciliationResult) {
        this(serviceName, targetObject, null, reconciliationResult);
    }

    protected ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject,final List<ValidationError> validationErrors, final ReconciliationResult reconciliationResult) {
        super(serviceName, targetObject, validationErrors);
        this.reconciliationResult = reconciliationResult;
        setSucceeded(getValidationErrors().isEmpty() && targetObject instanceof Person);
    }

    public ReconciliationResult getReconciliationResult() {
        return this.reconciliationResult;
    }

}
