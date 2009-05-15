package org.openregistry.service.identifier;

import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * NetId Identifier Generator
 */
@Component
public final class NetIdIdentifierGenerator {

    private final int baseNumber = 1;
    private final int baseLength = 3;
    private final String APPENDED_VALUE ="0";

    public String generateIdentifier(Person person) {
        StringBuffer netId = null;
        Name name = person.getOfficialName();
        netId = appendNumber(constructBase(name));
        return netId.toString();
    }

    private StringBuffer constructBase(Name name){
        StringBuffer base = new StringBuffer();

        if (StringUtils.hasText(name.getGiven()))
            base.append(name.getGiven().substring(0,1));

        if (StringUtils.hasText(name.getMiddle()))
            base.append(name.getMiddle().substring(0,1));

        if (StringUtils.hasText(name.getFamily()))
            base.append(name.getFamily().substring(0,1));

        //append a 0 to the base so that the base has a length of 3
        for (int i = base.length(); i< baseLength; i++)base.append(APPENDED_VALUE);

        return base;
    }

    private StringBuffer appendNumber(StringBuffer base){
        StringBuffer netId =base;

        //TODO look for base in registry with highest number and increment by 1.
        netId.append(baseNumber);

        return netId;
    }

}