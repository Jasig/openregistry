package org.openregistry.core.domain;

/**
 * Exception thrown when a lock can't be acquired for an activation key or if an action can't be performed because you're
 * not the one holding the lock.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class LockingException extends RuntimeException {

    public LockingException() {
    }

    public LockingException(final String s) {
        super(s);
    }

    public LockingException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public LockingException(final Throwable throwable) {
        super(throwable);
    }
}
