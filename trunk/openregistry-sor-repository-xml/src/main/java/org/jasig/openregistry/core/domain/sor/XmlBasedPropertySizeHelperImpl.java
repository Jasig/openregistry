package org.jasig.openregistry.core.domain.sor;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Collection;

/**
 * Helper class to hold the min and max values for collections.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class XmlBasedPropertySizeHelperImpl {

    @XmlAttribute(name="min")
    private int min = 0;

    @XmlAttribute(name="max")
    private int max = Integer.MAX_VALUE;

    @XmlAttribute(name = "property", required = true)
    private String property;

    public void setProperty(final String property) {
        this.property = property;
    }

    public void setMax(final int max) {
        this.max = max;
    }

    public void setMin(final int min) {
        this.min = min;
    }

    public boolean supportsProperty(final String property) {
        return this.property.equals(property);
    }

    public boolean isWithinRequiredRangeForProperty(final Collection collection) {
        return collection.size() >= this.min && collection.size() <= this.max;
    }
}
