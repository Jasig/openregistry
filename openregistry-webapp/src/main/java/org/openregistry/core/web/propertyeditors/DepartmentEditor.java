package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Department;

/**
 * Converts the numerical identifier for a department into the appropriate object.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class DepartmentEditor extends AbstractReferenceRepositoryPropertyEditor {

    public DepartmentEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        Department department = (Department) getValue();
        return department != null ? department.getName() : " ";
    }

    protected void setAsTextInternal(final String s) {
        setValue(getReferenceRepository().getDepartmentById(new Long(s)));
    }
}
