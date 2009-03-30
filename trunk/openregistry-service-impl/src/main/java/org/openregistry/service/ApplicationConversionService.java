package org.openregistry.service;

import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Mar 26, 2009
 * Time: 9:58:55 AM
 * To change this template use File | Settings | File Templates.
 */

@Component("conversionService")
public class ApplicationConversionService extends DefaultConversionService {

    @Override
    protected void addDefaultConverters() {
	    super.addDefaultConverters();
	    StringToDate dateConverter = new StringToDate();
	    dateConverter.setPattern("MM/dd/yyyy");
	    addConverter("shortDate", dateConverter);
    }
}