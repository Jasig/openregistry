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

package org.openregistry.core.domain.sor;

/**
 * Updates the current SystemOfRecord in the ThreadLocal so that its available to lower layers.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class SystemOfRecordHolder {

    private static InheritableThreadLocal<SoRSpecification> SYSTEM_OF_RECORD_THREAD_LOCAL = new InheritableThreadLocal<SoRSpecification>();

    public static SoRSpecification getCurrentSystemOfRecord() {
        return SYSTEM_OF_RECORD_THREAD_LOCAL.get();
    }

    public static void clearCurrentSystemOfRecord() {
        SYSTEM_OF_RECORD_THREAD_LOCAL.remove();
    }

    public static void setCurrentSystemOfRecord(final SoRSpecification systemOfRecord) {
        SYSTEM_OF_RECORD_THREAD_LOCAL.set(systemOfRecord);
    }
}
