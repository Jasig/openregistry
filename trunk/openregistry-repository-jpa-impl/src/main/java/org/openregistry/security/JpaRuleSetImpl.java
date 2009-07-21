package org.openregistry.security;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

/**
 * JPA-backed implementation of the {@link org.openregistry.security.RuleSet} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="ruleSet")
@Table(name="or_security_rule_set")
public class JpaRuleSetImpl implements RuleSet {

   @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "or_ruleset_seq")
    @SequenceGenerator(name="or_ruleset_seq",sequenceName="or_ruleset_seq",initialValue=1,allocationSize=50)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ruleset_id")
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

    public Set<Rule> getRules() {
        final Set<Rule> rules = new HashSet<Rule>();

        for (final Permission p : this.getPermissions()) {
            rules.add(new JpaRuleImpl(this.permissionType, this.value, this.systemOfRecord, p));
        }

        return rules;
    }
}
