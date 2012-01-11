package org.openregistry.core.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 10/20/11
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuxiliaryProgram {

    Long getId();
    String getProgramName();
    Long getSponsorId();
    Type getSponsorType();
    Date getAffiliationDate();
    Date getTerminationDate();
    Set<AuxiliaryIdentifier> getIdentifiers();
    void addAuxiliaryIdentifier(AuxiliaryIdentifier identifier);
    void removeAuxiliaryIdentifier(AuxiliaryIdentifier identifier);

    void setProgramName(String auxiliaryProgramName);
    void setSponsorId(Long sponsorId);
    void setSponsorType(Type sponsorType);
    void setAffiliationDate(Date affiliationDate);
    void setTerminationDate(Date terminationDate);
}
