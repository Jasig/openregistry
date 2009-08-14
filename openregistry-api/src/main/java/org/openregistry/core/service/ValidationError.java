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

import java.io.Serializable;

/**
 * Represents an error from when the system attempted to validate the object.
 * <p>
 * Exists so we're not tied to the JaValid or Spring APIs.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ValidationError extends Serializable {

    /**
     * The field from which there was an error.  If its null, that means global error message.
     *
     * @return the field, or null, if its a global error message.
     */
    String getField();

    /**
     * The list of arguments to supply to the message for rendering purposes.
     * @return the list of arguments to apply to the rendered message.
     */
    Object[] getArguments();

    /**
     * The code description of the message.
     * @return the code of the message.  CANNOT be null.
     */
    String getCode();
}
