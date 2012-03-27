package org.openregistry.core.authorization.jpa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.domain.internal.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */

@javax.persistence.Entity(name="auth_users")
@Table(name="auth_users")
@Audited
@org.hibernate.annotations.Table(appliesTo = "auth_users", indexes = {
        @Index(name = "AUTH_USERS_NAME_IDX", columnNames = "USER_NAME")
})

public class JpaUserImpl extends Entity implements User {

    protected static final Logger logger = LoggerFactory.getLogger(JpaUserImpl.class);

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auth_users_seq")
    @SequenceGenerator(name="auth_users_seq",sequenceName="auth_users_seq",initialValue=1,allocationSize=50)


    private Long id;

    @Column(name="USER_NAME",nullable=false)
    private String userName;

    @Column(name="PASSWORD",nullable=true)
    private String password;

    @Column(name="DESCRIPTION",nullable=true)
    private String description;

    @Column(name="IS_ENABLED", nullable=false)
    private boolean enabled = false;


    //@ManyToMany(targetEntity = JpaGroupImpl.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //@ManyToMany(targetEntity = JpaGroupImpl.class,fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @ManyToMany(targetEntity = JpaGroupImpl.class,fetch = FetchType.EAGER)
    @JoinTable(
      name="AUTH_USER_GROUP",
      joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="GROUP_ID", referencedColumnName="ID")})
    private List<Group> groups = new ArrayList<Group>() ;

    public List<Group> getGroups() {
        return this.groups;
    }

    public void addGroup(final Group group){
        //final JpaGroupImpl jpaGroupImpl = new JpaGroupImpl(this,group);
        //this.groups.add(jpaGroupImpl);
        this.groups.add(group);
    }

    @Override
    public void removeGroup(Group group) {
/*        for(Group aGroup : this.groups){
            System.out.println("Set Group HashCode:  " + aGroup.hashCode() );
        }
            System.out.println("Passed Group HashCode:  " + group.hashCode() );
*/
        if(this.groups.contains(group)){
            this.groups.remove(group);
        }

//          for(Group aGroup : this.groups){
//            System.out.println("Set Group HashCode:  " + aGroup.hashCode() );
//              if(aGroup.equals(group)){
//                  this.groups.remove(group);
//              }
//        }
    }

    @Override
    public void removeAllGroups(){
        this.groups.clear();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

        @Override
    public String toString() {
        return new ToStringBuilder(this).append("User Name",this.userName).append("Description",this.description).
                append("Enabled",this.enabled).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof JpaUserImpl == false) return false;

        JpaUserImpl jpaUser = (JpaUserImpl) o;
        EqualsBuilder b= new EqualsBuilder();
         b.append(this.userName, jpaUser.userName)
         .append(this.description, jpaUser.description)
         .append(this.enabled, jpaUser.enabled);
         return b.isEquals();

//        if (groups != null ? !groups.equals(jpaUser.groups) : jpaUser.groups!= null)
//            return false;
//
//        if (id != null ? !id.equals(jpaUser.id) : jpaUser.id != null) return false;
//        if (userName != null ? !userName.equals(jpaUser.userName) : jpaUser.userName != null)
//            return false;
//        if (password != null ? !password.equals(jpaUser.password) : jpaUser.password != null) return false;
//        if (description != null ? !description.equals(jpaUser.description) : jpaUser.description != null)
//            return false;
//        if (enabled != jpaUser.enabled) return false;
//
//        return true;
    }

    @Override
    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 41 * result + (groups != null ? groups.hashCode() : 0);
//        result = 41 * result + (userName != null ? userName.hashCode() : 0);
//        result = 41 * result + (password != null ? password.hashCode() : 0);
//        result = 41 * result + (description != null ? description.hashCode() : 0);
//        result = 41 * result + (enabled == true ? 1 : 0);
//        return result;
        return new HashCodeBuilder()
         .append(this.userName)
                 .append(this.description).append(this.enabled)
         .toHashCode();
    }

    @Override
    public List<Group> getUserGroups() {
        return groups;
    }
}
