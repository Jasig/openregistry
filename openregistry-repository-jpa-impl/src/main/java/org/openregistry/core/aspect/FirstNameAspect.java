package org.openregistry.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Changes the first name to confirm to particular cases when capitalization is set to normal.  If its set to UPPER or
 * LOWER, this should be disabled.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Aspect
public final class FirstNameAspect {

    private boolean disable = false;

    @Around("set(@org.openregistry.core.domain.normalization.FirstName * *)")
    public Object transformFieldValue(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String value = (String) joinPoint.getArgs()[0];

        if (this.disable || value == null || value.isEmpty()) {
            return joinPoint.proceed();
        }

        // TODO we need Dave's algorithm
        return joinPoint.proceed();
    }

    public void setDisable(final boolean disable) {
        this.disable = disable;
    }
}


