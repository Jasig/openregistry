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
package org.openregistry.core.service;

import org.openregistry.core.domain.ActivationKey;

import java.util.Date;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class MockActivationKey  implements ActivationKey {

    private final String value;

    private final Date start;

    private final Date end;

    public MockActivationKey(final String value, final Date start, final Date end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isNotYetValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isExpired() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
        return this.value.compareTo(activationKey.getValue());
    }
}
