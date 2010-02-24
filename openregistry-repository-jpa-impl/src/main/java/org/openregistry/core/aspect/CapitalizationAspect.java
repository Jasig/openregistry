package org.openregistry.core.aspect;

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

    public static enum Capitalization {UPPER, LOWER, NORMAL, NONE}

    private Capitalization capitalization = Capitalization.NONE;

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

            case NORMAL:
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
        if (!value.contains(" ")) {
            // we just need to capitalized the first letter and lowercase everything else
        }

        final StringBuilder builder = new StringBuilder(value.length());
        final String[] separated = value.split(" ");

        for (final String v : separated) {
            final String start = v.substring(0,1);

            builder.append(start.toUpperCase());

            if (v.length() > 1) {
                final String rest = v.substring(1);
                builder.append(rest.toLowerCase());
            }

            builder.append(" ");
        }
        final String finalString = builder.toString();
        return finalString.substring(0, finalString.length()-1);
    }
}
