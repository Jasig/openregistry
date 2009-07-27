package org.openregistry.security;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the {@link org.openregistry.security.Permission} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="permission")
@Table(name="ctx_permissions")
public final class JpaPermissionImpl implements Permission {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_permissions_seq")
    @SequenceGenerator(name="ctx_permissions_seq",sequenceName="ctx_permissions_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="resource")
    private String resource;

    @Column(name="description")
    private String description;

    @Column(name="p_create")
    private boolean create;

    @Column(name="p_read")
    private boolean read;

    @Column(name="p_update")
    private boolean update;

    @Column(name="p_delete")
    private boolean delete;

    @OneToMany(mappedBy = "permission",fetch = FetchType.LAZY)
    private List<JpaPrivilegeImpl> rules;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY,targetEntity = JpaPermissionSetImpl.class)
    private Set<PermissionSet> permissionSets;

    public String getResource() {
        return this.resource;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canCreate() {
        return this.create;
    }

    public boolean canRead() {
        return this.read;
    }

    public boolean canUpdate() {
        return this.update;
    }

    public boolean canDelete() {
        return this.delete;
    }
}
