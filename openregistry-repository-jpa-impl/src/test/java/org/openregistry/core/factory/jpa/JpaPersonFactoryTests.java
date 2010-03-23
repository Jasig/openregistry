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

package org.openregistry.core.factory.jpa;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class JpaPersonFactoryTests {

    private JpaPersonFactory jpaPersonFactory;

    @Before
    public void setUp() {
        this.jpaPersonFactory = new JpaPersonFactory();
    }

    @Test
    public void returnedInstance() {
        final Person person = this.jpaPersonFactory.getObject();
        assertTrue("person must be instanceof JpaPersonImpl", person instanceof JpaPersonImpl);
    }
}
