package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.net.URL;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="url")
@Table(name="prc_urls")
@Audited
public class JpaUrlImpl extends Entity implements Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_urls_seq")
    @SequenceGenerator(name="prc_urls_seq",sequenceName="prc_urls_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="url",length=500,nullable = false)
    private URL url;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaRoleImpl role;

    public JpaUrlImpl() {
        // nothing to do
    }

    public JpaUrlImpl(final JpaRoleImpl role) {
        this.role = role;
    }

    public Type getType() {
        return this.type;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setType(final Type type) {
        if (!(type instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.type = (JpaTypeImpl) type;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }
}
