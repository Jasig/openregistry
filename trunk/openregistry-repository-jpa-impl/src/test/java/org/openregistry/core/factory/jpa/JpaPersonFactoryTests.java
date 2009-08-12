package org.openregistry.core.factory.jpa;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class JpaPersonFactoryTests {

    private JpaPersonFactory jpaPersonFactory;

    @Before
    public void setUp() {
        this.jpaPersonFactory = new JpaPersonFactory();
    }

    @Test
    public void returnedInstance() {
        final Person person = this.jpaPersonFactory.getObject();
        assertTrue("person must be instanceof JpaPersonImpl", person instanceof JpaPersonImpl);
    }
}
