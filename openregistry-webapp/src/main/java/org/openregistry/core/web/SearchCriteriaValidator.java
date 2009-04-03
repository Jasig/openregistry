package org.openregistry.core.web;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Component;
import org.javalid.external.spring.SpringValidator;
import org.javalid.external.spring.SpringMessageConverter;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidatorParams;
import org.javalid.core.ValidationMessage;
import org.javalid.core.resource.MessageCodeResourceBundleResolverImpl;
import org.javalid.core.config.JvConfiguration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

/**
 * Bridges the Spring Validation code with the JaValid validation code.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component("searchCriteriaValidator")
public final class SearchCriteriaValidator implements Validator {

//    private final SpringValidator validator;

    private final SpringMessageConverter springMessageConverter = new SpringMessageConverter();

    private AnnotationValidatorImpl v = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    public SearchCriteriaValidator() {
        final AnnotationValidatorImpl v = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);
//         v.setMessageCodeResolver(new MessageCodeResourceBundleResolverImpl(new String[] {"org/javalid/core/validator/jv_messages","messages"}));
//        this.validator = new SpringValidator();

//        this.validator.setValidator(v);
//        this.validator.setValidatorMap(new ConcurrentHashMap<String, ValidatorParams>());
    }

    public final void validate(final Object o, final Errors errors) {
        final List<ValidationMessage> validationMessageList = this.v.validateObject(o, "1", "", true, 2);

        for (final ValidationMessage m : validationMessageList) {
            System.out.println(m.toString());
        }

        errors.rejectValue("gender", "testValue");

        this.springMessageConverter.convertMessages(validationMessageList, errors);
    }

    public boolean supports(final Class aClass) {
        return true;
    }
}
