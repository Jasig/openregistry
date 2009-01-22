package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Department {

    Type getDepartmentType();

    String getLocalCode();

    String getName();

    Campus getCampus();

    Department getParentDepartment();
}
