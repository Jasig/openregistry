package org.openregistry.core.service.identifier;

import java.util.Deque;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;

public abstract class AbstractIdentifierAssigner implements IdentifierAssigner {

	/**
	 * Looks up a personn's identifiers of a given type and returns the first primary, non-deleted one.
	 * If none found, returns NULL.
	 */
	protected final Identifier findPrimaryIdentifier(Person person, String identifierType) {
		final Deque<Identifier> identifiersForThisType = person.getIdentifiersByType().get(identifierType);
		if (identifiersForThisType != null) {
			for (final Identifier id : identifiersForThisType) {
				if (id.isPrimary() && ! id.isDeleted()) {
					return id;
				}
			}
		}
		return null;
	}
}

