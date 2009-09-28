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
package org.openregistry.core.factory.jpa;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;

/**
 * Constructs a new {@link org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl}.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component("reconciliationCriteriaFactory")
@Qualifier("reconciliationCriteria")
public final class JpaReconciliationCriteriaFactory implements ObjectFactory<ReconciliationCriteria> {

    public ReconciliationCriteria getObject() throws BeansException {
        return new JpaReconciliationCriteriaImpl();
    }
}
