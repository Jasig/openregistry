package org.openregistry.core.domain.jpa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.IdCard;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * JPa implementation of ID card class
 * @author  Muhammad Siddique
 * Date: 4/16/13
 * Time: 9:57 AM
 *
 */

@javax.persistence.Entity(name="idCard")
@Table(name="prc_id_cards", uniqueConstraints= {@UniqueConstraint(columnNames={"card_number"}),@UniqueConstraint(columnNames={"bar_code"})} )
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_id_cards", indexes = {
        @Index(name = "PRC_CARD_PERSON_IDX", columnNames = {"person_id"})})


public class JpaIdCardImpl  extends Entity  implements  IdCard{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_id_cards_seq")
    @SequenceGenerator(name="prc_id_cards_seq",sequenceName="prc_id_cards_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    @Column(name="card_number", length=100, nullable=false)
    private String cardNumber;

    @Column(name="security_value", length=100, nullable=false)
    private String cardSecurityValue;

    @Column(name="bar_code", length=100, nullable=false)
    private String barCode;

    @Column(name="proximity_number", length=100, nullable=true)

    private String proximityNumber;


    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();



    @Column(name="expiration_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name="update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name="is_primary", nullable=false)
    private boolean primary = true;

    public JpaIdCardImpl(){

    }

    public JpaIdCardImpl(final JpaPersonImpl person){
        this.person=person;
    }
    public JpaIdCardImpl(final JpaPersonImpl person,final String cardNumber,final String cardSecurityValue,final String barCode){
        this(person);
        this.cardNumber=cardNumber;
        this.cardSecurityValue=cardSecurityValue;
        this.barCode=barCode;
        this.creationDate=new Date();
        this.updateDate=new Date();
    }

    protected Long getId() {
        return id;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public void setCardNumber(String cardNumber) {
        this.cardNumber=cardNumber;
    }
    @Override
    public String getCardSecurityValue() {
        return cardSecurityValue;
    }

    @Override
    public void setCardSecurityValue(String cardSecurityValue) {
        this.cardSecurityValue=cardSecurityValue;
    }


    @Override
    public String getBarCode() {
       return barCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barCode=barCode;
    }
    @Override
    public void setProximityNumber(String proximityNumber) {
        this.proximityNumber = proximityNumber;
    }
    @Override
    public String getProximityNumber() {
        return proximityNumber;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate=creationDate;
    }


    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public int hashCode(){
      return new HashCodeBuilder().append(this.getCardNumber()).append(this.getBarCode()).append(this.cardSecurityValue).toHashCode();
    }
    @Override
    public String toString(){
        return new ToStringBuilder(this).append(this.cardSecurityValue).append(this.cardNumber).append(this.getBarCode()).toString();

    }
    @Override
    public boolean equals(Object obj){
          if (obj instanceof  JpaIdCardImpl==false) return false;
          if (this==obj) return true;
        JpaIdCardImpl otherObj=(JpaIdCardImpl)obj;
        return new EqualsBuilder().append(this.cardNumber,otherObj.cardNumber).
                    append(this.barCode,otherObj.cardNumber).append(this.cardSecurityValue,otherObj.cardSecurityValue).isEquals();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Date();
    }
    public boolean isPrimary() {
        return this.primary;
    }
    public void setPrimary(boolean value) {
        this.primary = value;
    }



}
