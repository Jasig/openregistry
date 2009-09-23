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
package org.openregistry.core.domain.internal;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since 1.0
 */
public class EntityTests {

    private Long id = 1L;

    @Test
    public void equalsOK() {

        Entity e1 = new Entity() {
            protected Long getId() {
                return id;
            }
        };
        Entity e2 = new Entity() {
            protected Long getId() {
                return id;
            }
        };
        assertTrue(e1.equals(e2));
    }
}
