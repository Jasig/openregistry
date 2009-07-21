package org.openregistry.security;

import javax.persistence.*;
import java.util.List;

/**
 * Represents the {@link org.openregistry.security.SystemOfRecord} via JPA.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "systemOfRecord")
@Table(name="or_sors")
public final class JpaSystemOfRecordImpl implements SystemOfRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "or_sors_seq")
    @SequenceGenerator(name="or_sors_seq",sequenceName="or_sors_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="systemOfRecord", fetch = FetchType.LAZY, targetEntity = JpaRuleImpl.class)
    private List<JpaRuleImpl> permissions;

    public String getName() {
        return this.name;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaSystemOfRecordImpl that = (JpaSystemOfRecordImpl) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
