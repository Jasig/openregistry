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
public class DepartmentEditor extends PropertyEditorSupport {
    private String format;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    ReferenceRepository referenceRepository;

    public DepartmentEditor(ReferenceRepository referenceRepository){
        this.referenceRepository = referenceRepository;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAsText(){
        Department department = (Department) getValue();
        String name = " ";
        if (department != null)
            name = department.getName();
        return (name);
    }

    public void setAsText(String text) {
        setValue(null);
        if (text != null && StringUtils.hasText(text)){
            List departmentList =  referenceRepository.getDepartments();
            Iterator<Department> iterator = departmentList.iterator();
	        while ( iterator.hasNext() ){
	            Department department = iterator.next();
                if (department.getLocalCode().equals(text)){
                    setValue(department);
                }
	        }
        }
    }
}
