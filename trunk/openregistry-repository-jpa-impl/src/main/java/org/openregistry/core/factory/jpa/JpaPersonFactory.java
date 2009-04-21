package org.openregistry.core.factory.jpa;

import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.openregistry.core.domain.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 * Autowired component that will construct a new JpaPersonImpl to be fed to our other layers.  There should only be one
 * of these configured at a given time.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component
@Qualifier(value = "person")
public final class JpaPersonFactory implements ObjectFactory<Person> {

    public Person getObject() throws BeansException {
        return new JpaPersonImpl();
    }
}
