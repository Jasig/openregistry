package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.OrganizationalUnit;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.DateRange;
import org.openregistry.core.domain.Url;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 11:00:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SorRole extends Serializable, DateRange {

    /**
     * Identifier as assigned from the System of Record
     *
     * @return the identifier from the system of record.  CANNOT be null.
     */
    String getSorId();

    /**
     * Sets the Identifier for this Role WITHIN the system of record.
     * @param id the identifier. CANNOT be null, if setting.
     */
    void setSorId(String id);

    /**
     * The identifier for the System of Record that is asserting this role.  CANNOT be null.
     *
     * @return the identifier for the System of Record
     */
    String getSourceSorIdentifier();

    /**
     * Sets the identifier for the system of record.
     *
     * @param sorIdentifier the system of record identifier.
     */
    void setSourceSorIdentifier(String sorIdentifier);

    Type getAffiliationType();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);
    
    RoleInfo getRoleInfo();

    SorSponsor setSponsor();

    SorSponsor getSponsor();

    Type getTerminationReason();

    void setTerminationReason(Type reason);
    
    Set<Address> getAddresses();
    
    Address addAddress();

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    EmailAddress addEmailAddress();

    Phone addPhone();
    
    Set<Url> getUrls();

    Url addUrl();

    Set<Leave> getLeaves();

    String getTitle();

    OrganizationalUnit getOrganizationalUnit();

    Campus getCampus();

    String getLocalCode();
 
    /**
     * Sets the calculated role Id to create the link between the SoR Role and the calculated role.
     * @param roleId the role Id.  CANNOT be null.
     */
    void setRoleId(Long roleId);

    /**
     * Returns the calculated role identifier, if one has been set.  If there is a calculated role, this MUST be set.
     * @return the role id, if a calculated role exists.  Otherwise, NULL.
     */
    Long getRoleId();

}
