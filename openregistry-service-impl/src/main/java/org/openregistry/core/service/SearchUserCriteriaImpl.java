package org.openregistry.core.service;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 4/9/12
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchUserCriteriaImpl implements SearchUserCriteria {

    private String userName;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
