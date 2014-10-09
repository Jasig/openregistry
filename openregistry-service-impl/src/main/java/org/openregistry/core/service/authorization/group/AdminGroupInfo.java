package org.openregistry.core.service.authorization.group;

import org.openregistry.core.authorization.Group;

/**
 * Created by nah67 on 9/22/14.
 */
public class AdminGroupInfo {
    Group group;
    int adminLevel;
    boolean activeGroupAdmin;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public boolean isActiveGroupAdmin() {
        return activeGroupAdmin;
    }

    public void setActiveGroupAdmin(boolean activeGroupAdmin) {
        this.activeGroupAdmin = activeGroupAdmin;
    }
}
