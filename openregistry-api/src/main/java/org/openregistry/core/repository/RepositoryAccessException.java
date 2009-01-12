package org.openregistry.core.repository;

/**
 * Runtime exception signaling the exceptional conditions during repositories
 * method invocations. Such events are usually unrecoverable e.g. database resource is down, etc.
 * @since 1.0
 */
public class RepositoryAccessException extends RuntimeException {
}
