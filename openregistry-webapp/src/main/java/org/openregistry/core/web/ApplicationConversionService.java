package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Mar 26, 2009
 * Time: 9:58:55 AM
 * To change this template use File | Settings | File Templates.
 */

@Service("conversionService")
public final class ApplicationConversionService extends DefaultConversionService {

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    @Override
    protected void addDefaultConverters() {
	    super.addDefaultConverters();
	    StringToDate dateConverter = new StringToDate();
	    dateConverter.setPattern("MM/dd/yyyy");
	    addConverter("shortDate", dateConverter);
    }
}