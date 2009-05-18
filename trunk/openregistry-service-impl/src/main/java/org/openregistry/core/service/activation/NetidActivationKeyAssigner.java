package org.openregistry.core.service.activation;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.service.activation.ActivationKeyAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class NetidActivationKeyAssigner implements ActivationKeyAssigner {

	@Autowired(required = true)
	private NetIdActivationKeyGenerator generator;

	private final String identifierType = "NETID";

    public void addActivationKeyTo(Identifier identifier){
        //TODO should verify that identifier is a NETID.
        ActivationKey activationKey = identifier.addActivationKey();
        activationKey.setValue(generator.generateNextString());
        activationKey.setExpirationDate(getExpirationDate());
    }

    public String getActivationKeyType(){
        return identifierType;
    }

    private Date getExpirationDate(){
        //set the expiration date to be in 1 month
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
}