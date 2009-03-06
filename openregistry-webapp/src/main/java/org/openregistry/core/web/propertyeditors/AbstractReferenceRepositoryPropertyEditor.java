package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Abstract class that supports the notion that a property editor will need access to the reference
 * repository to obtain its information.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public abstract class AbstractReferenceRepositoryPropertyEditor extends PropertyEditorSupport {

    private final ReferenceRepository referenceRepository;

    protected AbstractReferenceRepositoryPropertyEditor(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    protected ReferenceRepository getReferenceRepository() {
        return this.referenceRepository;
    }

    @Override
    public final void setAsText(final String s) throws IllegalArgumentException {
        if (StringUtils.hasText(s)) {
            setAsTextInternal(s);
        } else {
            setValue(null);
        }
    }

    protected abstract void setAsTextInternal(String s);
}
