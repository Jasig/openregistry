package org.openregistry.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.identifier.IdentifierAssigner;
import org.openregistry.service.activation.NetidActivationKeyAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NetidIdentifierAssigner implements IdentifierAssigner {

	@Autowired(required = true)
	private ReferenceRepository referenceRepository;

	@Autowired(required = true)
	private NetIdIdentifierGenerator netIdGenerator;

    @Autowired(required = true)
    private NetidActivationKeyAssigner netIdActivationKeyAssigner;

	private final String identifierType = "NETID";

	public void addIdentifierTo(SorPerson sorPerson, Person person) {
		// TODO should we check to see if this identifier already exists?
        // TODO should be getting netid from iid which is coming from PDB in the first release.
		final Identifier identifier = person.addIdentifier();
		identifier.setType(referenceRepository.findIdentifierType(identifierType));
		identifier.setValue(netIdGenerator.generateIdentifier(person));
		identifier.setDeleted(false);
		identifier.setPrimary(false);

        //create activation key for new netid
        createActivationKey(identifier);
	}

	public String getIdentifierType() {
		return identifierType;
	}

    private void createActivationKey(Identifier identifier){
        netIdActivationKeyAssigner.addActivationKeyTo(identifier);
    }

}