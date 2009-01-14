package org.openregistry.core.service.internal;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Open Registry's default implementation of the <code>PersonService</code>.
 *
 * @since 1.0
 *        TODO: implementation
 */
public class DefaultPersonService implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * @see org.openregistry.core.service.PersonService#findPersonById(Long)
     */
    public Person findPersonById(Long id) {
        return this.personRepository.findByInternalId(id);
    }

    /**
     * @see org.openregistry.core.service.PersonService#validateAndSaveRoleForPerson(org.openregistry.core.domain.Person, org.openregistry.core.domain.Role)
     */
    public ServiceExecutionResult validateAndSaveRoleForPerson(Person person, Role role) {
        return null;
    }
}
