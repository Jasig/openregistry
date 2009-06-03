package org.openregistry.core.service.activation;

import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.Person;
import org.openregistry.core.service.activation.ActivationKeyAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ActivationKeyAssignerImpl implements ActivationKeyAssigner {

	@Autowired(required = true)
	private ActivationKeyGeneratorImpl generator;

    public void addActivationKeyTo(Person person){
        ActivationKey activationKey = person.addActivationKey();
        activationKey.setValue(generator.generateNextString());
        activationKey.setExpirationDate(getExpirationDate());
    }

    private Date getExpirationDate(){
        //set the expiration date to be in 1 month
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
}