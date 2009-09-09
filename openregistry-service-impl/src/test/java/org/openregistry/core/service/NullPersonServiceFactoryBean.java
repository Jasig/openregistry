package org.openregistry.core.service;

import org.springframework.beans.factory.FactoryBean;

/**
 * <code>FactoryBean</code> for producing <i>null</i> references of <code>PersonService</code>
 *
 * This is used solely for testing.
 *
 * @author Dmitriy Kopylenko
 */
public class NullPersonServiceFactoryBean implements FactoryBean<PersonService> {

    public PersonService getObject() throws Exception {
        return null;
    }

    public Class<? extends PersonService> getObjectType() {
        return PersonService.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
