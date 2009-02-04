package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.jpa.JpaCountryImpl;
import org.openregistry.core.domain.Country;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Jan 30, 2009
 * Time: 10:34:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegionEditor extends PropertyEditorSupport {

    private String format;

    @Autowired(required=true)
    private ReferenceRepository referenceRepository;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAsText(String text) {
        if (text == null) setValue(" ");        
    }
}
