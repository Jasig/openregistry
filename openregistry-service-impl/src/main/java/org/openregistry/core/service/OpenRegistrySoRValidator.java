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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.*;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Named("openRegistryValidator")
public final class OpenRegistrySoRValidator<T> extends LocalValidatorFactoryBean {

    @Inject
    public OpenRegistrySoRValidator(final SystemOfRecordRepository systemOfRecordRepository) {
        final Configuration configuration = Validation.byDefaultProvider().configure();
        final ConstraintValidatorFactory c = configuration.getDefaultConstraintValidatorFactory();
        final ConstraintValidatorFactory sorAware = new SoRAwareConstraintValidatorFactoryImpl(c, systemOfRecordRepository);
        setConstraintValidatorFactory(sorAware);
    }
}
