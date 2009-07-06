package org.openregistry.security;

import javax.persistence.*;
import java.util.List;

/**
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Entity(name = "systemOfRecord")
@Table(name="or_sors")
// TODO mapping
public final class JpaSystemOfRecordImpl implements SystemOfRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "or_sors_seq")
    @SequenceGenerator(name="or_sors_seq",sequenceName="or_sors_seq",initialValue=1,allocationSize=50)
    private long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="systemOfRecord", fetch = FetchType.LAZY, targetEntity = JpaPermissionImpl.class)
    private List<JpaPermissionImpl> permissions;

    public String getName() {
        return this.name;
    }
}
