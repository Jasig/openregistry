package org.openregistry.core.authorization;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Group extends Serializable{
    Long getId();
    String getGroupName();
    String getDescription();
    boolean isEnabled();

    void setGroupName(String groupName);
    void setDescription(String description);
    void setEnabled(boolean value);

    Authority addAuthority(Authority authority);

    Set<Authority> getGroupAuthorities();

    public void removeAllGroups();
}
