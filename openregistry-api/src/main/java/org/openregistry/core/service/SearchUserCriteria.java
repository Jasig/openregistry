package org.openregistry.core.service;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 4/9/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchUserCriteria extends Serializable {
    public String getUserName();
    public void setUserName(String userName);
}
