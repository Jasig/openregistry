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

import org.springframework.beans.factory.FactoryBean;
import org.openregistry.core.service.IdentifierChangeService;

/**
 * <code>FactoryBean</code> for producing <i>null</i> references of <code>IdentifierChangeService</code>
 *
 * This is used solely for testing.
 *
 * @author Dmitriy Kopylenko
 */
public class NullIdentifierChangeServiceFactoryBean implements FactoryBean<IdentifierChangeService> {

    public IdentifierChangeService getObject() throws Exception {
        return null;
    }

    public Class<? extends IdentifierChangeService> getObjectType() {
        return IdentifierChangeService.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
