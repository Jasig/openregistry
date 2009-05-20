package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.identifier.IdentifierAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Component;

@Component
public class RcpidIdentifierAssigner implements IdentifierAssigner {
	
	@Autowired(required = true)
	private ReferenceRepository referenceRepository;
	
	@Autowired(required = true)
	private DataFieldMaxValueIncrementer rcpIdGenerator;
	
	private final String identifierType = "RCPID";

	public void addIdentifierTo(SorPerson sorPerson, Person person) {
		// TODO should we check to see if this identifier already exists?
		final Identifier identifier = person.addIdentifier();
		identifier.setType(referenceRepository.findIdentifierType(identifierType));
		identifier.setValue(rcpIdGenerator.nextStringValue());
		identifier.setDeleted(false);
		identifier.setPrimary(false);
	}

	public String getIdentifierType() {
		return identifierType;
	}

}
