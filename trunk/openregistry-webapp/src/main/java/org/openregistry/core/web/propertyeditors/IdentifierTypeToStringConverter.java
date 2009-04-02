package org.openregistry.core.web.propertyeditors;

import org.springframework.binding.convert.converters.Converter;
import org.openregistry.core.domain.IdentifierType;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Apr 1, 2009
 * Time: 4:12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdentifierTypeToStringConverter implements Converter {

    public Class getSourceClass() {
        return IdentifierType.class;
    }

    public Class getTargetClass() {
        return String.class;
    }

    public Object convertSourceToTargetClass(final Object o, final Class aClass) throws Exception {
        return this.toString();
    }
}
