package org.openregistry.core.aspect;

import junit.framework.TestCase;
import org.junit.Test;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonImpl;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Feb 23, 2010
 * Time: 3:55:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CapitalizationTests extends TestCase {

    @Test
    public void testUppercase() {
        final JpaSorPersonImpl person = new JpaSorPersonImpl();
        final Name name = person.addName();
        name.setGiven("given");

        assertEquals("given", name.getGiven());
    }
}
