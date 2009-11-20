package org.openregistry.core.service;

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
@Named("constraintValidatorFactory")
public final class SoRAwareConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    private final ConstraintValidatorFactory delegatedConstraintValidatorFactory;

    @Inject
    public SoRAwareConstraintValidatorFactoryImpl(final ConstraintValidatorFactory delegatedConstraintValidatorFactory) {
        this.delegatedConstraintValidatorFactory = delegatedConstraintValidatorFactory;
    }

    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> tClass) {
        return wrap(this.delegatedConstraintValidatorFactory.getInstance(tClass));
    }

    protected <T extends ConstraintValidator<?, ?>> T wrap(final T constraintValidator) {
        return constraintValidator;
    }
}
