package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class PojoIdentifierImpl implements Identifier {

    private IdentifierType type;

    private String value;

    public IdentifierType getType() {
        return this.type;
    }

    public String getValue() {
        return value;
    }

    public void setType(final IdentifierType type) {
        this.type = type;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
