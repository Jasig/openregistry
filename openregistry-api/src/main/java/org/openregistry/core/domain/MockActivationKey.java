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

import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.LockingException;

import java.util.Date;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class MockActivationKey implements ActivationKey {

    private final String value;

    private final Date start;

    private final Date end;

    private String lock;

    public MockActivationKey(final String value, final Date start, final Date end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public String asString() {
        return this.value;
    }

    public boolean isNotYetValid() {
       return (this.start == null || this.start.compareTo(new Date()) > 0);
    }

    public boolean isExpired() {
        return (this.end == null || this.end.compareTo(new Date()) < 0);
    }

    public boolean isValid() {
        return !this.isNotYetValid() && !this.isExpired();
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public int compareTo(final ActivationKey activationKey) {
        return this.value.compareTo(activationKey.asString());
    }

    public void lock(final String lock) throws LockingException {
        if (this.lock == null) {
            this.lock = lock;
            return;
        }

        if (!this.lock.equals(lock)) {
            throw new LockingException();
        }
    }

    public boolean hasLock(final String lock) {
        return this.lock != null && this.lock.equals(lock);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockActivationKey that = (MockActivationKey) o;

        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (lock != null ? !lock.equals(that.lock) : that.lock != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        return result;
    }
}
