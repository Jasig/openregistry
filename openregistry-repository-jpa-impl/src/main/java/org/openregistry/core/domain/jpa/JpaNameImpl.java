package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaNameImpl extends Entity implements Name {

    private String prefix;

    private String given;

    private String middle;

    private String family;

    private String suffix;

    public String getPrefix() {
        return this.prefix;
    }

    public String getGiven() {
        return this.given;
    }

    public String getMiddle() {
        return this.middle;
    }

    public String getFamily() {
        return this.family;
    }

    public String getSuffix() {
        return this.suffix;
    }

    // TODO implement such that it returns a properly formatted name.
    public String toString() {
        return "";
    }
}
