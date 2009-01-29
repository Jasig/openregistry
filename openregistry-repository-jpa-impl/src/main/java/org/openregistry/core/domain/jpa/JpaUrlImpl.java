package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.net.URL;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="url")
@Table(name="prc_urls")
public class JpaUrlImpl extends Entity implements Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_urls_seq")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="url",length=500,nullable = false)
    private URL url;

    @ManyToOne(optional=false)
    @JoinColumn(name="prc_role_record_id")
    private JpaRoleImpl role;

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

    public void setURL(final URL url) {
        this.url = url;
    }

    protected Long getId() {
        return this.id;
    }
}
