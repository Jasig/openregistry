package org.openregistry.service.reconciliation;

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
	
	private List<PersonMatch> exactMatches;
	private List<PersonMatch> partialMatches;
	
	/* 
	 * @see org.openregistry.core.service.reconciliation.Reconciler#reconcile(org.openregistry.core.domain.sor.PersonSearch)
	 */
	public ReconciliationResult reconcile(PersonSearch personSearch) {
		Set<? extends Name> names = personSearch.getPerson().getNames();  // TODO deal with multiple names properly
		for(Name name: names) {
			List<Person> matches = this.personRepository.findByFamilyName(name.getFamily());
			for(Person match: matches) {
				if (name.getGiven().equals(match.getOfficialName().getGiven())) {  // TODO use all names
					exactMatches.add(new PersonMatchImpl(match, 100, Collections.<FieldMatch>emptyList()));
				} else {
					partialMatches.add(new PersonMatchImpl(match, 50, Collections.<FieldMatch>emptyList()));
				}
			}
		}
		if (exactMatches.isEmpty() && partialMatches.isEmpty()) {
			return new ReconciliationResultImpl(ReconciliationType.NONE, Collections.<PersonMatch>emptyList());
		}
		if (! partialMatches.isEmpty()) {
		    List<PersonMatch> matches = null;
			matches.addAll(exactMatches);
			matches.addAll(partialMatches);
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, matches);
		} else if (exactMatches.size() > 1) {
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, exactMatches);
		} else {
			return new ReconciliationResultImpl(ReconciliationType.EXACT, exactMatches);
		}
	}

}
