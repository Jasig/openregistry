package org.openregistry.service;

import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.service.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.transaction.annotation.Transactional;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidationMessage;
import org.javalid.external.spring.SpringMessageConverter;
import java.util.List;

/**
 * Default implementation of the {@link PersonService}.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Service
public class DefaultPersonServiceImpl implements PersonService {

    @Autowired(required=true)
    private PersonRepository personRepository;

    @Autowired(required=true)
    private AnnotationValidator annotationValidator;

    @Autowired(required=true)
    private RoleValidator roleValidator;

    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }

    @Transactional
    public ServiceExecutionResult validateAndSaveRoleForPerson(final Person person, final Role role) {

        DefaultServiceExecutionResultImpl res = new DefaultServiceExecutionResultImpl();

        List<ValidationMessage> messages = annotationValidator.validateObject(role);

        DataBinder db = new DataBinder(role, "role");
        Errors errors = db.getBindingResult();

        if (messages.size() > 0){
            new SpringMessageConverter().convertMessages((List<ValidationMessage>) messages, errors);
            res.setErrors(errors);
            return res;
        }

        roleValidator.validate(role, errors);

        if (!errors.hasErrors()){
            this.personRepository.savePerson(person);
        }

        res.setErrors(errors);
        return res;
    }

}
