package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.GeneralServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

import javax.inject.Inject;
import java.util.Map;

/**
 * @since 1.0
 */
@Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultNetIdManagementService implements NetIdManagementService {

    private PersonService personService;

    private ReferenceRepository referenceRepository;

    private String netIdTypeCode;

    @Inject
    public DefaultNetIdManagementService(PersonService personService, ReferenceRepository referenceRepository, String netIdTypeCode) {
        this.personService = personService;
        this.referenceRepository = referenceRepository;
        this.netIdTypeCode = netIdTypeCode;
    }

    @Override
    public ServiceExecutionResult<Identifier> changeNetId(String oldNetIdValue, String newNetIdValue, boolean primary) throws IllegalArgumentException, IllegalStateException {
        if((this.personService.findPersonByIdentifier(netIdTypeCode, newNetIdValue) != null)) {
            throw new IllegalStateException(format("The person with the proposed new netid [%s] already exists.", newNetIdValue));
        }
        final Person person = this.personService.findPersonByIdentifier(netIdTypeCode, oldNetIdValue);
        if(person == null) {
            throw new IllegalArgumentException(format("The person with the provided netid [%s] does not exist", oldNetIdValue));
        }
        final IdentifierType idType = this.referenceRepository.findIdentifierType(this.netIdTypeCode);
        final Identifier newNetId = person.addIdentifier(idType, newNetIdValue);
        if(primary) {
            newNetId.setPrimary(true);
            Map<String, Identifier> primaryIds = person.getPrimaryIdentifiersByType();
            Identifier i = primaryIds.get(netIdTypeCode);
            if(i != null) {
                i.setPrimary(false);
            }
        }
        else {
            newNetId.setPrimary(false);
        }
        return new GeneralServiceExecutionResult<Identifier>(newNetId);
    }
}
