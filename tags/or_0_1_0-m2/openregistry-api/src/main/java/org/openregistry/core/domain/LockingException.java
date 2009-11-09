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
package org.openregistry.core.domain;

/**
 * Exception thrown when a lock can't be acquired for an activation key or if an action can't be performed because you're
 * not the one holding the lock.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class LockingException extends RuntimeException {

    public LockingException() {
    }

    public LockingException(final String s) {
        super(s);
    }

    public LockingException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public LockingException(final Throwable throwable) {
        super(throwable);
    }
}
