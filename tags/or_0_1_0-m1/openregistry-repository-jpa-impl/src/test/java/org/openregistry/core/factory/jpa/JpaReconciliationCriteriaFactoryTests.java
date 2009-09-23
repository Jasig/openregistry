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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class JpaReconciliationCriteriaFactoryTests {

    private JpaReconciliationCriteriaFactory jpaReconciliationCriteriaFactory;

    @Before
    public void setUp() {
        this.jpaReconciliationCriteriaFactory = new JpaReconciliationCriteriaFactory();
    }

    @Test
    public void returnProperInstance() {
        final ReconciliationCriteria reconciliationCriteria = this.jpaReconciliationCriteriaFactory.getObject();
        assertTrue("reconciliationCriteria must be of type JpaReconciliationCriteriaImpl", reconciliationCriteria instanceof JpaReconciliationCriteriaImpl);
    }
}
