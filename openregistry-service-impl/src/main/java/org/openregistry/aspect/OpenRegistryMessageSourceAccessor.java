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
