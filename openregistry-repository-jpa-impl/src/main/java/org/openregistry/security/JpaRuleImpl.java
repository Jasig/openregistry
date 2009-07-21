package org.openregistry.security;

import javax.persistence.*;

/**
 * Implementation of the {@link org.openregistry.security.Rule}.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "rule")
@Table(name = "or_rules")
public final class JpaRuleImpl implements Rule {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "or_rules_seq")
    @SequenceGenerator(name="or_rules_seq",sequenceName="or_rules_seq",initialValue=1,allocationSize=50)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id")
    private JpaPermissionImpl permission;

    @ManyToOne(optional=false)
    @JoinColumn(name="sor_id")
    private JpaSystemOfRecordImpl systemOfRecord;

    @Column(name="permission_type")
    private PermissionType permissionType;

    @Column(name="value")
    private String value;

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
    public int compareTo(final Rule rule) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
