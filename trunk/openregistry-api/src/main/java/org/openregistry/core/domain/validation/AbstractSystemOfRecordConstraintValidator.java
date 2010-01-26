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
package org.openregistry.core.domain.validation;

import org.openregistry.core.domain.sor.SoRSpecification;
import org.openregistry.core.domain.sor.SystemOfRecord;
import org.openregistry.core.domain.sor.SystemOfRecordHolder;
import org.openregistry.core.repository.SystemOfRecordRepository;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * Abstract class that exposes a method for obtaining the SoRSpecification.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractSystemOfRecordConstraintValidator<A extends Annotation,T> implements ConstraintValidator<A,T>, SystemOfRecordRepositoryAware {

    private SystemOfRecordRepository systemOfRecordRepository;

    protected SoRSpecification getSoRSpecification() {
        if (SystemOfRecordHolder.getCurrentSystemOfRecord() != null) {
            return this.systemOfRecordRepository.findSoRSpecificationById(SystemOfRecordHolder.getCurrentSystemOfRecord().getStringId());
        }

        // throw new IllegalStateException("No System Of Record found that can be used to validate these constraints.");

        // TODO this should be replaced with  the IllegalStateException above
        return new SoRSpecification() {

            public String getSoR() {
                return "foo";
            }

            public boolean isAllowedValueForProperty(String property, String value) {
                return true;
            }

            public boolean isRequiredProperty(String property) {
                return false;
            }

            public boolean isWithinRequiredSize(String property, Collection collection) {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean isInboundInterfaceAllowed(SystemOfRecord.Interfaces interfaces) {
                return true;
            }

            public Map<SystemOfRecord.Interfaces, String> getNotificationSchemesByInterface() {
                return null;
            }

            public boolean isDisallowedProperty(String property) {
                return false;
            }

            @Override
            public String getName() {
                return "Name";
            }

            @Override
            public String getDescription() {
                return "description";
            }
        };
    }

    public final void setSystemOfRecordRepository(final SystemOfRecordRepository systemOfRecordRepository) {
        this.systemOfRecordRepository = systemOfRecordRepository;
    }
}
