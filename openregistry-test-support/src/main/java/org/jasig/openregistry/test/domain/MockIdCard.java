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
    @Override
    public String getCardNumber() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCardNumber(String cardNumber) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCardSecurityValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCardSecurityValue(String cardSecurityValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getBarCode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBarCode(String barCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getProximityNumber() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setProximityNumber(String proximityNumber) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getStartDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getExpirationDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getUpdateDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
