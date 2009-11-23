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
package org.openregistry.core.service;

import org.openregistry.core.repository.SystemOfRecordRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Implementation of the {@link javax.validation.ConstraintValidatorFactory} that determines, based on the SoR's
 * specifications, whether to execute the constraint validator or not.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Named("constraintValidatorFactory")
public final class SoRAwareConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    private final ConstraintValidatorFactory delegatedConstraintValidatorFactory;

    private final SystemOfRecordRepository systemOfRecordRepository;

    @Inject
    public SoRAwareConstraintValidatorFactoryImpl(final ConstraintValidatorFactory delegatedConstraintValidatorFactory, final SystemOfRecordRepository systemOfRecordRepository) {
        this.delegatedConstraintValidatorFactory = delegatedConstraintValidatorFactory;
        this.systemOfRecordRepository = systemOfRecordRepository;
    }

    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> tClass) {
        return wrap(this.delegatedConstraintValidatorFactory.getInstance(tClass));
    }

    protected <T extends ConstraintValidator<?, ?>> T wrap(final T constraintValidator) {
        return (T) Proxy.newProxyInstance(constraintValidator.getClass().getClassLoader(), constraintValidator.getClass().getInterfaces(), new InvocationHandler() {

            public Object invoke(final Object o, final Method method, final Object[] objects) throws Throwable {
                if (method.getName().equals("initialize")) {
                    return method.invoke(constraintValidator, objects);
                }

                // do some magic to determine if we should call validate
                return method.invoke(constraintValidator, objects);
            }
        });
    }
}
