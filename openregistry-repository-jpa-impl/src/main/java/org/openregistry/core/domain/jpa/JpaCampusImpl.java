package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Campus;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaCampusImpl implements Campus {

    private String code;

    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
