package org.openregistry.core.service.security.auth;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * User: msidd
 * Date: Sep 14, 2010
 * Time: 9:59:49 AM
 * A permission evaluator to be used on service layer without using spring's ACL module
 * Default implementation simply return true for all invocations, i.e. doesn't authorize filter anything
 * Implementation would be institute specific
 */

public class DefaultPermissionEvaluator implements PermissionEvaluator{
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return true;  
    }
}
