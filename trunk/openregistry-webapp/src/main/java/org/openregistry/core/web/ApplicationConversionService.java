/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.web;

import org.openregistry.core.web.converters.TrimStringToStringConverter;
import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.beans.factory.InitializingBean;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Mar 26, 2009
 * Time: 9:58:55 AM
 * To change this template use File | Settings | File Templates.
 */

@Named("applicationConversionService")
public final class ApplicationConversionService extends DefaultConversionService implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final ReferenceRepository referenceRepository;

    private final PersonRepository personRepository;

    private String defaultDateFormat = "yyyy-MM-dd";

    @Inject
    public ApplicationConversionService(final PersonRepository personRepository, final ReferenceRepository referenceRepository) {
        this.personRepository = personRepository;
        this.referenceRepository = referenceRepository;
    }

    @Override
    protected void addDefaultConverters() {
	    super.addDefaultConverters();
        // short date
	    StringToDate dateConverter = new StringToDate();
	    dateConverter.setPattern("MM/dd/yyyy");

	    addConverter("shortDate", dateConverter);
        addConverter(new UrlConverter());
    }

    public void afterPropertiesSet() throws Exception {
        addConverter(new RegionConverter(this.referenceRepository));
        addConverter(new SponsorConverter(this.personRepository));
        addConverter(new CountryConverter(this.referenceRepository));
        addConverter(new CampusConverter(this.referenceRepository));
        addConverter(new TypeConverter(this.referenceRepository));
        addConverter(new IdentifierTypeConverter(this.referenceRepository));
        final StringToDate dateConverter = new StringToDate();
        dateConverter.setPattern(this.defaultDateFormat);
        
        addConverter(dateConverter);
        addConverter(new TrimStringToStringConverter());
    }

    public void setDefaultDateFormat(final String dateFormat) {
        this.defaultDateFormat = dateFormat;
    }
}