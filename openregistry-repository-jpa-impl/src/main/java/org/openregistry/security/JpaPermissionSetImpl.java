package org.openregistry.security;

import javax.persistence.*;
import java.util.Set;

/**
 * JPA-backed implementation of the {@link org.openregistry.security.PermissionSet} interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name="permissionSet")
@Table(name="ctx_permission_sets")
public class JpaPermissionSetImpl implements PermissionSet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctx_permission_sets_seq")
    @SequenceGenerator(name="ctx_permission_sets_seq",sequenceName="ctx_permission_sets_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="name", length = 100, nullable = false,unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = JpaPermissionImpl.class)
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "permissionSet",fetch = FetchType.LAZY,targetEntity = JpaPrivilegeSetImpl.class)
    private Set<PrivilegeSet> privilegeSets;

    public String getName() {
        return this.name;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }
}
