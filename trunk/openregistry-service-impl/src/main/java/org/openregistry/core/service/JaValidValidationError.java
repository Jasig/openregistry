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

import org.openregistry.core.service.ValidationError;
import org.javalid.core.ValidationMessage;

/**
 * Converts a JaValid error to the domain specific error.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JaValidValidationError implements ValidationError {

    private final ValidationMessage validationMessage;

    //Default ctor used solely for testing
    public JaValidValidationError() {
        this(null);
    }

    public JaValidValidationError(final ValidationMessage validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getField() {
        return this.validationMessage.getPath();
    }

    public Object[] getArguments() {
        return this.validationMessage.getValues();
    }

    public String getCode() {
        return this.validationMessage.getMessage();
    }
}
