package org.openregistry.core.aspect;

import org.apache.commons.lang.WordUtils;
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
public final class FirstNameAspect extends AbstractNameAspect {

    @Around("set(@org.openregistry.core.domain.normalization.FirstName * *)")
    public Object transformFieldValue(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String value = (String) joinPoint.getArgs()[0];

        if (isDisabled() || value == null || value.isEmpty()) {
            return joinPoint.proceed();
        }

        // TODO replace this with a dictionary or something else that doesn't require explicit configuration
        final String overrideValue = getCustomMapping().get(value);

        if (overrideValue != null) {
            return joinPoint.proceed(new Object[] {overrideValue});
        }

        return joinPoint.proceed(new Object[] {WordUtils.capitalizeFully(value)});
    }
}


