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

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 9/6/11
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */

@javax.persistence.Entity(name="auth_authorities")
@Table(name="auth_authorities")
@Audited
@org.hibernate.annotations.Table(appliesTo = "auth_authorities", indexes = {
        @Index(name = "AUTH_AUTHORITY_NAME_IDX", columnNames = "AUTHORITY_NAME")
})
public class JpaAuthorityImpl extends Entity implements Authority{

    public JpaAuthorityImpl(Group group, Authority authority){
        this.authorityName = authority.getAuthorityName();
        this.description = authority.getDescription();
    }

    public JpaAuthorityImpl(){
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auth_authority_seq")
    @SequenceGenerator(name="auth_authority_seq",sequenceName="auth_authority_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="AUTHORITY_NAME",nullable=false)
    private String authorityName;

    @Column(name="DESCRIPTION",nullable=true)
    private String description;

    @ManyToMany(mappedBy = "authorities",targetEntity = JpaGroupImpl.class,fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<Group>();

    public Set<Group> getGroups(){
        return this.groups;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Authority Name",this.authorityName)
                .append("Description",this.description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaAuthorityImpl jpaAuthority = (JpaAuthorityImpl) o;
        EqualsBuilder b= new EqualsBuilder();
         b.append(this.authorityName, jpaAuthority.authorityName)
         .append(this.description, jpaAuthority.description);
         return b.isEquals();

//        if (groups != null ? !groups.equals(jpaAuthority.groups) : jpaAuthority.groups!= null)
//            return false;
//
//        if (id != null ? !id.equals(jpaAuthority.id) : jpaAuthority.id != null) return false;
//
//        if (authorityName != null ? !authorityName.equals(jpaAuthority.authorityName) : jpaAuthority.authorityName!= null)
//            return false;
//        if (description != null ? !description.equals(jpaAuthority.description) : jpaAuthority.description != null)
//            return false;
//        return true;
    }

    @Override
    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 61 * result + (groups != null ? groups.hashCode() : 0);
//        result = 61 * result + (authorityName!= null ? authorityName.hashCode() : 0);
//        result = 61 * result + (description != null ? description.hashCode() : 0);
//        return result;

         return new HashCodeBuilder()
         .append(this.authorityName)
                 .append(this.description).toHashCode();
    }
}
