package org.openregistry.core.domain;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Jan 21, 2009
 * Time: 1:14:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Phone {

    Long getId();

    Type getAddressType();

    Type getPhoneType();

    String getCountryCode();

    String getAreaCode();

    String getNumber();

    String getExtension();
}
