package org.openregistry.core.service.identifier;

import java.util.Deque;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;

public abstract class AbstractIdentifierAssigner implements IdentifierAssigner {

	protected final Identifier findPrimaryIdentifier(Person person, String identifierType) {
		Identifier identifier = null;
		final Deque<Identifier> identifiersForThisType = person.getIdentifiersByType().get(identifierType);
		for (final Identifier id : identifiersForThisType) {
			if (id.isPrimary() && ! id.isDeleted()) {
				identifier = id;
			}
		}
		return identifier;
	}
}

