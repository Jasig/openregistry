package org.openregistry.core.factory.jpa;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;

/**
 * Constructs a new {@link org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl}.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component
@Qualifier(value = "reconciliationCriteria")
public final class JpaReconciliationCriteriaFactory implements ObjectFactory<ReconciliationCriteria> {

    public ReconciliationCriteria getObject() throws BeansException {
        return new JpaReconciliationCriteriaImpl();
    }
}
