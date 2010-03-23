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

import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.LockingException;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;
import java.util.Calendar;
import java.security.SecureRandom;

/**
 * Immutable implementation of the {@link org.openregistry.core.domain.ActivationKey} interface.
 * <p>
 * Note: its not TRULY immutable because of the JPA restrictions, but it exposes no setters to change values.
 * <p>
 * Due to JPA limitations, providers may leave an embedded copy of this around, which is why JpaPersonImpl MUST check
 * isInitialized.  If its false, it MUST NOT return the activation key.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Embeddable
public class JpaActivationKeyImpl implements ActivationKey {

    /** The array of printable characters to be used in our random string. */
    private static final char[] PRINTABLE_CHARACTERS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ2345679".toCharArray();

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int ID_LENGTH = 8;

    private static final int TWENTY_MINUTES_AS_MILLISECONDS = 20 * 60 * 1000;

    @Column(name = "activation_key")
    private String value;

    @Column(name = "act_key_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(name = "act_key_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(name="act_key_lock")
    private String lock;

    @Column(name="act_key_lock_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockExpirationDate;

    public JpaActivationKeyImpl() {
        this(null, null);
    }

    public JpaActivationKeyImpl(final Date endDate) {
        this(new Date(), endDate);
    }

    public JpaActivationKeyImpl(final Date startDate, final Date endDate) {
        Assert.isTrue((startDate != null && endDate != null) || (startDate == null && endDate == null), "Both start and end date must be specified, or they must both be null to use the default value.");

        if (startDate == null && endDate == null) {
            final Calendar calendar = Calendar.getInstance();
            final Date localStartDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            final Date localEndDate = calendar.getTime();

            this.start = localStartDate;
            this.end = localEndDate;
        } else {
            Assert.isTrue(endDate.compareTo(startDate) > 0, "The End Date MUST be after the Start Date.");
            this.start = new Date(startDate.getTime());
            this.end = new Date(endDate.getTime());
        }
        this.value = constructNewValue();        
    }

    private String constructNewValue() {
        final byte[] random = new byte[ID_LENGTH];
        secureRandom.nextBytes(random);
        return convertBytesToString(random);
    }

    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }

        return new String(output);
    }

    public String asString() {
        return this.value;
    }

    public boolean isNotYetValid() {
       return this.start.compareTo(new Date()) > 0;
    }

    public boolean isExpired() {
        return this.end.compareTo(new Date()) < 0;
    }

    public boolean isValid() {
        return !isNotYetValid() && !isExpired();
    }

    public Date getStart() {
        return new Date(this.start.getTime());
    }

    public Date getEnd() {
        return new Date(this.end.getTime());
    }

    public boolean isInitialized() {
        return this.value != null;
    }

    public void removeKeyValues(){
        this.value = null;
        this.start = null;
        this.end = null;
        this.lock = null;
        this.lockExpirationDate = null;
    }

    public int compareTo(final ActivationKey o) {
        Assert.notNull(o);

        return this.value.compareTo(o.asString());
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaActivationKeyImpl)) return false;

        JpaActivationKeyImpl that = (JpaActivationKeyImpl) o;

        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;

        return true;
    }

    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result;
        return result;
    }

    public synchronized void lock(final String lock) throws LockingException {
        if (this.lock == null) {
            this.lock = lock;
            this.lockExpirationDate = new Date(System.currentTimeMillis() + TWENTY_MINUTES_AS_MILLISECONDS);
            return;
        }

        if (this.lock.equals(lock)) {
            this.lockExpirationDate = new Date(System.currentTimeMillis() + TWENTY_MINUTES_AS_MILLISECONDS);
            return;
        }

        if (System.currentTimeMillis() > this.lockExpirationDate.getTime()) {
            this.lock = lock;
            this.lockExpirationDate = new Date(System.currentTimeMillis() + TWENTY_MINUTES_AS_MILLISECONDS);
            return;
        }

        throw new LockingException("Someone else currently holds the lock.");
    }

    public boolean hasLock(final String lock) {
        return this.lock != null && this.lock.equals(lock) && (System.currentTimeMillis() <= this.lockExpirationDate.getTime());
    }
}
