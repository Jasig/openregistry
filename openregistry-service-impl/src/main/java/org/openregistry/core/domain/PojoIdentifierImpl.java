package org.openregistry.core.domain;

import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.core.ValidateDefinition;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition()
public final class PojoIdentifierImpl implements Identifier {

    @NotNull(customCode = "testType")
    private IdentifierType type;

    @NotEmpty(customCode = "testValue")
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

	public Boolean isDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDeleted(Boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void setPrimary(Boolean value) {
		// TODO Auto-generated method stub
		
	}
}
