package org.openregistry.core.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * An aspect that can be disabled via configuration.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 *
 */
public abstract class AbstractNameAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private boolean disable;

    private Map<String,String> customMapping = new HashMap<String,String>();

    protected final boolean isDisabled() {
        return this.disable;
    }

    protected final Map<String,String> getCustomMapping() {
        return this.customMapping;
    }

    public final void setDisabled(final boolean disabled) {
        logger.warn("Setting disabled status for this Aspect: " + disabled);
        this.disable = disabled;
    }

    public final void setCustomMapping(final Map<String,String> customMapping) {
        this.customMapping = customMapping;
    }
}
