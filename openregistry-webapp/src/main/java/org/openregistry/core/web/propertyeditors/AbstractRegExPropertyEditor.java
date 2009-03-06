package org.openregistry.core.web.propertyeditors;

import java.beans.PropertyEditorSupport;

/**
 * Abstract class that all property editors doing regular expressions can extends.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public abstract class AbstractRegExPropertyEditor extends PropertyEditorSupport {

    private final String regExpression;

    protected AbstractRegExPropertyEditor(final String regExpression) {
        this.regExpression = regExpression;
    }

    @Override
    public final void setAsText(final String s) throws IllegalArgumentException {
        if (s == null) {
            setValue(null);
        } else {
            setValue(s.replaceAll(regExpression, ""));
        }
    }
}
