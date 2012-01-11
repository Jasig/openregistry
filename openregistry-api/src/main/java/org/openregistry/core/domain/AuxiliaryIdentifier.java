package org.openregistry.core.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/20/11
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuxiliaryIdentifier {
//    Long getId();

    Date getCreationDate();
    boolean isDeleted();
    Date getDeletedDate();
    Date getNotificationDate () throws IllegalStateException;
    boolean isPrimary();
    //Value is the Identifier itself
    String getValue();
    IdentifierType getType();
    public AuxiliaryProgram getProgram();

    void setCreationDate(Date originalCreationDate);
    void setDeleted(boolean deleted);
    void setDeletedDate(Date date);
    void setNotificationDate(Date notificationDate) throws IllegalStateException;
    void setPrimary(boolean primaryValue);
    void setValue(String identifier);
    void setProgram(AuxiliaryProgram program);
    //void setType(IdentifierType identifierType);
}
