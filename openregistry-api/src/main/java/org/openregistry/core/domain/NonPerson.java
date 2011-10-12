package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 8/23/11
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NonPerson extends Serializable {
    Long getId();
    String getName();
    //Type getType();
    Long getSponsorId();
    Type getSponsorType();
    Date getAffiliationDate();
    Date getTerminationDate();

    void setName(String name);
    void setSponsorId(Long sponsorId);
    void setSponsorType(Type sponsorType);
    void setAffiliationDate(Date affiliationDate);
    void setTerminationDate(Date terminationDate);


}
