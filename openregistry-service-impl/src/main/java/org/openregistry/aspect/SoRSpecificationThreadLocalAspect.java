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
package org.openregistry.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SoRSpecification;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SystemOfRecordHolder;
import org.openregistry.core.repository.SystemOfRecordRepository;

/**
 * @version $Revision$ $ Date$
 * @since 0.1
 */
@Aspect
public final class SoRSpecificationThreadLocalAspect {

    private SystemOfRecordRepository systemOfRecordRepository;

    @Around("(execution (public * org.openregistry.core.service.PersonService+.*(..))) && args(sorPerson, ..)")
    public  Object populateThreadLocalForSoRSpecification(final ProceedingJoinPoint proceedingJoinPoint, final SorPerson sorPerson) throws Throwable {
        try {
            final SoRSpecification soRSpecification = sorPerson != null ? this.systemOfRecordRepository.findSoRSpecificationById(sorPerson.getSourceSor()) : null;
            SystemOfRecordHolder.setCurrentSystemOfRecord(soRSpecification);
            return proceedingJoinPoint.proceed();
        } finally {
            SystemOfRecordHolder.clearCurrentSystemOfRecord();
        }
    }

    @Around("(execution (public * org.openregistry.core.service.PersonService+.*(..))) && args(reconciliationCriteria, ..)")
    public  Object populateThreadLocalForSoRSpecification(final ProceedingJoinPoint proceedingJoinPoint, final ReconciliationCriteria reconciliationCriteria) throws Throwable {
        try {
            final SoRSpecification soRSpecification =  reconciliationCriteria != null ? this.systemOfRecordRepository.findSoRSpecificationById(reconciliationCriteria.getSorPerson().getSourceSor()) : null;
            SystemOfRecordHolder.setCurrentSystemOfRecord(soRSpecification);
            return proceedingJoinPoint.proceed();
        } finally {
            SystemOfRecordHolder.clearCurrentSystemOfRecord();
        }
    }

    public void setSystemOfRecordRepository(final SystemOfRecordRepository systemOfRecordRepository) {
        this.systemOfRecordRepository = systemOfRecordRepository;
    }
}
