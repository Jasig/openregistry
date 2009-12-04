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
import javax.validation.*;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Named("openRegistryValidator")
public final class OpenRegistrySoRValidator<T> implements Validator {

    private final Validator validator;

    @Inject
    public OpenRegistrySoRValidator(final SystemOfRecordRepository systemOfRecordRepository) {
        final Configuration configuration = Validation.byDefaultProvider().configure();
        final ConstraintValidatorFactory c = Validation.byDefaultProvider().configure().getDefaultConstraintValidatorFactory();
        final ConstraintValidatorFactory sorAware = new SoRAwareConstraintValidatorFactoryImpl(c, systemOfRecordRepository);
        configuration.constraintValidatorFactory(sorAware);
        final ValidatorFactory v = configuration.buildValidatorFactory();
        this.validator = v.getValidator();
    }

    public <T> Set<ConstraintViolation<T>> validate(final T t, final Class<?>... classes) {
        return this.validator.validate(t, classes);
    }

    public <T> Set<ConstraintViolation<T>> validateProperty(final T t, final String s, final Class<?>... classes) {
        return this.validator.validateProperty(t, s, classes);
    }

    public <T> Set<ConstraintViolation<T>> validateValue(final Class<T> tClass, final String s, final Object o, final Class<?>... classes) {
        return this.validator.validateValue(tClass, s, o, classes);
    }

    public BeanDescriptor getConstraintsForClass(final Class<?> aClass) {
        return this.validator.getConstraintsForClass(aClass);
    }

    public <T> T unwrap(final Class<T> tClass) {
        return this.validator.unwrap(tClass);
    }
}
