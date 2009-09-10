package org.openregistry.core.repository;

import org.springframework.beans.factory.FactoryBean;

/**
 * <code>FactoryBean</code> for producing <i>null</i> references of <code>ReferenceRepository</code>
 * <p/>
 * This is used solely for testing.
 *
 * @author Dmitriy Kopylenko
 */
public class NullReferenceRepositoryFactoryBean implements FactoryBean<ReferenceRepository> {

    public ReferenceRepository getObject() throws Exception {
        return null;
    }

    public Class<? extends ReferenceRepository> getObjectType() {
        return ReferenceRepository.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
