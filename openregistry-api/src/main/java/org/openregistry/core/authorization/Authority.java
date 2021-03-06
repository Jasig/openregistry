package org.openregistry.core.authorization;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Authority extends Serializable{
    Long getId();
    String getAuthorityName();
    String getDescription();

    void setAuthorityName(String authorityName);
    void setDescription(String description);

    Group addGroup(Group group);
    void removeGroup(Group group);

    void removeAllGroups();
    public List<Group> getGroups();
}
