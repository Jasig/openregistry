package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.validation.IdentifierFormatValidator;
import org.openregistry.core.repository.AuxiliaryIdentifierRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.GeneralServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

import javax.inject.Inject;
import java.util.Map;

/**
 * @since 1.0
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class DefaultNetIdManagementService implements NetIdManagementService {

    private PersonService personService;

    private ReferenceRepository referenceRepository;

    private String netIdTypeCode = "NETID";

    private AuxiliaryIdentifierRepository auxiliaryIdentifierRepository;

    @Inject
    private IdentifierFormatValidator identifierFormatValidatorImpl;

    @Inject
    public DefaultNetIdManagementService(PersonService personService,
                                         ReferenceRepository referenceRepository,
                                         AuxiliaryIdentifierRepository auxiliaryIdentifierRepository
                                         ) {
        this.personService = personService;
        this.referenceRepository = referenceRepository;
        this.auxiliaryIdentifierRepository = auxiliaryIdentifierRepository;
    }

    public void setNetIdTypeCode(String netIdTypeCode) {
        this.netIdTypeCode = netIdTypeCode;
    }

    @Override
    public ServiceExecutionResult<Identifier> changePrimaryNetId(String currentNetIdValue, String newNetIdValue) throws IllegalArgumentException, IllegalStateException {
        if(currentNetIdValue == newNetIdValue) {
            throw new IllegalArgumentException("Primary and Non-Primary net ids cannot be the same");
        }

        final Person person = this.personService.findPersonByIdentifier(netIdTypeCode, currentNetIdValue);
        if (person == null) {
            throw new IllegalArgumentException(format("The person with the provided netid [%s] does not exist", currentNetIdValue));
        }
        Person person2 = this.personService.findPersonByIdentifier(netIdTypeCode, newNetIdValue);
        //if person can't be found by netid try to see if it is persons own iid
        if(person2==null ){

             person2 = this.personService.findPersonByIdentifier(Type.IdentifierTypes.IID.toString(),   newNetIdValue.toUpperCase());
        }


        if (person2 != null && person.getId() != person2.getId()) {
            throw new IllegalStateException(format("The person with the proposed new netid [%s] already exists.", newNetIdValue));
        }

         //Check if the netId is according to proper format
        if (person2 == null && !identifierFormatValidatorImpl.isNetIDAcceptable(newNetIdValue)){
                        throw new IllegalArgumentException("NetId is not according to accepted format");
        }


        //check if the netId already exists for an auxiliary program (also called program account)
        if (auxiliaryIdentifierRepository.identifierExistsForProgramAccount(newNetIdValue,netIdTypeCode)) {
            throw new IllegalStateException(format("netid [%s] already exists for a program.", newNetIdValue));
        }

        Map<String, Identifier> primaryIds = person.getPrimaryIdentifiersByType();
        Identifier currentNetId = primaryIds.get(netIdTypeCode);
        //Candidate for NPE - which is not handled here as it would be serious data error to not have a primary netid
        if (currentNetId.getValue().equals(newNetIdValue)) {
            throw new IllegalStateException(format("The provided new primary netid [%s] already assigned to the person.", newNetIdValue));
        }
        else if(!currentNetId.getValue().equals(currentNetIdValue)) {
            throw new IllegalArgumentException(format("The provided primary netid [%s] does not match the current primary netid", currentNetIdValue));
        }

        //check if the provided new net id is already there, and if so, do the update, otherwise - do the insert.
        Identifier providedId = person.findIdentifierByValue(netIdTypeCode, newNetIdValue);
        if (providedId == null) {
            final IdentifierType idType = this.referenceRepository.findIdentifierType(this.netIdTypeCode);
            providedId = person.addIdentifier(idType, newNetIdValue);
        }

        providedId.setPrimary(true);
        providedId.setDeleted(false);

        currentNetId.setPrimary(false);
        currentNetId.setDeleted(true);

        return new GeneralServiceExecutionResult<Identifier>(providedId);
    }

    @Override
    public ServiceExecutionResult<Identifier> addNonPrimaryNetId(String primaryNetIdValue, String newNonPrimaryNetIdValue) throws IllegalArgumentException, IllegalStateException {

        //Check if the netId is according to proper format
        if(!identifierFormatValidatorImpl.isNetIDAcceptable(newNonPrimaryNetIdValue)){
            throw new IllegalArgumentException("NetId is not according to accepted format");
        }

        if ((this.personService.findPersonByIdentifier(netIdTypeCode, newNonPrimaryNetIdValue) != null)) {
            throw new IllegalStateException(format("The person with the proposed new netid [%s] already exists.", newNonPrimaryNetIdValue));
        }

        //check if the NetID already exists for an auxiliary program (also called program account)
        if (auxiliaryIdentifierRepository.identifierExistsForProgramAccount(newNonPrimaryNetIdValue,netIdTypeCode)){
            throw new IllegalStateException(format("netid [%s] already exists for a program.", newNonPrimaryNetIdValue));
        }

        final Person person = this.personService.findPersonByIdentifier(netIdTypeCode, primaryNetIdValue);
        if (person == null) {
            throw new IllegalArgumentException(format("The person with the provided primary netid [%s] does not exist", primaryNetIdValue));
        }
        Map<String, Identifier> primaryIds = person.getPrimaryIdentifiersByType();
        Identifier i = primaryIds.get(netIdTypeCode);
        //Candidate for NPE - which is not handeled here as it would be serious data error to not have a primary netid
        if (!i.getValue().equals(primaryNetIdValue)) {
            throw new IllegalArgumentException(format("The provided primary netid [%s] does not match the current primary netid", primaryNetIdValue));
        }

        final IdentifierType idType = this.referenceRepository.findIdentifierType(this.netIdTypeCode);
        final Identifier newNetId = person.addIdentifier(idType, newNonPrimaryNetIdValue);
        newNetId.setPrimary(false);
        return new GeneralServiceExecutionResult<Identifier>(newNetId);
    }

}
