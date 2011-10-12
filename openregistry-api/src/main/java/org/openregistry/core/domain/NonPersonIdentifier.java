package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 8/23/11
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NonPersonIdentifier extends Serializable {
    Long getId();
    Date getCreationDate();
    boolean isDeleted();
    Date getDeletedDate();
    Date getNotificationDate () throws IllegalStateException;
    boolean isPrimary();
    String getValue();
    IdentifierType getType();


    void setCreationDate(Date originalCreationDate);
    void setDeleted(boolean value);
    void setDeletedDate(Date date);
    void setNotificationDate(Date date) throws IllegalStateException;
    void setPrimary(boolean value);
}
