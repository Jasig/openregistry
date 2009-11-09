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
package org.openregistry.integration;

/**
 * A component responsible for firing Open Registry messages related to an identifier change events.
 *
 * Typical implementations could send such event messages to a variety of destinations.
 *
 * @author Dmitriy kopylenko
 * @since 1.0
 */
public interface IdentifierChangeEventNotificationService {
 
    /**
     * Create an event message and send it to a subsystem(s) that understands how to deal with such messages. The event message format
     * is implementation-dependent.
     *
     * Implementations are assumed to be sending messages asynchronously.
     * 
     * @param internalIdentifierType
     * @param internalIdentifierValue
     * @param changedIdentifierType
     * @param changedIdentifierValue
     */
    void createAndSendEventMessageFor(String internalIdentifierType, String internalIdentifierValue, String changedIdentifierType, String changedIdentifierValue);
}
