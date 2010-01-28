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
