/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service.reconciliation;

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

	public List<FieldMatch> getFieldMatches() {
		return this.matches;
	}

	public Person getPerson() {
		return this.person;
	}

	public int compareTo(final PersonMatch personMatch) {
		final int level = this.confidenceLevel - personMatch.getConfidenceLevel();
		if (level == 0) {
			final int result = this.person.getOfficialName().getFamily().compareTo(personMatch.getPerson().getOfficialName().getFamily());
			return result == 0 ? this.person.getOfficialName().getGiven().compareTo(personMatch.getPerson().getOfficialName().getGiven()): result;
		}
		return level;
	}

}
