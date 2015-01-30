package org.openregistry.core.authorization.jpa;

import java.io.Serializable;

/**
 * Created by nah67 on 9/4/14.
 */
public class UserGroupId implements Serializable {
    //@Column(name="user_id")
    private Long user_id;

    //@Column(name="group_id")
    private Long group_id;

    public UserGroupId(){

    }

    public UserGroupId(Long userId, Long groupId){
        this.user_id = userId;
        this.group_id = groupId;
    }

    public boolean equals(Object o){
        if(o != null && o instanceof UserGroupId){
            UserGroupId that = (UserGroupId) o;
            return this.user_id.equals(that.user_id) && this.group_id.equals(that.group_id);
        }else{
            return false;
        }
    }

    public int hashCode(){
        return user_id.hashCode()+group_id.hashCode();
    }
}
