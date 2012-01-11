package org.openregistry.core.domain.jpa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.IdentifierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/25/11
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="AUX_IDENTIFIERS")

@Table(name="AUX_IDENTIFIERS", uniqueConstraints= @UniqueConstraint(columnNames={"IDENTIFIER_T", "IDENTIFIER"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "AUX_IDENTIFIERS", indexes = {
        @Index(name="AUX_ID_ID_TYPE_IDX", columnNames = {"IDENTIFIER", "IDENTIFIER_T"}),
        @Index(name="AUX_IDENTIFIER_IDX", columnNames = {"IDENTIFIER"}),
        @Index(name = "AUX_PROGRAM_IDX", columnNames = "PROGRAM_ID")
})
public class JpaAuxiliaryIdentifierImpl extends Entity implements AuxiliaryIdentifier{

    protected static final Logger logger = LoggerFactory.getLogger(JpaAuxiliaryIdentifierImpl.class);

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AUX_IDENTIFIERS_SEQ")
    @SequenceGenerator(name="AUX_IDENTIFIERS_SEQ",sequenceName="AUX_IDENTIFIERS_SEQ",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name="IDENTIFIER", length=100, nullable=false)
    private String value;

    @ManyToOne(optional=false,fetch = FetchType.EAGER)
    @JoinColumn(name="IDENTIFIER_T")
    private JpaIdentifierTypeImpl type;

    @Column(name="IS_PRIMARY", nullable=false)
    private boolean primary = true;

    @Column(name="IS_DELETED", nullable=false)
    private boolean deleted = false;

    @Column(name="CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name="DELETED_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name="NOTIFICATION_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationDate;

    @ManyToOne(optional=false)
    @JoinColumn(name="PROGRAM_ID")
    private JpaAuxiliaryProgramImpl program;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public Date getDeletedDate() {
        return deletedDate;
    }

    @Override
    public Date getNotificationDate() throws IllegalStateException {
        return notificationDate;
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public IdentifierType getType() {
        return type;
    }


    @Override
    public void setCreationDate(Date originalCreationDate) {
        this.creationDate = originalCreationDate;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void setDeletedDate(Date date) {
        this.deletedDate = date;
    }

    @Override
    public void setNotificationDate(Date notificationDate) throws IllegalStateException {
        this.notificationDate = notificationDate;
    }

    @Override
    public void setPrimary(boolean primaryValue) {
        this.primary = primaryValue;
    }

    @Override
    public void setValue(String identifier) {
        this.value = identifier;
    }

    //following function does not come from the interface
    public void setType(JpaIdentifierTypeImpl identifierType) {
        this.type = identifierType;
    }

    @Override
    public AuxiliaryProgram getProgram() {
        return this.program;
    }

    @Override
    public void setProgram(AuxiliaryProgram program) {
        this.program = (JpaAuxiliaryProgramImpl) program;
    }

    /*
    TODO - In my (nah67) opinion equals() and hasCode() should take care of all columns
    instead of only two.

    For example if one detached object has identifier ABC with isDeleted = false
    but there is another object with identifier ABC with isDeleted = true to me they are
    two distinct objects. However, in case of JpaIdentifierImpl it is taking care of
    only value and type. I am following the same approach only because of consistency.
    However, I feel that this implementation should change. Therefore, a team discussion
    is needed on this.
    */


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Value",this.value).append("Type",this.type).toString();
    }
    @Override
   public int hashCode()
   {
      return new HashCodeBuilder()
         .append(this.value)
                 .append(this.type)
//         .append(this.type!=null?this.type.getName():"NULL")
         .toHashCode();
   }
    @Override
   public boolean equals(Object obj)
   {
      if (obj instanceof JpaAuxiliaryIdentifierImpl == false) return false;
      if (this == obj)  return true;
      final JpaAuxiliaryIdentifierImpl otherObject = (JpaAuxiliaryIdentifierImpl) obj;

      EqualsBuilder b= new EqualsBuilder();
         b.append(this.value, otherObject.value)
         .append(this.type, otherObject.type);
         return b.isEquals();
   }


}
