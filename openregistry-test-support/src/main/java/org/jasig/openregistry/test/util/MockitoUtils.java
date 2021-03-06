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

package org.jasig.openregistry.test.util;

import org.mockito.Mockito;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public final class MockitoUtils {


    private MockitoUtils() {
    }

    public static Set<ConstraintViolation> oneMinimalisticMockConstraintViolation() {

        final ConstraintViolation mockConstraintViolation = Mockito.mock(ConstraintViolation.class, Mockito.RETURNS_SMART_NULLS);
        final Path mockPath = Mockito.mock(Path.class);
        Mockito.when(mockPath.toString()).thenReturn("test.property.path");
        Mockito.when(mockConstraintViolation.getPropertyPath()).thenReturn(mockPath);
        Mockito.when(mockConstraintViolation.getMessage()).thenReturn("mockConstraintViolation: error message");
        Mockito.when(mockConstraintViolation.getInvalidValue()).thenReturn("mockConstraintViolation: invalid value");
        Set<ConstraintViolation> setWithErrors = new HashSet<ConstraintViolation>();
        setWithErrors.add(mockConstraintViolation);
        return setWithErrors;
    }
}
