package org.openregistry.core.authorization.jpa;

import org.hibernate.envers.Audited;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.authorization.UserGroup;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nah67 on 8/22/14.
 */
@javax.persistence.Entity(name="auth_user_group")
@Table(name="auth_user_group")
@Audited
@org.hibernate.annotations.Table(appliesTo = "auth_user_group")
@IdClass(UserGroupId.class)

public class JpaUserGroupImpl implements UserGroup {

//    //@Embeddable
//    public static class Id implements Serializable{
//        //@Column(name="user_id")
//        private Long user_id;
//
//        //@Column(name="group_id")
//        private Long group_id;
//
//        public Id(){
//
//        }
//
//        public Id(Long userId, Long groupId){
//            this.user_id = userId;
//            this.group_id = groupId;
//        }
//
//        public boolean equals(Object o){
//            if(o != null && o instanceof Id){
//                Id that = (Id) o;
//                return this.user_id.equals(that.user_id) && this.group_id.equals(that.group_id);
//            }else{
//                return false;
//            }
//        }
//
//        public int hashCode(){
//            return user_id.hashCode()+group_id.hashCode();
//        }
//    }

//    @EmbeddedId
//    private Id id = new Id();
    @Id
    private Long user_id;

    @Id
    private Long group_id;

    @ManyToOne(targetEntity = JpaUserImpl.class)
    @JoinColumn(name = "user_id", insertable=false, updatable=false, referencedColumnName = "id")
    //private Long userId;
    private User user;

    @ManyToOne(targetEntity = JpaGroupImpl.class)
    @JoinColumn(name = "group_id", insertable=false, updatable=false, referencedColumnName = "id")
    //private Long groupId;
    private Group group;

    @Column(name = "perm_admin")
    private Long permanentAdmin;

    @Column(name = "delegated_admin")
    private Long delegatedAdmin;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public JpaUserGroupImpl(){
        //default constructor is empty
    }

    public JpaUserGroupImpl(User user, Group group, Long permanentAdmin, Long delegatedAdmin, Date startDate, Date endDate){
        this.permanentAdmin = permanentAdmin;
        this.delegatedAdmin = delegatedAdmin;
        this.startDate = startDate;
        this.endDate = endDate;

        //set the Ids
        this.user_id = user.getId();
        this.group_id = group.getId();
        this.user = user;
        this.group = group;

        //referential integrity should be maintained
        //user.getGroups().add(this);
        //group.getUsers().add(this);

        user.addUserGroup(this);
        group.addUserGroup(this);
    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Long groupId) {
//        this.groupId = groupId;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getPermanentAdmin() {
        return permanentAdmin;
    }

    public void setPermanentAdmin(Long permanentAdmin) {
        this.permanentAdmin = permanentAdmin;
    }

    public Long getDelegatedAdmin() {
        return delegatedAdmin;
    }

    public void setDelegatedAdmin(Long delegatedAdmin) {
        this.delegatedAdmin = delegatedAdmin;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
