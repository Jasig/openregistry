package org.openregistry.core.factory.jpa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class JpaReconciliationCriteriaFactoryTests {

    private JpaReconciliationCriteriaFactory jpaReconciliationCriteriaFactory;

    @Before
    public void setUp() {
        this.jpaReconciliationCriteriaFactory = new JpaReconciliationCriteriaFactory();
    }

    @Test
    public void returnProperInstance() {
        final ReconciliationCriteria reconciliationCriteria = this.jpaReconciliationCriteriaFactory.getObject();
        assertTrue("reconciliationCriteria must be of type JpaReconciliationCriteriaImpl", reconciliationCriteria instanceof JpaReconciliationCriteriaImpl);
    }
}
