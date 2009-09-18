package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Sep 18, 2009
 * Time: 2:30:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestIdentifierAssigner implements IdentifierAssigner {

    /** The array of printable characters to be used in our random string. */
    private static final char[] PRINTABLE_CHARACTERS = "0123456789".toCharArray();

    private static final SecureRandom secureRandom = new SecureRandom();

	@Autowired(required = true)
	private ReferenceRepository referenceRepository;

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

        final Identifier identifier = person.addIdentifier();
        identifier.setType(referenceRepository.findIdentifierType(getIdentifierType()));
        identifier.setValue(builder.toString());
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
