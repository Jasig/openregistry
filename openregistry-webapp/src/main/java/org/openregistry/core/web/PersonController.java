package org.openregistry.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.JpaPersonImpl;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PersonService personService;

    private MessageSource messageSource;

    private Class<? extends Person> personClass;

    @Autowired(required=true)
    public PersonController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

	@RequestMapping(method = RequestMethod.GET)
    public String addPersonSetUpForm(ModelMap model) {
    	logger.info("Populating: setUpForm: ");
        Person person = addPerson();
        model.addAttribute("person", person);
    	return "addPerson";
    }

    public void setPersonClass(final Class<? extends Person> personClass) {
        this.personClass = personClass;
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(ModelMap model, @ModelAttribute("person") Person person, BindingResult result, SessionStatus status ) {
        logger.info("processSubmit in add person");

        if (!result.hasErrors()){
            logger.info("calling validate and save person");
            ServiceExecutionResult res = personService.validateAndSavePerson(person);
            Errors errors = res.getErrors();
            if (errors == null) {
                model.addAttribute("infoModel", messageSource.getMessage("personAdded", null, null));
                status.setComplete();
            } else {
                result.addAllErrors(errors);
            }
        } else {
            logger.info("results had errors");
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
         try {
             final Person person = personClass.newInstance();
             person.addOfficialName();
             person.addPreferredName();
             person.addIdentifier();
             person.addIdentifier();
             return person;
         } catch (final Exception e) {
             throw new RuntimeException(e);
         }
    }
}
