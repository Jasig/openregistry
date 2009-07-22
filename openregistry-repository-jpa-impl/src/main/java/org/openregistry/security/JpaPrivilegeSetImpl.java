package org.openregistry.security;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

/**
 * JPA-backed implementation of the {@link PrivilegeSet} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="privilegeSet")
@Table(name="ctx_privilege_sets")
public final class JpaPrivilegeSetImpl implements PrivilegeSet {

   @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_privilege_sets_seq")
    @SequenceGenerator(name="ctx_privilege_sets_seq",sequenceName="ctx_privilege_sets_seq",initialValue=1,allocationSize=50)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "permissionset_id")
    private JpaPermissionSetImpl permissionSet;

    @ManyToOne(optional=false)
    @JoinColumn(name="sor_id")
    private JpaSystemOfRecordImpl systemOfRecord;

    @Column(name="permission_type")
    private PermissionType permissionType;

    @Column(name="value")
    private String value;

    public String getName() {
        return this.permissionSet.getName();
    }

    public Set<Permission> getPermissions() {
        return this.permissionSet.getPermissions();
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

    public Set<Privilege> getPrivileges() {
        final Set<Privilege> privileges = new HashSet<Privilege>();

        for (final Permission p : this.getPermissions()) {
            privileges.add(new JpaPrivilegeImpl(this.permissionType, this.value, this.systemOfRecord, p));
        }

        return privileges;
    }
}
