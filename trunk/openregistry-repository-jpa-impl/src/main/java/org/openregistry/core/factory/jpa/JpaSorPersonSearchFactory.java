package org.openregistry.core.factory.jpa;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonSearchImpl;

/**
 * Constructs a new {@link org.openregistry.core.domain.jpa.sor.JpaSorPersonSearchImpl}.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component
public final class JpaSorPersonSearchFactory implements ObjectFactory<PersonSearch> {

    public PersonSearch getObject() throws BeansException {
        return new JpaSorPersonSearchImpl();
    }
}
