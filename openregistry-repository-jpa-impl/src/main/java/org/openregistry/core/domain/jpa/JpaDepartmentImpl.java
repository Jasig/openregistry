package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Department;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;


/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="department")
@Table(name="prs_departments")
// TODO
public class JpaDepartmentImpl extends Entity implements Department {

    @Id
    @Column(name="department_id")
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="campus_id")
    private JpaCampusImpl campus;

    // TODO map
    private JpaTypeImpl departmentType;

    @Column(name="code",length = 100, nullable = true)
    private String localCode;

    @Column(name="name", length=100, nullable = false)
    private String name;

    @ManyToOne(optional=true)
    @JoinColumn(name="parent_department_id")
    private JpaDepartmentImpl department;

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
