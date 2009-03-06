package org.openregistry.core.web.propertyeditors;

/**
 * Removes the extraneous characters from the Phone number in oder to persist it.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class PhoneEditor extends AbstractRegExPropertyEditor {

    /** Regular expression used to identify range of tag characters. */
    private static final String REG_EX_TAGS = "[()-. \t]";

    public PhoneEditor() {
        super(REG_EX_TAGS);
    }
}

