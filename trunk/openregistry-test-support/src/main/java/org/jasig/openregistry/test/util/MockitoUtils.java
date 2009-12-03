package org.jasig.openregistry.test.util;

import org.mockito.Mockito;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public final class MockitoUtils {


    private MockitoUtils() {
    }

    public static Set<ConstraintViolation> oneMinimalisticMockConstraintViolation() {

        final ConstraintViolation mockConstraintViolation = Mockito.mock(ConstraintViolation.class, Mockito.RETURNS_SMART_NULLS);
        final Path mockPath = Mockito.mock(Path.class);
        Mockito.when(mockPath.toString()).thenReturn("test.property.path");
        Mockito.when(mockConstraintViolation.getPropertyPath()).thenReturn(mockPath);

        Set<ConstraintViolation> setWithErrors = new TreeSet<ConstraintViolation>();
        setWithErrors.add(mockConstraintViolation);
        return setWithErrors;
    }
}
