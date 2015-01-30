package org.openregistry.core.authorization;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface User extends Serializable {

    Long getId();
    String getUserName();
    String getPassword();
    String getDescription();
    boolean isEnabled();

    void setUserName(String userName);
    void setPassword(String password);
    void setDescription(String description);
    void setEnabled(boolean value);
    public void addUserGroup(UserGroup userGroup);
    public void removeUserGroup(UserGroup userGroup);
    public void removeAllGroups();
    public void setGroups(Set<UserGroup> groups);
    public Set<UserGroup> getGroups();


/*
    void addGroup(Group group);
    void removeGroup(Group group);
    public void removeAllGroups();
    List<Group> getGroups();
*/

}
