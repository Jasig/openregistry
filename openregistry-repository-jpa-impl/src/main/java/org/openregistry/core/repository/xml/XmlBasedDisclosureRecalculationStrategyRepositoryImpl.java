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
package org.openregistry.core.repository.xml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.openregistry.core.repository.DisclosureRecalculationStrategyRepository;
import org.openregistry.core.service.DisclosureRecalculationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class XmlBasedDisclosureRecalculationStrategyRepositoryImpl implements
		DisclosureRecalculationStrategyRepository {

	private DisclosureRecalculationStrategy disclosureRecalcualationStrategy;
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public XmlBasedDisclosureRecalculationStrategyRepositoryImpl
		(final Resource resource) throws JAXBException, IOException {
		
		File disclosureCalculationStrategyFile = resource.getFile();
	    JAXBContext jaxbContext = JAXBContext.newInstance(XmlBasedDisclosureRecalculationStrategyImpl.class);
	    Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
		
		
        logger.info("Attempting to load Xml Disclosure recalculation strategy spec from [" 
        		+ disclosureCalculationStrategyFile.getAbsolutePath() + "]");

		Assert.isTrue(disclosureCalculationStrategyFile.isFile() 
			&& disclosureCalculationStrategyFile.canRead() 
			&& disclosureCalculationStrategyFile.getName().endsWith(".xml"));
		
		final FileReader fileReader = new FileReader(disclosureCalculationStrategyFile);
		disclosureRecalcualationStrategy = (DisclosureRecalculationStrategy) unMarshaller.unmarshal(fileReader);
        logger.info("Loaded Xml Disclosure recalculation strategy spec with name [" 
        		+ disclosureRecalcualationStrategy.getName()+"] description ["
        		+ disclosureRecalcualationStrategy.getDescription()+"]");
	}
	
	/**
	 * @see org.openregistry.core.repository.DisclosureRecalculationStrategyRepository#getDisclosureRecalculationStrategy()
	 */
	public DisclosureRecalculationStrategy getDisclosureRecalculationStrategy() {
		return disclosureRecalcualationStrategy;
	}
}
