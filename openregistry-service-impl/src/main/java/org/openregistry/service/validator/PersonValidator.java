package org.openregistry.service.validator;

import org.openregistry.core.domain.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 25, 2009
 * Time: 11:35:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersonValidator {

    public void validate(final Person person, final Errors errors) {

        // check official name
        // TODO we really want to ensure that one item on the name is set, not these specifically --sb
        ValidationUtils.rejectIfEmpty(errors, "officialName.given", "firstNameRequiredMsg");
        ValidationUtils.rejectIfEmpty(errors, "officialName.family", "lastNameRequiredMsg");
    }
}
