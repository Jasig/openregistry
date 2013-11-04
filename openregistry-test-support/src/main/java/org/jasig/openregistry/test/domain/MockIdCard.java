package org.jasig.openregistry.test.domain;

import org.openregistry.core.domain.IdCard;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: msidd
 * Date: 4/16/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockIdCard implements IdCard {



    private MockPerson person;


    private String cardNumber;


    private String cardSecurityValue;


    private String barCode;



    private String proximityNumber;


    private Date creationDate;


    private Date expirationDate;
    private boolean primary = true;

    public MockIdCard(final MockPerson person,final String cardNumber,final String cardSecurityValue,final String barCode){
        this.person=person;
        this.cardNumber=cardNumber;
        this.cardSecurityValue=cardSecurityValue;
        this.barCode=barCode;
        this.creationDate=new Date();
        this.updateDate=new Date();
    }

    public MockPerson getPerson() {
        return person;
    }

    public void setPerson(MockPerson person) {
        this.person = person;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardSecurityValue() {
        return cardSecurityValue;
    }

    public void setCardSecurityValue(String cardSecurityValue) {
        this.cardSecurityValue = cardSecurityValue;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProximityNumber() {
        return proximityNumber;
    }

    public void setProximityNumber(String proximityNumber) {
        this.proximityNumber = proximityNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private Date updateDate;


    public boolean isPrimary() {
        return this.primary;
    }
    public void setPrimary(boolean value) {
        this.primary = value;
    }
}
