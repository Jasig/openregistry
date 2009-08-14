/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        addConverter(new UrlConverter());
    }

    public void afterPropertiesSet() throws Exception {
        addConverter(new RegionConverter(this.referenceRepository));
        addConverter(new SponsorConverter(this.personRepository));
        addConverter(new CountryConverter(this.referenceRepository));
        addConverter(new CampusConverter(this.referenceRepository));
        addConverter(new RoleInfoConverter(this.referenceRepository));
        addConverter(new TypeConverter(this.referenceRepository));
    } 

}