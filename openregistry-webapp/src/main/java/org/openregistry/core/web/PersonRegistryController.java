package org.openregistry.core.web;

import org.openregistry.core.domain.*;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openregistry.core.web.propertyeditors.*;
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
@SessionAttributes({"role","personKey"})
public class PersonRegistryController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonService personService;

    private MessageSource messageSource;
    
    @Autowired
    public PersonRegistryController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired(required=true)
    private ReferenceRepository referenceRepository;

    @ModelAttribute("countryLookup")
    public List<Country> populateCountryLookup() {
        return this.referenceRepository.getCountries();
    }

    @ModelAttribute("campusLookup")
    public List<Campus> populateCampusLookup() {
        return this.referenceRepository.getCampuses();
    }

    @ModelAttribute("departmentLookup")
    public List<Department> populateDepartmentLookup() {
        return this.referenceRepository.getDepartments();
    }

    @ModelAttribute("sponsorLookup")
    public List<Person> populateSponsorLookup() {
        return this.referenceRepository.getPeople();
    }


	@RequestMapping(method = RequestMethod.GET)
    public String addRoleSetUpForm(ModelMap model, @RequestParam("personKey")String personKey, @RequestParam("roleInfoKey")String roleInfoKey) {
    	logger.info("Populating: setUpForm: ");

        Person person = personService.findPersonById(new Long(personKey));
        RoleInfo roleInfo = referenceRepository.getRoleInfo(new Long(roleInfoKey));
        Role role = person.addRole(roleInfo);
        role.addEmailAddress();
        role.addPhone();
        role.addAddress();
        Calendar cal = Calendar.getInstance();
        role.setStart(cal.getTime());
        cal.add(Calendar.MONTH, 6);
        role.setEnd(cal.getTime());

        model.addAttribute("person",person);
        model.addAttribute("personDescription", getPersonDisplayName(person));
        model.addAttribute("affiliationTypeDescription", role.getAffiliationType().getDescription());
        model.addAttribute("title", role.getTitle());
        model.addAttribute("role",role);
        model.addAttribute("personKey", personKey);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(ModelMap model, @ModelAttribute("personKey") String personKey, @ModelAttribute("role") Role role, BindingResult result, SessionStatus status ) {
        logger.info("processSubmit in add role");

        Person person = personService.findPersonById(new Long(personKey));

        ServiceExecutionResult res = personService.validateAndSaveRoleForPerson(person,role);
        List errorList = res.getErrorList();
        if (errorList.size() == 0 && !result.hasErrors()){
            model.addAttribute("infoModel", "Role has been added.");
            status.setComplete();
        } else {
            //show errors
            for (int i=0; i < errorList.size(); i++){
                ValidationMessage validationMsg = (ValidationMessage)errorList.get(i);
                result.reject( validationMsg.getMessage(),validationMsg.getPath());
            }
        }

        model.addAttribute("role",role);
        model.addAttribute("personKey", personKey);
		return "addRole";
	}

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(df, true));
        binder.registerCustomEditor(String.class,"phone.number", new PhoneEditor());
        binder.registerCustomEditor(Country.class,"addresses.country", new CountryEditor(referenceRepository));
        binder.registerCustomEditor(Region.class,"addresses.region", new RegionEditor(referenceRepository));
        binder.registerCustomEditor(Person.class,"sponsor", new SponsorEditor(referenceRepository));
        binder.registerCustomEditor(Department.class,"department", new DepartmentEditor(referenceRepository));
    }

    protected String getPersonDisplayName(Person person){
        String name = person.getOfficialName().toString()  + " (ID: " + person.getId() + ")";
        return name;
    }

}