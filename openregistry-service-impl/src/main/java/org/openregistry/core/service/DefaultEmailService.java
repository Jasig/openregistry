package org.openregistry.core.service;

import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.utils.ValidationUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import java.util.*;

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
    public String findEmailForSorPersonWithRoleIdentifiedByAffiliation(SorPerson sorPerson,
                                                                       Type emailType,
                                                                       Type affiliationType) {

        List<SorRole> openRoles = sorPerson.findOpenRolesByAffiliation(affiliationType);
        if (openRoles.isEmpty()) {
            return null;
        }
        //TODO: confirm if this is the correct behavior. If not, how to handle it otherwise i.e. affiliation could have multiple roles?
        String returnEmailValue = null;
        for (SorRole r : openRoles) {
            for(EmailAddress e : r.getEmailAddresses()) {
                if(emailType.isSameAs(e.getAddressType())) {
                    returnEmailValue = e.getAddress();
                    break;
                }
            }
            if(returnEmailValue != null) {
                break;
            }
        }
        return returnEmailValue;
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

        ServiceExecutionResult<SorPerson> ser = this.personService.updateSorPerson(sorPerson);
        //OR-384
        if(!ser.succeeded()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ser;
    }

    @Override
    public ServiceExecutionResult<SorPerson> saveOrCreateEmailForSorPersonForAllRoles(SorPerson sorPerson, String emailAddress,
                                                                                                   Type emailType) {

        //get all the roles for an SorPerson
        List<SorRole> openRoles = sorPerson.getRoles();
        if (openRoles.isEmpty()) {
            return new GeneralServiceExecutionResult<SorPerson>((SorPerson) null);
        }
        for (SorRole r : openRoles) {
            r.addOrUpdateEmail(emailAddress, emailType);
        }

        ServiceExecutionResult<SorPerson> ser = this.personService.updateSorPerson(sorPerson);
        //OR-384
        if(!ser.succeeded()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ser;
    }

    @Override
    //OR-386
    public List<ServiceExecutionResult<SorPerson>> saveOrCreateEmailForAllSorPersons(List<SorPerson> sorPersons,String emailAddress,Type emailType) {
        List <ServiceExecutionResult<SorPerson>> listOfServiceExecutionResults = new ArrayList<ServiceExecutionResult<SorPerson>>();
        for(SorPerson sorPerson:sorPersons){
            ServiceExecutionResult<SorPerson> result = null;
            result = saveOrCreateEmailForSorPersonForAllRoles(sorPerson,
                    emailAddress,
                    emailType);

            listOfServiceExecutionResults.add(result);
            if(!result.succeeded()){
                //transaction rollback
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                //there is no need to execute the loop as the trasnaction is rolling back
                break;
            }

        }
        return listOfServiceExecutionResults;
    }
}
