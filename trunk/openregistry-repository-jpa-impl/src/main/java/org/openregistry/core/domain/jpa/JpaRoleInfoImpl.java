package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.OrganizationalUnit;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0
 */
@javax.persistence.Entity(name="roleInfo")
@Table(name="prd_roles")
@Audited
public class JpaRoleInfoImpl extends Entity implements RoleInfo {

    @Id
    @GeneratedValue(generator="prd_roles_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="prd_roles_seq",sequenceName="prd_roles_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="title",nullable = false, length = 100)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name="organizational_unit_id")
    private JpaOrganizationalUnitImpl organizationalUnit;

    @ManyToOne(optional = false)
    @JoinColumn(name="campus_id")
    private JpaCampusImpl campus;

    @ManyToOne(optional = false)
    @JoinColumn(name="affiliation_t")
    private JpaTypeImpl affiliationType;

    @Column(name="code",nullable = false,length = 3)
    private String code;

    @OneToMany(mappedBy = "roleInfo")
    private List<JpaRoleImpl> roles = new ArrayList<JpaRoleImpl>();

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return this.organizationalUnit;
    }

    public Campus getCampus() {
        return this.campus;
    }

    public Type getAffiliationType() {
        return this.affiliationType;
    }

    public String getCode() {
        return this.code;
    }

    public String getDisplayableName(){
        return getAffiliationType().getDescription()+ "/"+ getTitle();
    }

    public String toString(){
        return getId().toString();
    }
}
