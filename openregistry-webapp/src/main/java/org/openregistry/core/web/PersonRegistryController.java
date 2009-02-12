package org.openregistry.core.web;

import org.openregistry.core.domain.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openregistry.core.web.propertyeditors.*;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.service.DefaultServiceExecutionResultImpl;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Controller
@RequestMapping("/addRole.htm")
@SessionAttributes({"role","person"})
public class PersonRegistryController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final String ACTIVE_STATUS = "Active";
    protected final String TYPE_STATUS = "Status";
    protected final String TYPE_ADDRESS = "Address";
    protected final String TYPE_PHONE = "Phone";
    protected final String TYPE_EMAIL_ADDRESS = "EmailAddress";
    protected final String CAMPUS = "Campus";

    @Autowired
    private PersonService personService;

    private MessageSource messageSource;
    
    @Autowired(required=true)
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
        Role role = addRole(person, roleInfo);

        model.addAttribute("personDescription", getPersonDisplayName(person));
        model.addAttribute("role",role);
        model.addAttribute("person",person);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(ModelMap model, @ModelAttribute("person") Person person, @ModelAttribute("role") Role role, BindingResult result, SessionStatus status ) {
        logger.info("processSubmit in add role");

        if (!result.hasErrors()){
            ServiceExecutionResult res = personService.validateAndSaveRoleForPerson(person, role);

            Errors errors = ((DefaultServiceExecutionResultImpl)res).getErrors();
            if (!errors.hasErrors()){
                model.addAttribute("infoModel", messageSource.getMessage("roleAdded",null,null));
                status.setComplete();
            } else {
                result.addAllErrors(errors);
            }
        }

        model.addAttribute("personDescription", getPersonDisplayName(person));
        model.addAttribute("role",role);
        model.addAttribute("person",person);
		return "addRole";
	}

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(df, true));
        binder.registerCustomEditor(String.class,"phones.number", new PhoneEditor());
        binder.registerCustomEditor(String.class,"phones.areaCode", new PhoneEditor());
        binder.registerCustomEditor(String.class,"phones.countryCode", new PhoneEditor());
        binder.registerCustomEditor(Country.class,"addresses.country", new CountryEditor(referenceRepository));
        binder.registerCustomEditor(Region.class,"addresses.region", new RegionEditor(referenceRepository));
        binder.registerCustomEditor(Person.class,"sponsor", new SponsorEditor(referenceRepository));
        binder.registerCustomEditor(Department.class,"department", new DepartmentEditor(referenceRepository));
    }

    /**
     * Construct person information to display in heading.
     * @param person
     * @return
     */
    protected String getPersonDisplayName(Person person){
        String name = person.getOfficialName().toString()  + " (ID: " + person.getId() + ")";
        return name;
    }

    /**
     * Add and initialize new role.
     * @param person
     * @param roleInfo
     * @return role
     */
    protected Role addRole(Person person, RoleInfo roleInfo){
        Role role = person.addRole(roleInfo);
        role.setPersonStatus(referenceRepository.findType(TYPE_STATUS, ACTIVE_STATUS));
        EmailAddress emailAddress = role.addEmailAddress();
        emailAddress.setAddressType(referenceRepository.findType(TYPE_EMAIL_ADDRESS, CAMPUS));
        Phone phone = role.addPhone();
        phone.setPhoneType(referenceRepository.findType(TYPE_PHONE, CAMPUS));
        phone.setAddressType(referenceRepository.findType(TYPE_PHONE, CAMPUS));
        Address address = role.addAddress();
        address.setType(referenceRepository.findType(TYPE_ADDRESS, CAMPUS));
        
        //provide default values for start and end date of role
        Calendar cal = Calendar.getInstance();
        role.setStart(cal.getTime());
        cal.add(Calendar.MONTH, 6);
        role.setEnd(cal.getTime());
        return role;
    }

}