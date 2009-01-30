package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Department;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="department")
@Table(name="prs_departments")
public class JpaDepartmentImpl extends Entity implements Department {

    @Id
    @Column(name="department_id")
    @GeneratedValue(generator="prd_department_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="prd_department_seq",sequenceName="prd_department_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="campus_id")
    private JpaCampusImpl campus;

    @ManyToOne(optional = false)
    @JoinColumn(name="department_t")    
    private JpaTypeImpl departmentType;

    @Column(name="code",length = 100, nullable = true)
    private String localCode;

    @Column(name="name", length=100, nullable = false)
    private String name;

    @ManyToOne(optional=true)
    @JoinColumn(name="parent_department_id")
    private JpaDepartmentImpl department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<JpaRoleInfoImpl> roleInfos = new ArrayList<JpaRoleInfoImpl>();

    protected Long getId() {
        return this.id;
    }

    public Type getDepartmentType() {
        return this.departmentType;
    }

    public String getLocalCode() {
        return this.localCode;
    }

    public String getName() {
        return this.name;
    }

    public Campus getCampus() {
        return this.campus;
    }

    public Department getParentDepartment() {
        return this.department;
    }
}
