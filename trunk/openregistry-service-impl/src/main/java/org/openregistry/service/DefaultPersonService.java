package org.openregistry.service;

import org.openregistry.core.service.*;
import org.openregistry.core.service.identifier.IdentifierAssigner;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.repository.PersonRepository;
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

/**
 * Default implementation of the {@link PersonService}.
 *
 * @author Scott Battaglia
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 */
@Service
public class DefaultPersonService implements PersonService {

    @Autowired(required = true)
    private PersonRepository personRepository;

    @Autowired(required = false)
    private AnnotationValidator annotationValidator = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    @Autowired(required=true)
    private Reconciler reconciler;

    @Autowired(required=true)
    private ObjectFactory<Person> personObjectFactory;

    @Autowired(required=false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Autowired(required=true)
    private IdentifierGenerator identifierGenerator;

    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
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
    public ServiceExecutionResult validateAndSavePerson(final Person person) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        final List<ValidationError> validationErrors = validateAndConvert(person);

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, person, validationErrors);
        }

        // TODO Call to reconciliation code and save code.
        //this.personRepository.savePerson(person);
        this.personRepository.addPerson(person);
        return new DefaultServiceExecutionResult(serviceName, person);
    }

    @Transactional
    public ServiceExecutionResult addPerson(final PersonSearch personSearch, final ReconciliationResult oldReconciliationResult) {
        final List<ValidationError> validationErrors = validateAndConvert(personSearch);
        final String serviceName = "PersonService.addPerson";

        // TODO some SorIdentifier needs to be assigned (i.e. web-ui)

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, personSearch, validationErrors);
        }


        if (oldReconciliationResult != null) {
            final ReconciliationResult result = this.reconciler.reconcile(personSearch);

            if (result.getReconciliationType() == ReconciliationResult.ReconciliationType.NONE) {
                return new DefaultServiceExecutionResult(serviceName, magic(personSearch));
            }

            return new DefaultServiceExecutionResult(serviceName, personSearch, result);
        }

        return new DefaultServiceExecutionResult(serviceName, magic(personSearch));
    }

    /**
     * Validates the object using the JaValid annotation framework and then converts the errors into the OpenRegistry API for errors.
     *
     * @param object the object to check
     * @return the list of validation errors.  CANNOT be NULL.  Can be empty.
     */
    protected List<ValidationError> validateAndConvert(final Object object) {
        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object, null, "", true, -1);
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
