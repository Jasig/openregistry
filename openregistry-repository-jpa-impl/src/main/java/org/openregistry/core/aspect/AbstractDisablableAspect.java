package org.openregistry.core.aspect;

/**
 * An aspect that can be disabled via configuration.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 *
 */
public abstract class AbstractDisablableAspect {

    private boolean disable;

    protected final boolean isDisabled() {
        return this.disable;
    }

    public final void setDisabled(final boolean disabled) {
        this.disable = disabled;
    }
}
