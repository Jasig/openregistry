package org.openregistry.service;

import org.springframework.stereotype.Service;
import org.openregistry.core.service.ServiceExecutionResult;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 2, 2009
 * Time: 11:46:48 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultServiceExecutionResultImpl implements ServiceExecutionResult {

    private List errorList = new ArrayList();

    public List getErrorList(){
        return errorList;
    }

    public void setErrorList(List list){
        errorList = list;    
    }

}
