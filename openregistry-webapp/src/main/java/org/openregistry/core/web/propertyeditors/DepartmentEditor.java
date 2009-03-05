package org.openregistry.core.web.propertyeditors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Iterator;
import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 6, 2009
 * Time: 8:25:30 AM
 * To change this template use File | Settings | File Templates.
 */
public final class DepartmentEditor extends AbstractReferenceRepositoryPropertyEditor {

    public DepartmentEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        Department department = (Department) getValue();
        return department != null ? department.getName() : " ";
    }

    @Override
    public void setAsText(final String text) {
        if (StringUtils.hasText(text)){
            setValue(getReferenceRepository().getDepartmentById(new Long(text)));
        } else {
            setValue(null);
        }
    }
}
