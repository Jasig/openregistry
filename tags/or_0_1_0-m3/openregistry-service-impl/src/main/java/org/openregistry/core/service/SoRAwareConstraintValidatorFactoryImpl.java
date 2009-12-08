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

import org.openregistry.core.domain.validation.SystemOfRecordRepositoryAware;
import org.openregistry.core.repository.SystemOfRecordRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

/**
 * Implementation of the {@link javax.validation.ConstraintValidatorFactory} that determines, based on the SoR's
 * specifications, whether to execute the constraint validator or not.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class SoRAwareConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    private final ConstraintValidatorFactory delegatedConstraintValidatorFactory;

    private final SystemOfRecordRepository systemOfRecordRepository;

    public SoRAwareConstraintValidatorFactoryImpl(final ConstraintValidatorFactory delegatedConstraintValidatorFactory, final SystemOfRecordRepository systemOfRecordRepository) {
        this.delegatedConstraintValidatorFactory = delegatedConstraintValidatorFactory;
        this.systemOfRecordRepository = systemOfRecordRepository;
    }

    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> tClass) {
        final T constraintValidator = this.delegatedConstraintValidatorFactory.getInstance(tClass);

        if (constraintValidator instanceof SystemOfRecordRepositoryAware) {
            ((SystemOfRecordRepositoryAware) constraintValidator).setSystemOfRecordRepository(this.systemOfRecordRepository);
        }

        return constraintValidator;
    }
}
