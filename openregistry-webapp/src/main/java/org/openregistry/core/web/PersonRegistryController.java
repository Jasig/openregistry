package org.openregistry.core.web;

import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.RoleInfo;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openregistry.core.web.propertyeditors.PhoneEditor;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import com.sun.xml.internal.bind.v2.TODO;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Controller
@RequestMapping("/addRole.htm")

public class PersonRegistryController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonService personService;

    @Autowired(required=true)
    private ReferenceRepository referenceRepository;

	@RequestMapping(method = RequestMethod.GET)
    public String setUpForm(ModelMap model, @ModelAttribute("personKey") Long personKey, @ModelAttribute("roleInfoKey") Long roleInfoKey) {
    	logger.info("Populating: setUpForm: ");

        //fudge personKey
        personKey = new Long(1);
        Person person = personService.findPersonById(personKey);

        //fudge roleInfoKey
        roleInfoKey = new Long(1);
        RoleInfo roleInfo = referenceRepository.getRoleInfo(roleInfoKey);
        Role role = person.addRole(roleInfo);

        String personId = getPersonFullName(person) + " (ID: " + person.getId() + ")";
        model.addAttribute("personid",personId);

        //add reference data
        readReferenceData(model);

        model.addAttribute("role",role);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap model, @ModelAttribute("role") Role role, @ModelAttribute("personKey") Long personKey, BindingResult result, SessionStatus status) {
        logger.info("processSubmit in add role");

        Person person = personService.findPersonById(personKey);
        ServiceExecutionResult res = personService.validateAndSaveRoleForPerson(person,role);
        if (res.getErrorList().size() == 0){
            model.addAttribute("infoModel", "Role has been added.");
        } else {
            //show errors
        }

        model.addAttribute("personKey",personKey);
        
		return "addRole";
	}

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(df, true));
        binder.registerCustomEditor(String.class,"phone.number", new PhoneEditor());
    }

    protected void readReferenceData(ModelMap model){
        model.put("countryLookup", referenceRepository.getCountries());
        model.put("campusLookup", referenceRepository.getCampuses());
        model.put("departmentLookup", referenceRepository.getDepartments());
        model.put("sponsorLookup", referenceRepository.getPeople());
    }

    protected String getPersonFullName(Person person){
        String name = person.getOfficialName().toString();
        return name;
    }

}