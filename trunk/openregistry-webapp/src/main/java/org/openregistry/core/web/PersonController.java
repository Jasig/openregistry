package org.openregistry.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 25, 2009
 * Time: 9:24:00 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/addPerson.htm")
@SessionAttributes("person")
public final class PersonController extends AbstractLocalizationController {

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required=true)
    private ObjectFactory<Person> personFactory;

	@RequestMapping(method = RequestMethod.GET)
    public String addPersonSetUpForm(final ModelMap model, final ServletRequest request) {
    	logger.info("Populating: setUpForm: ");
        model.addAttribute("person", createPerson());
    	return "addPerson";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(final ModelMap model, @ModelAttribute("person") final Person person, final BindingResult result, final SessionStatus status ) {
        logger.info("processSubmit in add person");

        if (!result.hasErrors()){
            logger.info("calling validate and save person");

            // TODO temporarily disabled until we fix the design of the API --sb
            final Errors errors = null;
            this.personService.validateAndSavePerson(person);

            if (errors == null) {
                model.addAttribute("infoModel", getMessageSource().getMessage("personAdded", null, null));
                status.setComplete();
            } else {
                result.addAllErrors(errors);
            }
        }

        model.addAttribute("person", person);
		return "addPerson";
	}

    @InitBinder
    protected void initDataBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, createNewCustomDateEditor());
    }

     /**
     * Add and initialize new person.
     * @return person
     */
    protected Person createPerson(){
        final Person person = personFactory.getObject();
        person.addOfficialName();
        person.addPreferredName();
        person.addIdentifier();
        person.addIdentifier();
        return person;
    }
}
