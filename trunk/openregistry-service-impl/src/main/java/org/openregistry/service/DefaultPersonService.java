package org.openregistry.service;

import org.openregistry.core.service.*;
import org.openregistry.core.service.identifier.IdentifierAssigner;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.FieldMatch;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.PojoPersonImpl;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.service.reconciliation.PersonMatchImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.ValidationMessage;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the {@link PersonService}.
 *
 * @author Scott Battaglia
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 */
@Service("personService")
public class DefaultPersonService implements PersonService {

    @Autowired(required = true)
    private PersonRepository personRepository;

    @Autowired(required = false)
    private AnnotationValidator<Object> annotationValidator = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    @Autowired(required=true)
    private Reconciler reconciler;

    @Autowired(required=true)
    private ObjectFactory<Person> personObjectFactory;

    @Autowired(required=false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Autowired(required=true)
    private IdentifierGenerator identifierGenerator;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }

    @Transactional
    public boolean delete(final Person person, final DeletionCriteria deletionCriteria) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional
    public ServiceExecutionResult validateAndSaveRoleForPerson(final Person person, final Role role) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        final List<ValidationError> validationErrors = validateAndConvert(role);

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, role, validationErrors);
        }

        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName, role);
    }

    @Transactional
    public ServiceExecutionResult addPerson(final PersonSearch personSearch, final ReconciliationResult oldReconciliationResult) {
        logger.info("In personService.addPerson.");
        logger.info("In personService.addPerson: entered following for gender: "+ personSearch.getPerson().getGender());
        final List<ValidationError> validationErrors = validateAndConvert(personSearch);
        final String serviceName = "PersonService.addPerson";

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, personSearch, validationErrors);
        }


        if (oldReconciliationResult == null) {
            final ReconciliationResult result = this.reconciler.reconcile(personSearch);

            if (result.getReconciliationType() == ReconciliationResult.ReconciliationType.NONE) {
                return new DefaultServiceExecutionResult(serviceName, magic(personSearch));
            }
            logger.info("In personService.addPerson: reconciliation result: "+ result.getReconciliationType().toString());
            return new DefaultServiceExecutionResult(serviceName, personSearch, result);
        }

        return new DefaultServiceExecutionResult(serviceName, magic(personSearch));
    }

    @Transactional
    public List<PersonMatch> searchForPersonBy(final SearchCriteria searchCriteria) {
        logger.info("searchForPersonBy was called.");
        final List<PersonMatch> personMatches = new ArrayList<PersonMatch>();
        final PersonMatch p = new PersonMatchImpl(new PojoPersonImpl(), 100, new ArrayList<FieldMatch>());
        p.getPerson().getPreferredName().setPrefix("Mr.");
        p.getPerson().getPreferredName().setGiven("Scott");
        p.getPerson().getPreferredName().setFamily("Battaglia");
        final PersonMatch p1 = new PersonMatchImpl(new PojoPersonImpl(), 50, new ArrayList<FieldMatch>());
        p1.getPerson().getPreferredName().setPrefix("Mr.");
        p1.getPerson().getPreferredName().setGiven("David");
        p1.getPerson().getPreferredName().setFamily("Steiner");

        personMatches.add(p);
        if (!"Scott".equals(searchCriteria.getGivenName())) {
            personMatches.add(p1);
        }

        // TODO actually get some data here.
        return personMatches;
    }

    /**
     * Validates the object using the JaValid annotation framework and then converts the errors into the OpenRegistry API for errors.
     *
     * @param object the object to check
     * @return the list of validation errors.  CANNOT be NULL.  Can be empty.
     */
    protected List<ValidationError> validateAndConvert(final Object object) {
        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object, "1", "", true, -1);
//        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object);
        logger.info("In personService.validateAndConvert: size of messages: "+ validationMessages.size());
        return convertToValidationErrors(validationMessages);
    }

    /**
     * Converts the validation errors from JaValid into the OpenRegistry API validation errors.
     *
     * @param messages the JaValid messages to convert.
     * @return the list of validation errors.  CANNOT be null.  CAN be empty.
     */
    protected List<ValidationError> convertToValidationErrors(final List<ValidationMessage> messages) {
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        for (final ValidationMessage message : messages) {
            validationErrors.add(new JaValidValidationError(message));
        }

        return validationErrors;
    }

    /**
     * Current workflow for converting an SorPerson into the actual Person.
     *
     * @param personSearch the original search criteria.
     * @return the newly saved Person.
     */
    protected Person magic(final PersonSearch personSearch) {
        final SorPerson sorPerson = personSearch.getPerson();

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }

        // Save Sor Person
        this.personRepository.saveSorPerson(sorPerson);

        // Construct actual person from Sor Information
        final Person person = personObjectFactory.getObject();
        person.setDateOfBirth(sorPerson.getDateOfBirth());
        person.setGender(sorPerson.getGender());
        final Name name = person.addOfficialName();

        // There should only be one at this point.
        // TODO generalize this to all names
        final Name sorName = sorPerson.getNames().iterator().next();

        name.setFamily(sorName.getFamily());
        name.setGiven(sorName.getGiven());
        name.setMiddle(sorName.getMiddle());
        name.setPrefix(sorName.getPrefix());
        name.setSuffix(sorName.getSuffix());

        // Assign identifiers, including SSN from the SoR Person
        // TODO set SSN as an Identifier, it should be one of the IdentifierAssigners
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, person);
        }

        // Save into the repository
        this.personRepository.savePerson(person);
        return person;
    }
}
