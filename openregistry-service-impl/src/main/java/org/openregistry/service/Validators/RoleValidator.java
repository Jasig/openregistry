package org.openregistry.service.validators;

import org.springframework.validation.Errors;
import org.openregistry.core.domain.Role;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 10, 2009
 * Time: 1:27:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoleValidator {

	public void validate(Role role, Errors errors) {

        validateStartEndDates(role, errors);
        validateAddresses(role, errors);

	}

    /**
     * Role start date must be earlier than role end date
     * @param role
     * @param errors
     */
    void validateStartEndDates(Role role, Errors errors){
        Date startDate = role.getStart();
        Date endDate = role.getEnd();

		if (startDate != null && endDate != null) {
            int difference = startDate.compareTo(endDate);
            if (difference >= 0)
			    errors.rejectValue("start","startDateEndDateCompareMsg");
		}
    }

    /**
     *  If any address component is specified then all required address components must
     *  be specified.
     * @param role
     * @param errors
     */
    void validateAddresses(Role role, Errors errors){

    }

}
