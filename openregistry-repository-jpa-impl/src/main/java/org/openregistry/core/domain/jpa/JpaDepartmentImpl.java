package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Department;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Campus;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaDepartmentImpl implements Department {

    private JpaCampusImpl campus;

    private Type departmentType;

    private String localCode;

    private String name;

    private JpaDepartmentImpl department;

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
