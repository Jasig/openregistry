package org.openregistry.core.web;

import org.openregistry.core.domain.Role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import javax.servlet.http.HttpServletRequest;

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Controller
@RequestMapping("/addRole.htm")

public class PersonRegistryController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
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
    	System.out.println("Populating: setUpForm: ");

        //fudge for now...  necessary until data is in db
        String roleName = "SAKAI Access";
        String personId = "Alexander, Alex A." + " (ID: 111002002)";
        String title = "SAKAI User";
    	model.addAttribute("rolename",roleName);
        model.addAttribute("personid",personId);
        model.addAttribute("title",title);

        this.role.setStart(new Date());
        this.role.setEnd(new Date());
        this.role.addAddress();
        this.role.addPhone();
        this.role.addEmailAddress();
        model.addAttribute("role",role);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap model, @ModelAttribute("role") Role role, @ModelAttribute("addRoleHeading") String heading, BindingResult result, SessionStatus status) {
        logger.info("processSubmit in add role");
        System.out.println("processSubmit: add role: percentage: " + role.getPercentage());
        
        AnnotationValidator validator    = null;
        List messages = null;

        validator = new AnnotationValidatorImpl();
        
        messages = validator.validateObject(role,"group01");
        System.out.println("Person errors=" + messages.size()); // Should print 0
        System.out.println("Messages=" + messages);

        model.addAttribute("infoModel", "Role has been added.");
        model.addAttribute("addRoleHeading",heading);
		return "addRole";
	}
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(DateFormat.getDateInstance(), true));
   }

}