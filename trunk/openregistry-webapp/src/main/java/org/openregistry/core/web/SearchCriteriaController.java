package org.openregistry.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.openregistry.core.service.SearchCriteria;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.service.MutableSearchCriteriaImpl;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Controller
@RequestMapping("/search.htm")
public class SearchCriteriaController {

    private final PersonService personService;

    @Autowired(required=true)
    public SearchCriteriaController(final PersonService personService) {
        this.personService = personService;
    }

    @InitBinder()
        public void initBinder(WebDataBinder binder) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
            binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        }

    @RequestMapping(method = RequestMethod.POST)
    // TODO replace MutableSearchCriteria with the appropriate class.
    public String processSubmit(@ModelAttribute("searchCriteria") final MutableSearchCriteriaImpl searchCriteria, final ModelMap modelMap) {
        final List<PersonMatch> matches = this.personService.searchForPersonBy(searchCriteria);

        modelMap.put("matches", matches);
        return "jsonView";
    }
}
