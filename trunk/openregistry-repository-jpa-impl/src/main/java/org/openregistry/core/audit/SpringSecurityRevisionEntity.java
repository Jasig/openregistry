package org.openregistry.core.audit;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

/**
 * Adds the username of the person who made the revision change.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 4.0.0
 */
@Entity
@RevisionEntity(SpringSecurityListener.class)
public final class SpringSecurityRevisionEntity extends DefaultRevisionEntity {

    private String username;

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
