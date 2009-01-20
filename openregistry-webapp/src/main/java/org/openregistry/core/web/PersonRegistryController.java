package org.openregistry.core.web;

import org.openregistry.core.domain.Person;
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
	
	@RequestMapping(method = RequestMethod.GET)
    public String setUpForm(ModelMap model) {
    	logger.info("Populating: setUpForm: ");
    	Person person = new Person();
    	person.setName("Alexander, Alex A.");
    	person.setRoleName("SAKAI User");
    	person.setPersonId("111002002");
    	String infoValue = "Add Role: "+ person.getRoleName()+ " To: " + person.getName() + " ID: " + person.getPersonId();
    	model.addAttribute("addRoleHeading",infoValue);
    	model.addAttribute("person",person);
    	return "addRole";
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap model, @ModelAttribute("person") Person person, BindingResult result, SessionStatus status) {				
		logger.info("processSubmit: add role: email: " + person.getEmail());
		model.addAttribute("infoModel", "Role has been added.");
		return "addRole";
	}

}