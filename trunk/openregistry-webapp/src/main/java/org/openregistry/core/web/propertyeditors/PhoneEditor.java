package org.openregistry.core.web.propertyeditors;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Jan 30, 2009
 * Time: 9:19:42 AM
 * To change this template use File | Settings | File Templates.
 */

import java.beans.PropertyEditorSupport;

public class PhoneEditor extends PropertyEditorSupport {

    /** Regular expression used to identify range of tag characters. */
    private static final String REG_EX_TAGS = "[()-. \t]";

    public String getAsText() {
        return null;
    }

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(text.replaceAll(REG_EX_TAGS, ""));
    }

}

