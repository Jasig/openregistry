package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Main API for email related operations
 *
 * @since 1.0
 */
public interface EmailService {

    ServiceExecutionResult<SorPerson> saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation(SorPerson sorPerson, String emailAddress,
                                                                                                   Type emailType,
                                                                                                   Type affiliationType);

    ServiceExecutionResult<SorPerson> saveOrCreateEmailForSorPersonForAllRoles(SorPerson sorPerson, String emailAddress,
                                                                                                   Type emailType);

    String findEmailForSorPersonWithRoleIdentifiedByAffiliation(SorPerson sorPerson,
                                                                Type emailType,
                                                                Type affiliationType);

    List<ServiceExecutionResult<SorPerson>> saveOrCreateEmailForAllSorPersons(List<SorPerson> sorPersons, String emailAddress,
                                                                Type emailType);

}
