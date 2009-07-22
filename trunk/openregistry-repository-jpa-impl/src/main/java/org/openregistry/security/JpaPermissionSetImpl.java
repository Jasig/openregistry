package org.openregistry.security;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Jul 21, 2009
 * Time: 11:38:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name="permissionSet")
@Table(name="or_security_permission_set")
public class JpaPermissionSetImpl implements PermissionSet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "or_permission_set_seq")
    @SequenceGenerator(name="or_permission_set_seq",sequenceName="or_permission_set_seq",initialValue=1,allocationSize=50)
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
