package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Apr 2, 2009
 * Time: 3:33:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ORDateConverter extends StringToObject {

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    private String dateFormat = DEFAULT_DATE_FORMAT;

    private SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ORDateConverter() {
        super(Date.class);
    }

    @Override
    protected Object toObject(String string, Class targetClass) {
        Date date=null;
        try {
            date = df.parse(string);
        } catch (Exception ex){
        }
        logger.info("ORDateConverter: Converting to String to Date");
        return date;
    }

    @Override
    protected String toString(Object object) {
        logger.info("ORDateConverter: Converting to Date to string: "+df.format((Date)object));
        return df.format((Date)object);
    }
}