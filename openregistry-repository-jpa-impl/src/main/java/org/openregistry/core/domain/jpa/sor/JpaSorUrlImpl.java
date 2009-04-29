package org.openregistry.core.domain.jpa.sor;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;

import javax.persistence.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 2:45:38 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorUrl")
@Table(name="prs_urls")
@Audited
public class JpaSorUrlImpl extends Entity implements Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_urls_seq")
    @SequenceGenerator(name="prs_urls_seq",sequenceName="prs_urls_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="url",length=500,nullable = false)
    private URL url;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    public JpaSorUrlImpl() {
        // nothing to do
    }

    public JpaSorUrlImpl(final JpaSorRoleImpl sorRole) {
        this.sorRole = sorRole;
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

    public void setURL(final URL url) {
        this.url = url;
    }

    protected Long getId() {
        return this.id;
    }
}
