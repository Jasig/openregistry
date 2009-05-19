package org.openregistry.core.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContext;

/**
 * Sets the username from the Spring SecurityContext.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class SpringSecurityListener implements RevisionListener {

    public void newRevision(final Object o) {
        final SpringSecurityRevisionEntity entity = (SpringSecurityRevisionEntity) o;
        final SecurityContext context = SecurityContextHolder.getContext();

        if (context != null) {
            if (context.getAuthentication() != null) {
                entity.setUsername(context.getAuthentication().getName());
            } else {
                entity.setUsername("anonymous");
            }
        }
    }
}
