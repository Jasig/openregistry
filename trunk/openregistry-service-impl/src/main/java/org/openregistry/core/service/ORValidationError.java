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

import java.util.Collections;

/**
 * OR Validation errors.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class ORValidationError implements ValidationError {

    private String field;
    private Object[] args;
    private String code;

    public ORValidationError(String field, Object[] args, String code) {
        this.field = field;
        this.code = code;
        this.args = args;
    }

    public String getField() {
        return this.field;
    }

    public Object[] getArguments() {
        return this.args;
    }

    public String getCode() {
        return this.code;
    }
}