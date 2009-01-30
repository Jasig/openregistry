package org.openregistry.core.domain.internal;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since 1.0
 */
public class EntityTests {

    private Long id = 1L;

    @Test
    public void equalsOK() {

        Entity e1 = new Entity() {
            protected Long getId() {
                return id;
            }
        };
        Entity e2 = new Entity() {
            protected Long getId() {
                return id;
            }
        };
        assertTrue(e1.equals(e2));
    }
}
