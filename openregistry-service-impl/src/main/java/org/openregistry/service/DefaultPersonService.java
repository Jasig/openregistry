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
        String serviceName = "PersonService.validateAndSaveRoleForPerson";
        Date executionDate = new Date();
        DefaultServiceExecutionResult executionResult = null;

        DataBinder db = new DataBinder(role, "role");
        Errors errors = db.getBindingResult();

        //Do the actual validation (both using annotations and plain code)
        //and accumulate any validation errors in the created Errors instance
        this.springMessageConverter.convertMessages(annotationValidator.validateObject(role), errors);
        roleValidator.validate(role, errors);
        if (!errors.hasErrors()) {
            this.personRepository.savePerson(person);
            executionResult = new DefaultServiceExecutionResult(serviceName, executionDate);
        }
        return executionResult == null ? new DefaultServiceExecutionResult(serviceName, executionDate, errors) : executionResult;
    }

    @Transactional
    public ServiceExecutionResult validateAndSavePerson(final Person person) {
        String serviceName = "PersonService.validateAndSaveRoleForPerson";
        Date executionDate = new Date();
        DefaultServiceExecutionResult executionResult = null;

        DataBinder db = new DataBinder(person, "person");
        Errors errors = db.getBindingResult();

        //Do the actual validation (both using annotations and plain code)
        //and accumulate any validation errors in the created Errors instance        
        this.springMessageConverter.convertMessages(annotationValidator.validateObject(person), errors);
        
        personValidator.validate(person, errors);
        if (!errors.hasErrors()) {
            // TODO Call to reconciliation code and save code.
            executionResult = new DefaultServiceExecutionResult(serviceName, executionDate);
        }

        return executionResult == null ? new DefaultServiceExecutionResult(serviceName, executionDate, errors) : executionResult;
    }


}
