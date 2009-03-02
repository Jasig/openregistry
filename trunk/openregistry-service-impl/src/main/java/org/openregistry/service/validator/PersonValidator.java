package org.openregistry.service.validator;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.springframework.validation.Errors;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 25, 2009
 * Time: 11:35:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersonValidator {

    public void validate(Person person, Errors errors) {
        //tried to validate using javalid but had problems due to nested name class.
        validateOfficialName(person, errors);
    }

    /**
     * Official Name must have a first and last name.
     * @param person
     * @param errors
     */
    void validateOfficialName(Person person, Errors errors){
        Name officialName = person.getOfficialName();
        String givenName = officialName.getGiven();
        String familyName = officialName.getFamily();

		if (!StringUtils.hasText(givenName)) {
		    errors.rejectValue("officialName","firstNameRequiredMsg");
		}
        if (!StringUtils.hasText(familyName)) {
		    errors.rejectValue("officialName","lastNameRequiredMsg");
		}
    }

}
