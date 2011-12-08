package org.openregistry.core.authorization;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 12/6/11
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationException extends Exception {
    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
