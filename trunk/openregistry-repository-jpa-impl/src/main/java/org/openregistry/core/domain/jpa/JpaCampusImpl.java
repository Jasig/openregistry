package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="campus")
@Table(name="prd_campuses")
@Audited
public class JpaCampusImpl extends Entity implements Campus {

    @Id()
    @Column(name="id")
    @GeneratedValue(generator="prd_campuses_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="prd_campuses_seq",sequenceName="prd_campuses_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="code", length=2, nullable = false)
    private String code;

    @Column(name="name", length=100, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="campus")
    private List<JpaRoleInfoImpl> roleInfos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campus")
    private List<JpaOrganizationalUnitImpl> organizationalUnits;

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
