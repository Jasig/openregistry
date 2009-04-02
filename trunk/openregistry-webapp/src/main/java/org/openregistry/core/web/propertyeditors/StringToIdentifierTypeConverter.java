package org.openregistry.core.web.propertyeditors;

import org.springframework.binding.convert.converters.Converter;
import org.springframework.util.Assert;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.IdentifierType;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Apr 2, 2009
 * Time: 11:02:54 AM
 * To change this template use File | Settings | File Templates.
 */
public final class StringToIdentifierTypeConverter implements Converter {

    private ReferenceRepository referenceRepository;

    public StringToIdentifierTypeConverter(final ReferenceRepository referenceRepository) {
        Assert.notNull(referenceRepository);
        this.referenceRepository = referenceRepository;
    }

    public Class getSourceClass() {
        return String.class;
    }

    public Class getTargetClass() {
        return IdentifierType.class;
    }

    public Object convertSourceToTargetClass(final Object o, final Class aClass) throws Exception {
        final String identifierName = (String) o;
        return this.referenceRepository.findIdentifierType(identifierName);
    }
}
