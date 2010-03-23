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

package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.security.SecureRandom;

/**
 * Test identifier assigner that generates identifiers purely randomly.  Its PERFECT for demonstrations, but not
 * for any form of actual testing or production usage.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class TestIdentifierAssigner implements IdentifierAssigner {

    /** The array of printable characters to be used in our random string. */
    private static final char[] PRINTABLE_CHARACTERS = "0123456789".toCharArray();

    private static final SecureRandom secureRandom = new SecureRandom();

	private final ReferenceRepository referenceRepository;

    @Inject
    public TestIdentifierAssigner(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public void addIdentifierTo(final SorPerson sorPerson, final Person person) {
        final Name name = person.getOfficialName();

        final StringBuilder builder = new StringBuilder();

        if (StringUtils.hasText(name.getGiven())) {
            builder.append(name.getGiven().substring(0,1));
        }

        if (StringUtils.hasText(name.getMiddle())) {
            builder.append(name.getMiddle().substring(0,1));
        }

        if (StringUtils.hasText(name.getFamily())) {
            builder.append(name.getFamily().substring(0,1));
        }

        builder.append(constructNewValue());

        final Identifier identifier = person.addIdentifier(referenceRepository.findIdentifierType(getIdentifierType()), builder.toString());
        identifier.setDeleted(false);
        identifier.setPrimary(true);
    }

    public String getIdentifierType() {
        return "NETID";
    }

    private String constructNewValue() {
        final int ID_LENGTH = secureRandom.nextInt(7) + 3;
        final byte[] random = new byte[ID_LENGTH];
        secureRandom.nextBytes(random);
        return convertBytesToString(random);
    }

    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }

        return new String(output);
    }
}
