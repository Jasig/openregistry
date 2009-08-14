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
package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Abstract class that supports the notion that a property editor will need access to the reference
 * repository to obtain its information.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public abstract class AbstractReferenceRepositoryPropertyEditor extends PropertyEditorSupport {

    private final ReferenceRepository referenceRepository;

    protected AbstractReferenceRepositoryPropertyEditor(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    protected ReferenceRepository getReferenceRepository() {
        return this.referenceRepository;
    }

    @Override
    public final void setAsText(final String s) throws IllegalArgumentException {
        if (StringUtils.hasText(s)) {
            setAsTextInternal(s);
        } else {
            setValue(null);
        }
    }

    protected abstract void setAsTextInternal(String s);
}
