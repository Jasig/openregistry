package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Default implementation of <code>EmailService</code> building on the lower level <code>PersonService</code>
 *
 * @since 1.0
 */
@Named("emailService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultEmailService implements EmailService {

    private PersonService personService;

    @Inject
    public DefaultEmailService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ServiceExecutionResult<SorPerson> saveOrCreateEmailForSorPersonWithRoleIdentifiedByAffiliation(SorPerson sorPerson,
                                                                                                          String emailAddress,
                                                                                                          Type emailType,
                                                                                                          Type affiliationType) {
        List<SorRole> openRoles = sorPerson.findOpenRolesByAffiliation(affiliationType);
        if (openRoles.isEmpty()) {
            return new GeneralServiceExecutionResult<SorPerson>((SorPerson) null);
        }
        for (SorRole r : openRoles) {
            r.addOrUpdateEmail(emailAddress, emailType);
        }
        return this.personService.updateSorPerson(sorPerson);
    }
}
