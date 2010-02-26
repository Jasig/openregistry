package org.openregistry.core.aspect;

import org.apache.commons.lang.WordUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Changes the last name to confirm to particular cases when capitalization is set to normal.  If its set to UPPER or
 * LOWER, this should be disabled.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Aspect
public final class LastNameAspect extends AbstractNameAspect {

    @Around("set(@org.openregistry.core.domain.normalization.LastName * *)")
    public Object transformFieldValue(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String value = (String) joinPoint.getArgs()[0];

        if (isDisabled() || value == null || value.isEmpty()) {
            return joinPoint.proceed();
        }

        final String overrideValue = getCustomMapping().get(value);

        if (overrideValue != null) {
            return joinPoint.proceed(new Object[] {overrideValue});
        }

        final String casifyValue = WordUtils.capitalizeFully(value);

        if (casifyValue.startsWith("Mc") && casifyValue.length() > 2) {
            return joinPoint.proceed(new Object[] {"Mc" + WordUtils.capitalizeFully(casifyValue.substring(3))});

        }

        return joinPoint.proceed(new Object[] {casifyValue});
    }
}
