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
package org.openregistry.core.repository;

import org.springframework.beans.factory.FactoryBean;

/**
 * <code>FactoryBean</code> for producing <i>null</i> references of <code>ReferenceRepository</code>
 * <p/>
 * This is used solely for testing.
 *
 * @author Dmitriy Kopylenko
 */
public class NullReferenceRepositoryFactoryBean implements FactoryBean<ReferenceRepository> {

    public ReferenceRepository getObject() throws Exception {
        return null;
    }

    public Class<? extends ReferenceRepository> getObjectType() {
        return ReferenceRepository.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
