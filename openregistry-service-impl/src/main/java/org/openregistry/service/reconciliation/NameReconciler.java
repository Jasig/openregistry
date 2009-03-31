package org.openregistry.service.reconciliation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.service.reconciliation.FieldMatch;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.ReconciliationResult.ReconciliationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Very simplistic Name Reconciler.  Just for testing purposes.
 * 
 * @author steiner
 * @version $Revision$ $Date$
 */
@Component
public final class NameReconciler implements Reconciler {

	@Autowired(required = true)
	private PersonRepository personRepository;
	
	private List<PersonMatch> exactMatches = new ArrayList<PersonMatch>();
	private List<PersonMatch> partialMatches = new ArrayList<PersonMatch>();
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/* 
	 * @see org.openregistry.core.service.reconciliation.Reconciler#reconcile(org.openregistry.core.domain.sor.PersonSearch)
	 */
	public ReconciliationResult reconcile(PersonSearch personSearch) {
		Set<? extends Name> names = personSearch.getPerson().getNames();  // TODO deal with multiple names properly
		logger.info("Reconcile: found " + names.size() + " names for " + personSearch.getPerson().toString());
		for(Name name: names) {
			List<Person> matches = this.personRepository.findByFamilyName(name.getFamily());
			logger.info("Reconcile: found " + matches.size() + " possible matches.");
			for(Person match: matches) {
				if (name.getGiven().equals(match.getOfficialName().getGiven())) {  // TODO use all names
					logger.info("Reconcile: found exact match " + match.getOfficialName().getGiven() + " " + match.getOfficialName().getFamily());
					exactMatches.add(new PersonMatchImpl(match, 100, Collections.<FieldMatch>emptyList()));
				} else {
					logger.info("Reconcile: found partial match " + match.getOfficialName().getGiven() + " " + match.getOfficialName().getFamily());
					partialMatches.add(new PersonMatchImpl(match, 50, Collections.<FieldMatch>emptyList()));
				}
			}
		}
		if (exactMatches.isEmpty() && partialMatches.isEmpty()) {
			logger.info("Reconcile: returning NONE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			return new ReconciliationResultImpl(ReconciliationType.NONE, Collections.<PersonMatch>emptyList());
		}
		if (! partialMatches.isEmpty()) {
		    List<PersonMatch> matches = null;
			matches.addAll(exactMatches);
			matches.addAll(partialMatches);
			logger.info("Reconcile: returning MAYBE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, matches);
		} else if (exactMatches.size() > 1) {
			logger.info("Reconcile: returning MAYBE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, exactMatches);
		} else {
			logger.info("Reconcile: returning EXACT; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			return new ReconciliationResultImpl(ReconciliationType.EXACT, exactMatches);
		}
	}

}
