package org.openregistry.core.domain;

import org.openregistry.core.domain.Name;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;

/**
 * POJO implementation of the {@link org.openregistry.core.domain.Name} interface.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition
public class PojoNameImpl implements Name {

    private String prefix;

    private String given;

    private String middle;

    @NotEmpty(customCode = "familyNameRequired")
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

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setGiven(final String given) {
        this.given = given;
    }

    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        if (this.prefix != null) {
            builder.append(this.prefix);
            builder.append(" ");
        }

        if (this.given != null) {
            builder.append(this.given);
            builder.append(" ");
        }

        builder.append(this.family);
        builder.append(" ");

        if (this.suffix != null) {
            builder.append(this.suffix);
        }

        return builder.toString().trim();
    }
}
