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
package org.openregistry.core.aspect;

import junit.framework.TestCase;
import org.junit.Test;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonImpl;
import org.openregistry.core.domain.sor.SorName;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Feb 23, 2010
 * Time: 3:55:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CapitalizationTests extends TestCase {

    @Test
    public void testFirstNameCapitalization() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setGiven("given");

        assertEquals("Given", name.getGiven());
    }

    @Test
    public void testFirstNameCapitalizationNull() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name = person.addName();
        name.setGiven(null);

        assertNull(name.getGiven());
    }

    @Test
    public void testCapitalization() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name = person.addName();
        name.setSuffix("jr.");

        assertEquals("Jr.", name.getSuffix());
    }
}
