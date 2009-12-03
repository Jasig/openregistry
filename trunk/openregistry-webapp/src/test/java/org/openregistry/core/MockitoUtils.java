package org.openregistry.core;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public final class MockitoUtils {


    private MockitoUtils() {
    }

    public static Set<ConstraintViolation> oneMinimalisticMockConstraintViolation() {

        final ConstraintViolation mockConstraintViolation = mock(ConstraintViolation.class, RETURNS_SMART_NULLS);
        final Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("test.property.path");
        when(mockConstraintViolation.getPropertyPath()).thenReturn(mockPath);

        Set<ConstraintViolation> setWithErrors = new TreeSet<ConstraintViolation>();
        setWithErrors.add(mockConstraintViolation);
        return setWithErrors;
    }
}
