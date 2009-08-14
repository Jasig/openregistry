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
package org.openregistry.aspect;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * Makes the message source accessor available to any class that requires it via a static getter.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

public final class OpenRegistryMessageSourceAccessor implements MessageSourceAware {

private static MessageSourceAccessor MESSAGE_SOURCE_ACCESSOR;

    public void setMessageSource(final MessageSource messageSource) {
        MESSAGE_SOURCE_ACCESSOR = new MessageSourceAccessor(messageSource);
    }

    public static MessageSourceAccessor getMessageSourceAccessor() {
        return MESSAGE_SOURCE_ACCESSOR;
    }
}
