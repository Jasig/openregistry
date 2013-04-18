package org.openregistry.core.service.identitycard;

import org.openregistry.core.domain.*;
import org.openregistry.core.service.ServiceExecutionResult;

/**
 * A service for generating updating, deleting and expiring the id cards
 */
public interface IdCardManagementService {

    ServiceExecutionResult<IdCard>  generateNewIdCard(Person p);

    ServiceExecutionResult<IdCard>  assignProximityNumber(Person p,String proximityNumber);
    ServiceExecutionResult<IdCard>  expireIdCard(Person p);

    }

