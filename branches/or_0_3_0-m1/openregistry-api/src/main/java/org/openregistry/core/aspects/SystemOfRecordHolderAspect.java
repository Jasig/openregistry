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

package org.openregistry.core.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openregistry.core.domain.sor.SoRSpecification;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SystemOfRecordHolder;
import org.openregistry.core.repository.SystemOfRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Aspect
public final class SystemOfRecordHolderAspect {

    @Inject
    private SystemOfRecordRepository systemOfRecordRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* org.openregistry.core.service.PersonService.*(..)) && args(sorPerson,..)")
    public Object manageSystemOfRecordHolder(final ProceedingJoinPoint proceedingJoinPoint, final SorPerson sorPerson) throws Throwable {
        try {
            logger.debug("Attempting to load System of Record from Person [" + sorPerson.getSourceSor() + "]");
            final SoRSpecification soRSpecification = this.systemOfRecordRepository.findSoRSpecificationById(sorPerson.getSourceSor());
            SystemOfRecordHolder.setCurrentSystemOfRecord(soRSpecification);
            return proceedingJoinPoint.proceed();

        } finally {
            logger.debug("Clearing System of Record.");
            SystemOfRecordHolder.clearCurrentSystemOfRecord();
        }
    }
}
