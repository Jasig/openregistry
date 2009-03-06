package org.openregistry.core.web;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public abstract class AbstractLocalizationController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    private String dateFormat = DEFAULT_DATE_FORMAT;

    @Autowired(required=true)
    private MessageSource messageSource;


    public final void setDateFormat(final String dateFormat) {
        this.dateFormat = dateFormat;
    }

    protected final CustomDateEditor createNewCustomDateEditor() {
        return new CustomDateEditor(new SimpleDateFormat(this.dateFormat), true);
    }

    protected final MessageSource getMessageSource() {
        return this.messageSource;
    }
}
