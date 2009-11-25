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
package org.openregistry.core.domain.sor;

/**
 * Updates the current SystemOfRecord in the ThreadLocal so that its available to lower layers.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class SystemOfRecordHolder {

    private static InheritableThreadLocal<SystemOfRecord> SYSTEM_OF_RECORD_THREAD_LOCAL = new InheritableThreadLocal<SystemOfRecord>();

    public static SystemOfRecord getCurrentSystemOfRecord() {
        return SYSTEM_OF_RECORD_THREAD_LOCAL.get();
    }

    public static void clearCurrentSystemOfRecord() {
        SYSTEM_OF_RECORD_THREAD_LOCAL.remove();
    }
}
