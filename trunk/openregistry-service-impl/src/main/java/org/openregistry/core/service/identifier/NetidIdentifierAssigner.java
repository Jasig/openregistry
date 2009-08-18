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
package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetidIdentifierAssigner implements IdentifierAssigner {

	@Autowired(required = true)
	private ReferenceRepository referenceRepository;

	@Autowired(required = true)
	private NetIdIdentifierGenerator netIdGenerator;

	private final String identifierType = "NETID";

	public void addIdentifierTo(SorPerson sorPerson, Person person) {
		// TODO should we check to see if this identifier already exists?
        // TODO should be getting netid from iid which is coming from PDB in the first release.
		final Identifier identifier = person.addIdentifier();
		identifier.setType(referenceRepository.findIdentifierType(identifierType));
		identifier.setValue(netIdGenerator.generateIdentifier(person));
		identifier.setDeleted(false);
		identifier.setPrimary(false);
	}

	public String getIdentifierType() {
		return identifierType;
	}
}