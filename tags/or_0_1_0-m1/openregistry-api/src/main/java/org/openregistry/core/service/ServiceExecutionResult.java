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

import org.openregistry.core.service.reconciliation.ReconciliationResult;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * A container encapsulating results of any number of different Open Registry public service API invocations.
 * Such results are validation errors if any, time stamp of the service invocation, service name being executed.
 *
 * @since 1.0
 */
public interface ServiceExecutionResult<T> extends Serializable {

    /**
     * Get an instant in time when a particular service, represented by this result, has been executed
     * @return Date representing a service execution date and time
     */
    Date getExecutionDate();

    /**
     * Get a symbolic name for an executed service represented by this result
     * @return a string token representing a symbolic name of a service that has been executed
     */
    String getServiceName();

    /**
     * Determines whether the action succeeded or not.  If it did not, then either validation errors or reconciliation
     * object should be filled in.
     * @return true if it succeeded, false otherwise.
     */
    boolean succeeded();

    /**
     * The original object we were working with, or the final object if we're successful.
     *
     * @return the original object.  This CANNOT be null.
     */
    T getTargetObject();

    /**
     * The list of field or object level validations.
     *
     * @return the list of validations.  CANNOT be null.  CAN be empty.
     */
    List<ValidationError> getValidationErrors();
}
