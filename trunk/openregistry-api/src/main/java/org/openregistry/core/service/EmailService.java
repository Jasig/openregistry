package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * Main API for email related operations
 *
 * @since 1.0
 */
public interface EmailService {

    ServiceExecutionResult<SorPerson> saveOrCreateAffiliatedEmailForSelectedSorPerson(Person calculatedPerson, String emailAddress,
                                                                                      Type emailType,
                                                                                      Type affiliationType);

}
