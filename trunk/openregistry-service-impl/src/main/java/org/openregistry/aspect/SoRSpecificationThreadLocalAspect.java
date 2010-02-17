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

    @Around("(execution (public * org.openregistry.core.service.PersonService+.*(..))) && args(sorPerson)")
    public  Object populateThreadLocalForSoRSpecification(final ProceedingJoinPoint proceedingJoinPoint, final SorPerson sorPerson) throws Throwable {
        if (this.systemOfRecordRepository != null) {
            try {
                final SoRSpecification soRSpecification = this.systemOfRecordRepository.findSoRSpecificationById(sorPerson.getSourceSor());
                SystemOfRecordHolder.setCurrentSystemOfRecord(soRSpecification);
                return proceedingJoinPoint.proceed();
            } finally {
                SystemOfRecordHolder.clearCurrentSystemOfRecord();
            }
        } else {
            return proceedingJoinPoint.proceed();
        }
    }

    @Around("(execution (public * org.openregistry.core.service.PersonService+.*(..))) && args(reconciliationCriteria)")
    public  Object populateThreadLocalForSoRSpecification(final ProceedingJoinPoint proceedingJoinPoint, final ReconciliationCriteria reconciliationCriteria) throws Throwable {
        if (this.systemOfRecordRepository != null) {
            try {
                // TODO: this is a bit of a hack to make sure the tests pass.
                final SoRSpecification soRSpecification = this.systemOfRecordRepository.findSoRSpecificationById(reconciliationCriteria.getSorPerson().getSourceSor());
                SystemOfRecordHolder.setCurrentSystemOfRecord(soRSpecification);
                return proceedingJoinPoint.proceed();
            } finally {
                SystemOfRecordHolder.clearCurrentSystemOfRecord();
            }
        } else {
            return proceedingJoinPoint.proceed();
        }
    }

    public void setSystemOfRecordRepository(final SystemOfRecordRepository systemOfRecordRepository) {
        this.systemOfRecordRepository = systemOfRecordRepository;
    }
}
