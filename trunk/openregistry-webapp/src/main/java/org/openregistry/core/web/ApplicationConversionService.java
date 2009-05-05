package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Mar 26, 2009
 * Time: 9:58:55 AM
 * To change this template use File | Settings | File Templates.
 */

@Service("conversionService")
public final class ApplicationConversionService extends DefaultConversionService implements InitializingBean {

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    @Autowired(required = true)
    private PersonRepository personRepository;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void addDefaultConverters() {
	    super.addDefaultConverters();
	    StringToDate dateConverter = new StringToDate();
	    dateConverter.setPattern("MM/dd/yyyy");
	    addConverter("shortDate", dateConverter);
        addConverter(dateConverter);
    }

    public void afterPropertiesSet() throws Exception {
        addConverter(new RegionConverter(this.referenceRepository));
        addConverter(new SponsorConverter(this.personRepository));
        addConverter(new CountryConverter(this.referenceRepository));
        addConverter(new CampusConverter(this.referenceRepository));
    } 

}