package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="type")
@Table(name="ctx_data_types")
public class JpaTypeImpl extends Entity implements Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_type_seq")
    @SequenceGenerator(name="prs_type_seq",sequenceName="prs_type_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="data_type", nullable = false, length =100)
    private String dataType;

    @Column(name="description",nullable = false, length=100)
    private String description;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="type")
    private List<JpaAddressImpl> addresses;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="departmentType")
    private List<JpaDepartmentImpl> departments;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "type")
    private List<JpaEmailAddressImpl> emailAddresses;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="reason")
    private List<JpaLeaveImpl> leaves;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="phoneType")
    private List<JpaPhoneImpl> phoneTypes;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="addressType")
    private List<JpaPhoneImpl> phoneAddressTypes;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="personStatus")
    private List<JpaRoleImpl> rolePersonStatuses;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="affiliationType")
    private List<JpaRoleInfoImpl> roleInfoAffiliationTypes;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="type")
    private List<JpaUrlImpl> urls;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="terminationReason")
    private List<JpaRoleImpl> roleTerminationTypes;

    
    public Long getId() {
        return this.id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getDescription() {
        return this.description;
    }

    
}
