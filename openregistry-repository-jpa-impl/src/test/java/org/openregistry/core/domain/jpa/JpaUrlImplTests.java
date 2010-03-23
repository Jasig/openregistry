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

package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version $Revision$ $Date$
 * @since 0.01
 */
public final class JpaUrlImplTests {


    @Test
    public void checkSettersAndGetters() throws MalformedURLException {
        final Url url = new JpaUrlImpl();

        assertNull(url.getType());
        assertNull(url.getUrl());

        final URL testUrl = new URL("http://www.cnn.com");
        final Type type = new JpaTypeImpl();

        url.setUrl(testUrl);
        assertEquals(testUrl, url.getUrl());
        url.setType(type);
        assertEquals(type, url.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkJpaTypeCheck() {
        final Url url = new JpaUrlImpl();
        url.setType(new Type() {
            public Long getId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getDataType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getDescription() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
