/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain.jpa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import javax.persistence.*;

import java.util.Date;

/**
 * Unique Constraints assumes each calculated person can have only one entry for each identifier type
 * For example, a person can't have multiple SSN or NetIDs
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="identifier")

@Table(name="prc_identifiers", uniqueConstraints= @UniqueConstraint(columnNames={"identifier_t", "identifier"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_identifiers", indexes = {
        @Index(name="ID_ID_TYPE_INDEX", columnNames = {"identifier", "identifier_t"}),
        @Index(name="ID_IDENTIFIER_INDEX", columnNames = {"identifier"}),
        @Index(name = "PRC_IDENTIF_PERSON_IDX", columnNames = "person_id")
})
public class JpaIdentifierImpl extends Entity implements Identifier {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_identifiers_seq")
    @SequenceGenerator(name="prc_identifiers_seq",sequenceName="prc_identifiers_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    @ManyToOne(optional=false,fetch = FetchType.EAGER)
    @JoinColumn(name="identifier_t")
    private JpaIdentifierTypeImpl type;

    @Column(name="identifier", length=100, nullable=false)
    private String value;

    @Column(name="is_primary", nullable=false)
    private boolean primary = true;

    @Column(name="is_deleted", nullable=false)
    private boolean deleted = false;

    @Column(name="changeable", nullable=true)
    private Boolean changeable = false;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name="deleted_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    
    @Column(name="notification_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationDate;
    
    public JpaIdentifierImpl() {
        // nothing to do
    }

    public JpaIdentifierImpl(final JpaPersonImpl person) {
        this.person = person;
    }

    public JpaIdentifierImpl(final JpaPersonImpl person, final JpaIdentifierTypeImpl jpaIdentifierType, final String value) {
        this(person);
        this.type = jpaIdentifierType;
        this.value = value;
    }

    protected Long getId() {
        return this.id;
    }

    public IdentifierType getType() {
    	// safe to return a reference since the type should be immutable
    	return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isPrimary() {
    	return this.primary;
    }

    public boolean isChangeable() {
        if (changeable == null) return false;
        else return changeable.booleanValue();
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    public boolean isDeleted() {
    	return this.deleted;
    }

    @Override
    public Date getCreationDate() {
        return (this.creationDate == null) ? null : new Date(this.creationDate.getTime());
    }
    @Override
    public void setCreationDate(Date originalCreationDate) {
        this.creationDate=originalCreationDate;
    }

    @Override
    public Date getDeletedDate() {
        return (this.deletedDate == null) ? null : new Date(this.deletedDate.getTime());
    }

    public void setType(JpaIdentifierTypeImpl type) {
        this.type = type;
    }

    @Override
    public void setDeletedDate(Date date) {

        this.deleted=true;
        this.deletedDate=date;
    }

    /**
     * @throws IllegalStateException if this identifier is not of a notifiable type
     * @see org.openregistry.core.domain.Identifier#getNotificationDate()
     */
	public Date getNotificationDate() throws IllegalStateException {
		if (this.type == null || !this.type.isNotifiable()) {
			throw new IllegalStateException ("Only identifiers with notifiable IdentifierType may have notification dates");
		} else {
			return (this.notificationDate == null) ? null : new Date(this.notificationDate.getTime());
		}
	}

    public void setValue(String value) {
        this.value = value;
    }

    public void setPrimary(boolean value) {
    	this.primary = value;
    }

    public void setDeleted(boolean value) {
        if (value == this.deleted) {
            return;
        }
        
    	this.deleted = value;

        if (value) {
            this.deletedDate = new Date();
        } else {
            this.deletedDate = null;
        }
    }

    /**
     * @throws IllegalStateException if the type of this identifier is not notifiable
     * @see org.openregistry.core.domain.Identifier#setNotificationDate(java.util.Date)
     */
	public void setNotificationDate(Date date) throws IllegalStateException {
		if (date != null) {
			if (this.type == null || !this.type.isNotifiable()) {
				throw new IllegalStateException ("Only identifiers with notifiable IdentifierType may have notification dates");
			} else {
				this.notificationDate = new Date(date.getTime());
			}
		} else {
			this.notificationDate = null;
		}
	}

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
      if (obj instanceof JpaIdentifierImpl == false) return false;
      if (this == obj)  return true;
      final JpaIdentifierImpl otherObject = (JpaIdentifierImpl) obj;

      EqualsBuilder b= new EqualsBuilder();
         b.append(this.value, otherObject.value)
         .append(this.type, otherObject.type);
         return b.isEquals();


   }
}
