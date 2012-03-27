package org.openregistry.core.authorization.jpa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.authorization.Authority;
import org.openregistry.core.authorization.Group;
import org.openregistry.core.authorization.User;
import org.openregistry.core.domain.internal.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import javax.persistence.*;
/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */

@javax.persistence.Entity(name="auth_groups")
@Table(name="auth_groups")
@Audited
@org.hibernate.annotations.Table(appliesTo = "auth_groups", indexes = {
        @Index(name = "AUTH_GROUP_NAME_IDX", columnNames = "GROUP_NAME")
})
public class JpaGroupImpl extends Entity implements Group{


    public JpaGroupImpl(JpaUserImpl jpaUserImpl, Group group){
        this.groupName = group.getGroupName();
        this.description = group.getDescription();
        this.enabled = group.isEnabled();

    }

     public JpaGroupImpl(){

    }

    protected static final Logger logger = LoggerFactory.getLogger(JpaUserImpl.class);

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auth_groups_seq")
    @SequenceGenerator(name="auth_groups_seq",sequenceName="auth_groups_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="GROUP_NAME",nullable=false)
    private String groupName;

    @Column(name="DESCRIPTION",nullable=true)
    private String description;

    @Column(name="IS_ENABLED", nullable=false)
    private boolean enabled = false;

    //@ManyToMany(mappedBy = "groups",fetch=FetchType.EAGER,targetEntity = JpaUserImpl.class)
    @ManyToMany(mappedBy = "groups",targetEntity = JpaUserImpl.class)
    private List<User> users = new ArrayList<User>();

    //@ManyToMany(targetEntity = JpaAuthorityImpl.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //@ManyToMany(targetEntity = JpaAuthorityImpl.class,fetch = FetchType.EAGER)
    @ManyToMany(targetEntity = JpaAuthorityImpl.class)
    @JoinTable(
      name="AUTH_GROUP_AUTHORITY",
      joinColumns={@JoinColumn(name="GROUP_ID", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="AUTHORITY_ID", referencedColumnName="ID")})
    private List<Authority> authorities = new java.util.ArrayList<Authority>();

    public List<Authority> getAuthorities() {
        return this.authorities;
    }

    public Authority addAuthority(final Authority authority){
        //final JpaAuthorityImpl jpaAuthorityImpl = new JpaAuthorityImpl(this,authority);
        //this.authorities.add(jpaAuthorityImpl);
        this.authorities.add(authority);
        return authority;

    }

    @Override
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User addUser(User user) {
        this.users.add(user);
        return user;
    }

    @Override
    public void removeUser(User user) {
        this.users.remove(user);

    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Group Name",this.groupName).append("Description",this.description).
                append("Enabled",this.enabled).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof JpaGroupImpl == false) return false;

        JpaGroupImpl jpaGroup = (JpaGroupImpl) o;
        EqualsBuilder b= new EqualsBuilder();
         b.append(this.groupName, jpaGroup.groupName)
         .append(this.description, jpaGroup.description)
         .append(this.enabled, jpaGroup.enabled);
         return b.isEquals();

//        if (users != null ? !users.equals(jpaGroup.users) : jpaGroup.users!= null)
//            return false;
//
//        if (authorities != null ? !authorities.equals(jpaGroup.authorities) : jpaGroup.authorities!= null)
//            return false;
//
//        if (id != null ? !id.equals(jpaGroup.id) : jpaGroup.id != null) return false;
//
//        if (groupName != null ? !groupName .equals(jpaGroup.groupName) : jpaGroup.groupName != null)
//            return false;
//        if (description != null ? !description.equals(jpaGroup.description) : jpaGroup.description != null)
//            return false;
//        if (enabled != jpaGroup.enabled) return false;
//        return true;
    }

    @Override
    public int hashCode() {

//        int result = id != null ? id.hashCode() : 0;
//        result = 51 * result + (users != null ? users.hashCode() : 0);
//        result = 51 * result + (authorities != null ? authorities.hashCode() : 0);
//        result = 51 * result + (groupName != null ? groupName.hashCode() : 0);
//        result = 51 * result + (description != null ? description.hashCode() : 0);
//        result = 51 * result + (enabled == true ? 1 : 0);
//        return result;

        return new HashCodeBuilder()
         .append(this.groupName)
                 .append(this.description).append(this.enabled)

         .toHashCode();
    }

    @Override
    public List<Authority> getGroupAuthorities() {
        return authorities;
    }

    @Override
    public void removeAllAuthorities(){
        this.authorities.clear();
    }

    @Override
    public void removeAllUsers() {
        this.users.clear();
    }
}
