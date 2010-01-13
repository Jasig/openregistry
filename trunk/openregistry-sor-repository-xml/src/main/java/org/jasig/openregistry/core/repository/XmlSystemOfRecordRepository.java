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
package org.jasig.openregistry.core.repository;

import org.jasig.openregistry.core.domain.sor.XmlBasedSoRSpecificationImpl;
import org.openregistry.core.domain.sor.SoRSpecification;
import org.openregistry.core.repository.SystemOfRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the XML files representing the System of Record specifications from a directory on the file system.
 * <p>
 * Note, this simple implementation ASSUMES that you want to replace the entire set with what's in the specified directory.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Named("systemOfRecordRepository")
public final class XmlSystemOfRecordRepository implements SystemOfRecordRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final File file;

    private final JAXBContext jaxbContext = JAXBContext.newInstance(XmlBasedSoRSpecificationImpl.class);

    private final Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();

    private List<SoRSpecification> soRSpecifications = new ArrayList<SoRSpecification>();

    /**
     * Creates a new repository using the supplied directory as the starting point.
     * 
     * @param resource
     * @throws Exception
     */
    public XmlSystemOfRecordRepository(final Resource resource) throws Exception {
        this.file = resource.getFile();
        logger.info("Attempting to load Xml Repository from [" + this.file.getAbsolutePath() + "]");
        Assert.isTrue(this.file.isDirectory(), "Provided resource MUST be a directory.");
        reload();
    }

    public SoRSpecification findSoRSpecificationById(final String sorSourceId) {
        for (final SoRSpecification soRSpecification : this.soRSpecifications) {
            if (soRSpecification.getSoR().equalsIgnoreCase(sorSourceId)) {
                return soRSpecification;
            }
        }
        throw new IllegalStateException("Requested SoR could not be found!");
    }

    public void reload() {
        logger.info("There are currently [" + soRSpecifications.size() + "] specs load.  Reloading now.");
        final List<SoRSpecification> soRSpecifications = new ArrayList<SoRSpecification>();

        for (final File file : this.file.listFiles(new FileFilter() {
            public boolean accept(final File file) {
                return file.getName().endsWith(".xml");
            }
        })) {
            final SoRSpecification soRSpecification = loadSoRSpecificationFromFile(file);

            if (soRSpecification != null) {
                soRSpecifications.add(soRSpecification);
            }
        }

        this.soRSpecifications = soRSpecifications;
    }

    private SoRSpecification loadSoRSpecificationFromFile(final File file) {
        try {
            logger.info("Attempting to load file [" + file.getName() + "] for reading specification.");
            final FileReader fileReader = new FileReader(file);
            logger.info("File [" + file.getName() + "] successfully loaded.");

            return (SoRSpecification) this.unMarshaller.unmarshal(fileReader);
        } catch (final Exception e) {
            logger.error("Cannot complete loading of [" + file.getName() + "] due to error below.");
            logger.error(e.getMessage(),e);
        }
        
        return null;
    }
}
