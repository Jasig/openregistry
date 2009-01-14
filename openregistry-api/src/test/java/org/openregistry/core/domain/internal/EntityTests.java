package org.openregistry.core.domain.internal;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since 1.0
 */
public class EntityTests {

    @Test
    public void equalsOK() {
        Entity e1 = new Entity() {};
        Entity e2 = new Entity() {};
        e1.setId(1L);
        e2.setId(1L);
        assertTrue(e1.equals(e2));
    }
}
