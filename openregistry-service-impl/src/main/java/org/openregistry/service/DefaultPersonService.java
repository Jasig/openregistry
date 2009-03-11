package org.openregistry.service;

import org.openregistry.core.service.*;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.service.validator.RoleValidator;
import org.openregistry.service.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.ValidationMessage;

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

    @Autowired(required = true)
    private AnnotationValidator annotationValidator;

    @Autowired(required=true)
    private RoleValidator roleValidator;

    @Autowired(required=true)
    private PersonValidator personValidator;

    @Autowired(required=true)
    private Reconciler reconciler;

    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }

    @Transactional
    public ServiceExecutionResult validateAndSaveRoleForPerson(final Person person, final Role role) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        // final Errors errors = new DataBinder(role, "role").getBindingResult();

        final List<ValidationError> validationErrors = validateAndConvert(role);
        // this.roleValidator.validate(role, errors);

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, role, validationErrors);
        }

        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName, role);
    }

    @Transactional
    public ServiceExecutionResult validateAndSavePerson(final Person person) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        // final Errors errors = new DataBinder(person, "person").getBindingResult();

        final List<ValidationError> validationErrors = validateAndConvert(person);

        //this.personValidator.validate(person, errors);

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, person, validationErrors);
        }

        // TODO Call to reconciliation code and save code.
        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName, person);
    }

    @Transactional
    public ServiceExecutionResult addPerson(final SorPerson person, final ReconciliationResult oldReconciliationResult) {
        final List<ValidationError> validationErrors = validateAndConvert(person);
        final String serviceName = "PersonService.addPerson";

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, person, validationErrors);
        }


        if (oldReconciliationResult != null) {
            final ReconciliationResult result = this.reconciler.reconcile(person);

            if (result.getReconciliationType() == ReconciliationResult.ReconciliationType.NONE) {
                this.personRepository.saveSorPerson(person);
                // TODO kick off the process to "calculate"
                return new DefaultServiceExecutionResult(serviceName, person);
            }

            return new DefaultServiceExecutionResult(serviceName, person, result);
        }

        this.personRepository.saveSorPerson(person);
        // TODO kick off process to "calculate"
        return new DefaultServiceExecutionResult(serviceName, person);
    }

    protected List<ValidationError> validateAndConvert(final Object object) {
        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object);
        return convertToValidationErrors(validationMessages);
    }

    protected List<ValidationError> convertToValidationErrors(final List<ValidationMessage> messages) {
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        for (final ValidationMessage message : messages) {
            validationErrors.add(new JaValidValidationError(message));
        }

        return validationErrors;
    }
}
