package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.jpa.JpaCountryImpl;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Jan 30, 2009
 * Time: 10:34:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class CountryEditor extends PropertyEditorSupport {

    private String format;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAsText(String text) {
        if (format != null && format.equals("upperCase")) {
            text = text.toUpperCase();
        }

        //setValue(type);
    }
}
