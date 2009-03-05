package org.openregistry.service;

import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.service.validator.RoleValidator;
import org.openregistry.service.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.DataBinder;
import org.springframework.transaction.annotation.Transactional;
import org.javalid.core.AnnotationValidator;
import org.javalid.external.spring.SpringMessageConverter;
import java.util.Date;

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

    //The converter is thread safe
    private SpringMessageConverter springMessageConverter = new SpringMessageConverter();

    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }

    @Transactional
    public ServiceExecutionResult validateAndSaveRoleForPerson(final Person person, final Role role) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        final Errors errors = new DataBinder(role, "role").getBindingResult();

        this.springMessageConverter.convertMessages(this.annotationValidator.validateObject(role), errors);
        this.roleValidator.validate(role, errors);

        if (errors.hasErrors()) {
            return new DefaultServiceExecutionResult(serviceName, errors);
        }

        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName);
    }

    @Transactional
    public ServiceExecutionResult validateAndSavePerson(final Person person) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        final Errors errors = new DataBinder(person, "person").getBindingResult();

        this.springMessageConverter.convertMessages(this.annotationValidator.validateObject(person), errors);
        this.personValidator.validate(person, errors);

        if (errors.hasErrors()) {
            return new DefaultServiceExecutionResult(serviceName, errors);
        }

        // TODO Call to reconciliation code and save code.
        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName);
    }
}
