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

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.openregistry.core.web.propertyeditors.*;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Controller
@RequestMapping("/addRole.htm")
@SessionAttributes({"role", "person"})
public final class RoleController extends AbstractLocalizationController {

    protected final String ACTIVE_STATUS = "Active";
    protected final String TYPE_STATUS = "Status";
    protected final String TYPE_ADDRESS = "Address";
    protected final String TYPE_PHONE = "Phone";
    protected final String TYPE_EMAIL_ADDRESS = "EmailAddress";
    protected final String CAMPUS = "Campus";

    @Autowired(required=true)
    private PersonService personService;

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    @ModelAttribute("countryLookup")
    public List<Country> populateCountryLookup() {
        return this.referenceRepository.getCountries();
    }

    @ModelAttribute("sponsorLookup")
    public List<Person> populateSponsorLookup() {
        return this.referenceRepository.getPeople();
    }

	@RequestMapping(method = RequestMethod.GET)
    public String addRoleSetUpForm(final ModelMap model, @RequestParam("personKey") final Long personKey, @RequestParam("roleInfoKey") final Long roleInfoKey) {
    	logger.info("Populating: setUpForm: ");

        final Person person = this.personService.findPersonById(personKey);
        final RoleInfo roleInfo = this.referenceRepository.getRoleInfo(roleInfoKey);
        final Role role = addRole(person, roleInfo);

        model.addAttribute("role", role);
        model.addAttribute("person", person);
    	return "addRole";
    }

	@RequestMapping(method = RequestMethod.POST)
	public String addRoleProcessSubmit(final ModelMap model, @ModelAttribute("person") final Person person, @ModelAttribute("role") final Role role, final BindingResult result, final SessionStatus status ) {
        logger.info("processSubmit in add role");

        if (!result.hasErrors()){
            final ServiceExecutionResult serviceExecutionResult = this.personService.validateAndSaveRoleForPerson(person, role);
            // TODO disabled right now because we need to get this off the interface
            final Errors errors = null;
                    // = res.getErrors();
            if (errors == null) {
                model.addAttribute("infoModel", getMessageSource().getMessage("roleAdded", null, null));
                status.setComplete();
            } else {
                result.addAllErrors(errors);
            }
        }

        model.addAttribute("role", role);
        model.addAttribute("person", person);
		return "addRole";
	}

    @InitBinder
    protected void initDataBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, createNewCustomDateEditor());
        binder.registerCustomEditor(String.class, "phones.number", new PhoneEditor());
        binder.registerCustomEditor(String.class, "phones.areaCode", new PhoneEditor());
        binder.registerCustomEditor(String.class, "phones.countryCode", new PhoneEditor());
        binder.registerCustomEditor(Country.class, new CountryEditor(referenceRepository));
        binder.registerCustomEditor(Region.class, new RegionEditor(referenceRepository));
        binder.registerCustomEditor(Person.class, new SponsorEditor(referenceRepository));
    }

    /**
     * Add and initialize new role.
     * @param person
     * @param roleInfo
     * @return role
     */
    protected Role addRole(final Person person, final RoleInfo roleInfo){
        final Role role = person.addRole(roleInfo);
        role.setPersonStatus(referenceRepository.findType(TYPE_STATUS, ACTIVE_STATUS));
        final EmailAddress emailAddress = role.addEmailAddress();
        emailAddress.setAddressType(referenceRepository.findType(TYPE_EMAIL_ADDRESS, CAMPUS));
        final Phone phone = role.addPhone();
        phone.setPhoneType(referenceRepository.findType(TYPE_PHONE, CAMPUS));
        phone.setAddressType(referenceRepository.findType(TYPE_PHONE, CAMPUS));
        final Address address = role.addAddress();
        address.setType(referenceRepository.findType(TYPE_ADDRESS, CAMPUS));

        //provide default values for start and end date of role
        final Calendar cal = Calendar.getInstance();
        role.setStart(cal.getTime());
        cal.add(Calendar.MONTH, 6);
        role.setEnd(cal.getTime());
        return role;
    }


}