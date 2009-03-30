package org.openregistry.service.reconciliation;

import java.util.List;

import org.openregistry.core.domain.Person;
import org.openregistry.core.service.reconciliation.FieldMatch;
import org.openregistry.core.service.reconciliation.PersonMatch;

public class PersonMatchImpl implements PersonMatch {
	
	private int confidenceLevel;
	private List<FieldMatch> matches;
	private Person person;
	
	public PersonMatchImpl(final Person person, 
			final int confidence, 
			final List<FieldMatch> matches) {
		this.person = person;
		this.confidenceLevel = confidence;
		this.matches = matches;
	}

	public int getConfidenceLevel() {
		return this.confidenceLevel;
	}

	public List<FieldMatch> getMatches() {
		return this.matches;
	}

	public Person getPerson() {
		return this.person;
	}

}
