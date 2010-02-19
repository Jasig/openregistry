package org.openregistry.core.web.converters;

import org.springframework.binding.convert.converters.Converter;

/**
 * Converts an empty String that is passed in to a NULL object.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class TrimStringToStringConverter implements Converter {
    @Override
    public Class getSourceClass() {
        return String.class;
    }

    @Override
    public Class getTargetClass() {
        return String.class;
    }

    @Override
    public Object convertSourceToTargetClass(final Object o, final Class aClass) throws Exception {
        if (o == null) {
            return o;
        }

        final String s = (String) o;
        if (s.isEmpty()) {
            return null;
        }

        return s.trim();
    }
}
