package org.openregistry.core.web;

import org.openregistry.core.domain.Person;
import org.openregistry.core.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.Date;


@Controller
@RequestMapping("/addRole.htm")

public class PersonRegistryController {

	protected final Log logger = LogFactory.getLog(getClass());

    @Autowired(required=true)
    private PersonService personService;
	
	@RequestMapping(method = RequestMethod.GET)
    public String setUpForm(final ModelMap model) {
    	logger.info("Populating: setUpForm: ");
        final Person person = this.personService.findPersonById(Long.parseLong("1"));
    	// TODO commented out so it compiles String infoValue = "Add Role: "+ person.getRoleName()+ " To: " + person.getFullName() + " ID: " + person.getId();
    	// model.addAttribute("addRoleHeading",infoValue);
    	model.addAttribute("person",person);
    	return "addRole";
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(final ModelMap model, @ModelAttribute("person") final Person person, final BindingResult result, final SessionStatus status) {				
		// logger.info("processSubmit: add role: email: " + person.getEmail());
		model.addAttribute("infoModel", "Role has been added.");
		return "addRole";
	}

}