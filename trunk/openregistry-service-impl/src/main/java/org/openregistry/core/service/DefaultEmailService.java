package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import javax.inject.Inject;
import java.util.List;

/**
 * Default implementation of <code>EmailService</code> building on the lower level <code>PersonService</code>
 * @since 1.0
 */
public class DefaultEmailService implements EmailService {

    private PersonService personService;

    @Inject
    public DefaultEmailService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ServiceExecutionResult<SorPerson> saveOrCreateAffiliatedEmailForSelectedSorPerson(Person calculatedPerson,
                                                                                             String emailAddress,
                                                                                             Type emailType, Type affiliationType) {

        SorPerson p = findFirstSorPersonMatchingRoleAffiliation(calculatedPerson, affiliationType);
        if(p == null) {
            return new GeneralServiceExecutionResult<SorPerson>((SorPerson)null);
        }
        List<SorRole> openRoles = p.findOpenRolesByAffiliation(affiliationType);
        if(openRoles.isEmpty()) {
            return new GeneralServiceExecutionResult<SorPerson>((SorPerson)null);    
        }
        for(SorRole r : openRoles) {
            r.addOrUpdateEmail(emailAddress, emailType);
        }
        return this.personService.updateSorPerson(p);        
    }

    private SorPerson findFirstSorPersonMatchingRoleAffiliation(Person calculatedPerson, Type affiliationType) {
        List<SorPerson> sorPeople = this.personService.getSorPersonsFor(calculatedPerson);
        if(sorPeople.isEmpty()) {
            return null;
        }
        for(SorPerson sorPerson : sorPeople) {
            for(SorRole r : sorPerson.getRoles()) {
                if(r.getAffiliationType().isSameAs(affiliationType)) {
                    //Short-circuit first person matching the given role's affiliation, as was discussed with
                    //the Rutgers team
                    return sorPerson;
                }
            }
        }
        return null;
    }
}
