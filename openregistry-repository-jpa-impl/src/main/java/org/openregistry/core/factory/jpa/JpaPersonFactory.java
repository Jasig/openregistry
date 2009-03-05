package org.openregistry.core.factory.jpa;

import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.openregistry.core.domain.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Mar 5, 2009
 * Time: 11:50:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public final class JpaPersonFactory implements ObjectFactory<Person> {

    public Person getObject() throws BeansException {
        return new JpaPersonImpl();
    }
}
