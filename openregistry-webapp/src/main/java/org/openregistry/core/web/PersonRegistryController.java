package org.openregistry.core.web;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.jpa.*;

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

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidationMessage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/addRole.htm")

public class PersonRegistryController {

	protected final Log logger = LogFactory.getLog(getClass());
    private Role role;

   @Autowired
    public PersonRegistryController(Role role) {
        this.role = role;
    }

    @ModelAttribute
    public Role setupModelAttribute() {
        return this.role;
    }
    	
	@RequestMapping(method = RequestMethod.GET)
    public String setUpForm(ModelMap model) {
    	logger.info("Populating: setUpForm: ");

        //fudge for now...
    	String infoValue = "Add Role: "+ "SAKAI User"+ " To: " + "Alexander, Alex A." + " ID: " + "111002002";
    	model.addAttribute("addRoleHeading",infoValue);

        this.role.getActive().setStart(new Date());
        this.role.getActive().setEnd(new Date());
        this.role.addAddress();
        this.role.addPhone();
        this.role.addEmailAddress();
        model.addAttribute("role",role);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap model, @ModelAttribute("role") Role role, BindingResult result, SessionStatus status) {
        logger.info("processSubmit in add role");
        logger.info("processSubmit: add role: percentage: " + role.getPercentage());
        

        AnnotationValidator validator    = null;
        List messages = null;

        validator = new AnnotationValidatorImpl();
        
        //messages = validator.validateObject(person,"group01");
        //System.out.println("Person errors=" + messages.size()); // Should print 0
        //System.out.println("Messages=" + messages);

        model.addAttribute("infoModel", "Role has been added.");
		return "addRole";
	}

}