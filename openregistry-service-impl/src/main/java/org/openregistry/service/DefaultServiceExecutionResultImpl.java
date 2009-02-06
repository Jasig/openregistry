package org.openregistry.service;

import org.springframework.stereotype.Service;
import org.openregistry.core.service.ServiceExecutionResult;
import org.javalid.core.ValidationMessage;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Nancy Mond
 * @since 1.0
 * TODO: Document me
 */
@Service
public class DefaultServiceExecutionResultImpl implements ServiceExecutionResult {

    private List<ValidationMessage> errorList = new ArrayList();

    public List getErrorList(){
        return errorList;
    }

    public void setErrorList(List list){
        errorList = list;    
    }

}
