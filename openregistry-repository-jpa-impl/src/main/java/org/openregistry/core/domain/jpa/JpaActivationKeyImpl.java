package org.openregistry.core.domain.jpa;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.ActivationKey;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;
import java.security.SecureRandom;

/**
 * Immutable implementation of the {@link org.openregistry.core.domain.ActivationKey} interface.
 * <p>
 * Note: its not TRULY immutable because of the JPA restrictions, but it exposes no setters to change values.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="activationKey")
@Table(name="kr_activation_keys")
@Audited
public final class JpaActivationKeyImpl implements ActivationKey {

    /** The array of printable characters to be used in our random string. */
    private static final char[] PRINTABLE_CHARACTERS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ2345679".toCharArray();

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int ID_LENGTH = 8;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end;

    @Column(name = "start_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start;

    @OneToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    public JpaActivationKeyImpl() {
        // only used by JPA
    }

    public JpaActivationKeyImpl(final JpaPersonImpl person, final  Date start, final Date end) {
        this.person = person;
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        final byte[] random = new byte[ID_LENGTH];
        secureRandom.nextBytes(random);
        this.id = convertBytesToString(random);
    }

    public JpaActivationKeyImpl(final JpaPersonImpl person, final Date end) {
        this(person, new Date(), end);
    }

    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }

        return new String(output);
    }

    public String getId() {
        return this.id;
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

    public int compareTo(final ActivationKey o) {
        Assert.notNull(o);

        return this.id.compareTo(o.getId());
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaActivationKeyImpl)) return false;

        JpaActivationKeyImpl that = (JpaActivationKeyImpl) o;

        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }
}
