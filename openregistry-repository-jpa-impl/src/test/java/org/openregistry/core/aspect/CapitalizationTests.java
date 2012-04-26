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

import junit.framework.TestCase;
import org.junit.Test;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonImpl;
import org.openregistry.core.domain.sor.SorName;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Feb 23, 2010
 * Time: 3:55:59 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"classpath:test-capitalization-context.xml"})
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
    public void testLastNameCapitalization() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("family");

        assertEquals("Family", name.getFamily());
    }
    
    @Test
    public void testLastNameCapitalizationWithSpace() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("family name");

        assertEquals("Family Name", name.getFamily());
    }
      //OR-286
     @Test
    public void testLastNameCapitalizationWithOutAnyDelima() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("MCGOVERN");
        assertEquals("McGovern", name.getFamily());

    }
    @Test
    public void testSpecialLastNameCapitalizationWithDash() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("MCMILLAN-MOORE");
        assertEquals("McMillan-Moore", name.getFamily());

    }
    @Test
    public void testSpecialLastNameCapitalizationWithSpace() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("MC INTYRE");
        assertEquals("Mc Intyre", name.getFamily());

    }
    
    @Test
    public void testLastNameCapitalizationWithDash() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name =  person.addName();
        name.setFamily("family-name");

        assertEquals("Family-Name", name.getFamily());
    }

    @Test
    public void testCapitalization() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final SorName name = person.addName();
        name.setSuffix("jr.");

        assertEquals("Jr.", name.getSuffix());
    }
}
