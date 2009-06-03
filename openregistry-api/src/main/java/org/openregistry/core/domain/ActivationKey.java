package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 11, 2009
 * Time: 3:55:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ActivationKey extends Serializable {

    String getValue();

    void setValue(String value);

    Person getPerson();

    void setPerson(Person person);

    Date getExpirationDate();

    void setExpirationDate(Date date);

    Date getActivitationDate();

    void setActivationDate(Date date);

}
