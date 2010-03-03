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
package org.openregistry.core.domain;

import java.net.URL;
import java.io.Serializable;

/**
 * Represents a URL that a person may have associated with their role.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 * 
 */
public interface Url extends Serializable {

    // TODO this should not be exposed
    Long getId();
    
    /**
     * Defines the type of URL this is, i.e. personal, research, etc.
     * @return the type.  CANNOT be null.
     */
    Type getType();

    /**
     * The actual URL value.  This CANNOT be null.
     * @return the URL value.  CANNOT be null.
     */
    URL getUrl();

    /**
     * Sets the type of this URL.
     *
     * @param type the type of the URL.  Cannot be NULL.
     */
    void setType(Type type);

    /**
     * Sets the url value.
     * @param url the URL value.  CANNOT be null.
     */
    void setUrl(URL url);
}
