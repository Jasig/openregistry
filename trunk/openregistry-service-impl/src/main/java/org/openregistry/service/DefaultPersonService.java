package org.openregistry.service;

import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.DataBinder;
import org.springframework.transaction.annotation.Transactional;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.ValidationMessage;
import org.javalid.external.spring.SpringMessageConverter;

import java.util.List;
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
        additionalNonAnnotationBasedValidation(role, errors);
        if (!errors.hasErrors()) {
            this.personRepository.savePerson(person);
            executionResult = new DefaultServiceExecutionResult(serviceName, executionDate);
        }
        return executionResult == null ? new DefaultServiceExecutionResult(serviceName, executionDate, errors) : executionResult;
    }

    /* This should be temporary until we figure out how to validate this using Javalid */
    private void additionalNonAnnotationBasedValidation(Role role, Errors errors) {
        validateStartEndDates(role, errors);
        validateAddresses(role, errors);
    }

    /*
     * Role start date must be earlier than role end date
     */
    private void validateStartEndDates(Role role, Errors errors) {
        Date startDate = role.getStart();
        Date endDate = role.getEnd();
        if (startDate != null && endDate != null) {
            int difference = startDate.compareTo(endDate);
            if (difference >= 0) {
                errors.rejectValue("start", "startDateEndDateCompareMsg");
            }
        }
    }

    /*
     *  If any address component is specified then all required address components must
     *  be specified.
     */
    private void validateAddresses(Role role, Errors errors) {
        //Noop TODO: add implementation
    }

}
