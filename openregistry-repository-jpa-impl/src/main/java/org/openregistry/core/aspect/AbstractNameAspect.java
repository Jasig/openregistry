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

package org.openregistry.core.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * An aspect that can be disabled via configuration.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 *
 */
public abstract class AbstractNameAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private boolean disable;

    private Map<String,String> customMapping = new HashMap<String,String>();

    protected final boolean isDisabled() {
        return this.disable;
    }

    protected final Map<String,String> getCustomMapping() {
        return this.customMapping;
    }

    public final void setDisabled(final boolean disabled) {
        logger.warn("Setting disabled status for this Aspect: " + disabled);
        this.disable = disabled;
    }

    public final void setCustomMapping(final Map<String,String> customMapping) {
        this.customMapping = customMapping;
    }
}
