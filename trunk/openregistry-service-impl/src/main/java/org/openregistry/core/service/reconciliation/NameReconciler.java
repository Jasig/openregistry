/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.service.reconciliation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
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

	private static final long serialVersionUID = -7509304431552307514L;

	@Autowired(required = true)
	private PersonRepository personRepository;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/* 
	 * @see org.openregistry.core.service.reconciliation.Reconciler#reconcile(org.openregistry.core.domain.sor.ReconciliationCriteria)
	 */
	public ReconciliationResult reconcile(final ReconciliationCriteria reconciliationCriteria) {
		final List<PersonMatch> exactMatches = new ArrayList<PersonMatch>();
		final List<PersonMatch> partialMatches = new ArrayList<PersonMatch>();
		
		final List<Name> names = reconciliationCriteria.getPerson().getNames();  // TODO deal with multiple names properly
		logger.info("Reconcile: found " + names.size() + " name(s)");
		for(final Name name: names) {
			logger.info("Reconcile: checking name: " + name.getGiven() + " " + name.getFamily());
			final List<Person> matches = this.personRepository.findByFamilyName(name.getFamily());
			logger.info("Reconcile: found " + matches.size() + " possible match(es)");
			for(Person match: matches) {
				if (name.getGiven().equals(match.getOfficialName().getGiven())) {  // TODO use all names
					logger.info("Reconcile: found exact match: " + match.getOfficialName().getGiven() + " " + match.getOfficialName().getFamily());
					exactMatches.add(new PersonMatchImpl(match, 100, Collections.<FieldMatch>emptyList()));
				} else {
					logger.info("Reconcile: found partial match: " + match.getOfficialName().getGiven() + " " + match.getOfficialName().getFamily());
					partialMatches.add(new PersonMatchImpl(match, 50, Collections.<FieldMatch>emptyList()));
				}
			}
		}
		logger.info("Reconcile: finished matching: " + exactMatches.size() + "," + partialMatches.size());
		if (exactMatches.isEmpty() && partialMatches.isEmpty()) {
			logger.info("Reconcile: returning NONE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			return new ReconciliationResultImpl(ReconciliationType.NONE, Collections.<PersonMatch>emptyList());
		} else if (! partialMatches.isEmpty()) {
		    final List<PersonMatch> matches = new ArrayList<PersonMatch>();
			matches.addAll(exactMatches);
			matches.addAll(partialMatches);
			logger.info("Reconcile: returning MAYBE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			Collections.sort(matches);
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, matches);
		} else if (exactMatches.size() > 1) {
			logger.info("Reconcile: returning MAYBE; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			Collections.sort(exactMatches);
			return new ReconciliationResultImpl(ReconciliationType.MAYBE, exactMatches);
		} else {
			logger.info("Reconcile: returning EXACT; " + exactMatches.size() + "," + partialMatches.size() + " matches found");
			// No need to sort exactMatches here as there is only one
			return new ReconciliationResultImpl(ReconciliationType.EXACT, exactMatches);
		}
	}

}
