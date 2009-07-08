package org.openregistry.core.domain;

/**
 * Exception to be thrown if a Person is Not Found, when its expected that they
 * should be found.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
    }

    public PersonNotFoundException(final String s) {
        super(s);
    }

    public PersonNotFoundException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public PersonNotFoundException(final Throwable throwable) {
        super(throwable);
    }
}
