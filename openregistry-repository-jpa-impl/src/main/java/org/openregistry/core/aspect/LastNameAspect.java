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

package org.openregistry.core.aspect;

import org.apache.commons.lang.StringUtils;
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
	
	final char[] delimiters = {' ', '-', '\'', '.'};

    @Around("set(@org.openregistry.core.domain.normalization.LastName * *)")
    public Object transformFieldValue(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String value = (String) joinPoint.getArgs()[0];

        if (isDisabled() || value == null || value.isEmpty()) {
            return joinPoint.proceed();
        }

        final String overrideValue = getCustomMapping().get(value);

        if (overrideValue != null) {
            return joinPoint.proceed(new Object[]{overrideValue});
        }


        if (StringUtils.containsAny(value,delimiters)) {


            final String casifyValue = WordUtils.capitalizeFully(value, delimiters);

            if (casifyValue.startsWith("Mc") && casifyValue.length() > 2) {
                return joinPoint.proceed(new Object[]{"Mc" + WordUtils.capitalizeFully(casifyValue.substring(2), delimiters)});

            }

            return joinPoint.proceed(new Object[]{casifyValue});
        } else {
            final String casifyValue = WordUtils.capitalizeFully(value);

            if (casifyValue.startsWith("Mc") && casifyValue.length() > 2) {
                return joinPoint.proceed(new Object[]{"Mc" + WordUtils.capitalizeFully(casifyValue.substring(2))});

            }

            return joinPoint.proceed(new Object[]{casifyValue});

        }
    }
}
