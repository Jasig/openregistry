package org.openregistry.core.authorization;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nah67 on 8/22/14.
 */
public interface UserGroup extends Serializable {
//    public Long getUserId();
//    public void setUserId(Long userId);
//    public Long getGroupId();
//    public void setGroupId(Long groupId);

    public User getUser();
    public void setUser(User user);
    public Group getGroup();
    public void setGroup(Group group);
    public Long getPermanentAdmin();
    public void setPermanentAdmin(Long permanentAdmin);
    public Long getDelegatedAdmin();
    public void setDelegatedAdmin(Long delegatedAdmin);
    public Date getStartDate();
    public void setStartDate(Date startDate);
    public Date getEndDate();
    public void setEndDate(Date endDate);
}
