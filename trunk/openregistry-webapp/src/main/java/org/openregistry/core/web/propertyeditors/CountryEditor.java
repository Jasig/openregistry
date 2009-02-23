package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.jpa.JpaCountryImpl;
import org.openregistry.core.domain.Country;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CountryEditor extends PropertyEditorSupport {

    private String format;

     protected final Logger logger = LoggerFactory.getLogger(getClass());
    ReferenceRepository referenceRepository;

    public CountryEditor(ReferenceRepository referenceRepository){
        this.referenceRepository = referenceRepository;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAsText(){
        Country country = (Country) getValue();
        return country != null ? country.getName() : " ";
    }

    @Override 
    public void setAsText(String text) {
        if (StringUtils.hasText(text)){
            setValue(referenceRepository.getCountryById(new Long(text)));
        } else{
            setValue(null);
        }
    }
}
