package org.openregistry.core.aspect;

import org.apache.commons.lang.WordUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openregistry.core.domain.normalization.Capitalize;

import java.util.HashMap;
import java.util.Map;

/**
 * Aspect to apply capitalization rules.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Aspect
public final class CapitalizationAspect {

    public static enum Capitalization {UPPER, LOWER, CAPITALIZE, NONE}

    private Capitalization capitalization = Capitalization.CAPITALIZE;

    private Map<String, Capitalization> overrideCapitalization = new HashMap<String,Capitalization>();

    @Around("set(* *) && @annotation(capitalize)")
    public Object transformFieldValue(final ProceedingJoinPoint joinPoint, final Capitalize capitalize) throws Throwable {
        final String newValue = (String) joinPoint.getArgs()[0];

        if (newValue == null) {
            return joinPoint.proceed();
        }

        final Capitalization customCapitalization = this.overrideCapitalization.get(capitalize.property());

        final Capitalization capitalizationToUse;
        if (customCapitalization != null) {
            capitalizationToUse = customCapitalization;
        } else {
            capitalizationToUse = this.capitalization;
        }

        switch (capitalizationToUse) {

            case UPPER:
                return joinPoint.proceed(new Object[] {doUpperCaseCapitalization(newValue)});

            case LOWER:
                return joinPoint.proceed(new Object[] {doLowerCaseCapitalization(newValue)});

            case CAPITALIZE:
                return joinPoint.proceed(new Object[] {doNormalCaseCapitalization(newValue)});

            default:
                return joinPoint.proceed();
        }
    }

    public void setDefaultCapitalization(final Capitalization capitalization) {
        this.capitalization = capitalization;
    }

    public void setOverrideCapitalization(final Map<String, Capitalization> overrideCapitalization) {
        this.overrideCapitalization = overrideCapitalization;
    }

    protected String doUpperCaseCapitalization(final String value) {
        return value.toUpperCase();
    }

    protected String doLowerCaseCapitalization(final String value) {
        return value.toLowerCase();
    }

    protected String doNormalCaseCapitalization(final String value) {
        return WordUtils.capitalizeFully(value);
    }
}
