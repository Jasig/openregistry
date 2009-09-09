package org.openregistry.core;

import org.springframework.beans.factory.FactoryBean;
import org.openregistry.core.service.IdentifierChangeService;

/**
 * <code>FactoryBean</code> for producing <i>null</i> references of <code>IdentifierChangeService</code>
 *
 * This is used solely for testing.
 *
 * @author Dmitriy Kopylenko
 */
public class NullIdentifierChangeServiceFactoryBean implements FactoryBean<IdentifierChangeService> {

    public IdentifierChangeService getObject() throws Exception {
        return null;
    }

    public Class<? extends IdentifierChangeService> getObjectType() {
        return IdentifierChangeService.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
