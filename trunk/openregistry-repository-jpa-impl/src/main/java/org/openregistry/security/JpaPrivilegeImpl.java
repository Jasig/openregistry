package org.openregistry.security;

import javax.persistence.*;

/**
 * Implementation of the {@link Privilege}.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "privilege")
@Table(name = "ctx_privileges")
public final class JpaPrivilegeImpl implements Privilege {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_privileges_seq")
    @SequenceGenerator(name="ctx_privileges_seq",sequenceName="ctx_privileges_seq",initialValue=1,allocationSize=50)
    private long id;

    @ManyToOne(optional = false,targetEntity = JpaPermissionImpl.class)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne(optional=false,targetEntity = JpaSystemOfRecordImpl.class)
    @JoinColumn(name="sor_id")
    private SystemOfRecord systemOfRecord;

    @Column(name="permission_type")
    private PermissionType permissionType;

    @Column(name="value")
    private String value;

    public JpaPrivilegeImpl() {
        // nothing to do for JPA
    }

    public JpaPrivilegeImpl(final PermissionType permissionType, final String value, final SystemOfRecord systemOfRecord, final Permission permission) {
        this.permissionType = permissionType;
        this.value = value;
        this.systemOfRecord = systemOfRecord;
        this.permission = permission;
    }

    public String getUser() {
        if (getPermissionType() == PermissionType.USER) {
            return this.value;
        }

        return null;
    }

    public String getExpression() {
        if (getPermissionType() == PermissionType.EXPRESSION) {
            return this.value;
        }
        return null;
    }

    public PermissionType getPermissionType() {
        return this.permissionType;
    }

    public SystemOfRecord getSystemOfRecord() {
        return this.systemOfRecord;
    }

    public String getResource() {
        return this.permission.getResource();
    }

    public String getDescription() {
        return this.permission.getDescription();
    }

    public boolean canCreate() {
        return this.permission.canCreate();
    }

    public boolean canRead() {
        return this.permission.canRead();
    }

    public boolean canUpdate() {
        return this.permission.canUpdate();
    }

    public boolean canDelete() {
        return this.permission.canDelete();
    }

    // TODO implement
    public int compareTo(final Privilege privilege) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
