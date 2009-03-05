package org.openregistry.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class PersonController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required=true)
    private ObjectFactory<Person> personFactory;

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    private MessageSource messageSource;

    @Autowired(required=true)
    public PersonController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	@RequestMapping(method = RequestMethod.GET)
    public String addPersonSetUpForm(ModelMap model, ServletRequest request) {
    	logger.info("Populating: setUpForm: ");
        Person person = addPerson();
        model.addAttribute("person", person);
    	return "addPerson";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(ModelMap model, @ModelAttribute("person") Person person, BindingResult result, SessionStatus status ) {
        logger.info("processSubmit in add person");

        if (!result.hasErrors()){
            logger.info("calling validate and save person");
            logger.info("oficialName: "+ person.getOfficialName().toString());

            // TODO temporarily disabled until we fix the design of the API --sb
            final Errors errors = null;
            //  = personService.validateAndSavePerson(person).getErrors();

            if (errors == null) {
                model.addAttribute("infoModel", messageSource.getMessage("personAdded", null, null));
                status.setComplete();
            } else {
                result.addAllErrors(errors);
            }
        }

        model.addAttribute("person", person);
		return "addPerson";
	}

    @InitBinder
    protected void initDataBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(df, true));
    }

     /**
     * Add and initialize new person.
     * @return person
     */
    protected Person addPerson(){
        final Person person = personFactory.getObject();
        person.addOfficialName();
        person.addPreferredName();
        person.addIdentifier();
        person.addIdentifier();
        return person;
    }
}
