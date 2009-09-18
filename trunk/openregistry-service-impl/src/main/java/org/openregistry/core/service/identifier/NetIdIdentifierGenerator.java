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

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

/**
 * NetId Identifier Generator
 */
@Component
public final class NetIdIdentifierGenerator {

    private final int baseLength = 3;
    private final String APPENDED_VALUE ="0";
    String NETID="NETID";

    @Autowired(required=true)
    private PersonRepository personRepository;

    public String generateIdentifier(Person person) {
        String netId = null;
        Name name = person.getOfficialName();
        netId = appendNumber(constructBase(name));
        return netId;
    }

    private String constructBase(Name name){
        StringBuffer base = new StringBuffer();

        if (StringUtils.hasText(name.getGiven()))
            base.append(name.getGiven().substring(0,1));

        if (StringUtils.hasText(name.getMiddle()))
            base.append(name.getMiddle().substring(0,1));

        if (StringUtils.hasText(name.getFamily()))
            base.append(name.getFamily().substring(0,1));

        //append a 0 to the base so that the base has a length of 3
        for (int i = base.length(); i< baseLength; i++)base.append(APPENDED_VALUE);

        return base.toString();
    }

    private String appendNumber(String base){
        int baseNumber = 1;

        List<Identifier> identifiers = new ArrayList<Identifier>();
        // personRepository.findNetIDBaseIdentifier(NETID,base);
        if (identifiers.isEmpty())
            return base+baseNumber;

        boolean netIDVerified = false;
        while (!netIDVerified){
            if (!isDuplicate(identifiers, base+baseNumber)) netIDVerified = true;
            else baseNumber++;
        }

        return base+baseNumber;
    }

    private boolean isDuplicate(List<Identifier> identifiers, String netID){
        for (final Identifier identifier : identifiers) {
            if (identifier.getValue().equals(netID)) return true;
        }
        return false;
    }

}