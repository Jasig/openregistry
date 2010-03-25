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

package org.openregistry.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;

import java.util.Locale;

/**
 * Applies TRACE, INFO and ERROR logging.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Aspect
public class LogAspect {

    private static final String TRACE_METHOD_BEGIN = "trace.enter.method";

    private static final String TRACE_METHOD_END = "trace.leave.method";

    private MessageSourceAccessor messageSourceAccessor;
 
    @Around("(execution (public * org.openregistry.core..*.*(..))) && !(execution( * org.openregistry.core..*.set*(..)))")
    public Object traceMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnVal = null;
        final Logger log = getLog(proceedingJoinPoint);
        final String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            if (log.isTraceEnabled()) {
                final Object[] args = proceedingJoinPoint.getArgs();
                if (args == null || args.length == 0) {
                    log.trace(this.messageSourceAccessor.getMessage(TRACE_METHOD_BEGIN, new Object[] {methodName, ""}, Locale.getDefault()));
                } else {
                    final StringBuilder stringBuilder = new StringBuilder();
                    for (final Object o : args) {
                        stringBuilder.append(o != null ? o.toString() : null).append(", ");
                    }
                    final String argString = stringBuilder.substring(0, stringBuilder.length()-3);
                    log.trace(this.messageSourceAccessor.getMessage(TRACE_METHOD_BEGIN, new Object[] {methodName, argString}, Locale.getDefault()));
                }
            }
            returnVal = proceedingJoinPoint.proceed();
            return returnVal;
        } finally {
            if (log.isTraceEnabled()) {
                log.trace(this.messageSourceAccessor.getMessage(TRACE_METHOD_END, new Object[] {methodName, (returnVal != null ? returnVal.toString() : "null")}, Locale.getDefault()));
            }
        }
    }

    protected Logger getLog(final JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();

        if (target != null) {
            return LoggerFactory.getLogger(target.getClass());
        }
        
        return LoggerFactory.getLogger(getClass());
    }

    @Around("(within(org.openregistry.core.service.PersonService+) && (execution (* *(..))))")
    public Object logInfoPersonService(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retVal = null;
        final String methodName = proceedingJoinPoint.getSignature().getName();
        final Logger log = getLog(proceedingJoinPoint);
        try {

            if (log.isInfoEnabled()) {
                final Object arg0;

                if (proceedingJoinPoint.getArgs().length > 0) {
                    arg0 = proceedingJoinPoint.getArgs()[0];
                } else {
                    arg0 = null;
                }
                final String argumentString = arg0 == null ? "null" : arg0.toString();
                log.info(this.messageSourceAccessor.getMessage(TRACE_METHOD_BEGIN, new Object[] {methodName, argumentString}, Locale.getDefault()));
            }

            retVal = proceedingJoinPoint.proceed();
            return retVal;
        } finally {

            if (log.isInfoEnabled()) {
                log.info(this.messageSourceAccessor.getMessage(TRACE_METHOD_END, new Object[]{methodName, (retVal == null ? "null" : retVal.toString())}, Locale.getDefault()));
            }
        }
    }


    @AfterThrowing(pointcut = "(execution (* org.openregistry.core..*.*(..)))", throwing="throwable")
    public void logErrorFromThrownException(final JoinPoint joinPoint, final Throwable throwable) {
        getLog(joinPoint).error(throwable.getMessage(),throwable);
    }

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
}
