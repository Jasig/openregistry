package org.openregistry.core.domain.normalization;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Feb 23, 2010
 * Time: 9:55:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RUNTIME)
@Target({FIELD})
public @interface Capitalize {

    String property();
}
