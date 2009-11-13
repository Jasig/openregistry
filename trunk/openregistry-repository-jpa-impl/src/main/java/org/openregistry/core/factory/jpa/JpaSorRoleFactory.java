package org.openregistry.core.factory.jpa;

import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.domain.sor.SorRole;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Autowired component that will construct a new JpaSorRoleImpl to be fed to our other layers.  There should only be one
 * of these configured at a given time.
 *
 * @since 1.0
 */
@Component("sorRoleFactory")
public class JpaSorRoleFactory implements ObjectFactory<SorRole> {

    public SorRole getObject() throws BeansException {
        return new JpaSorRoleImpl(); 
    }
}
