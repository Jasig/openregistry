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

/**
 * Removes the extraneous characters from the Phone number in oder to persist it.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class PhoneEditor extends AbstractRegExPropertyEditor {

    /** Regular expression used to identify range of tag characters. */
    private static final String REG_EX_TAGS = "[()-. \t]";

    public PhoneEditor() {
        super(REG_EX_TAGS);
    }
}

