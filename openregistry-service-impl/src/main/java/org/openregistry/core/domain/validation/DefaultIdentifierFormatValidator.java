package org.openregistry.core.domain.validation;

import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: msidd
 * Date: 8/21/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */


public class DefaultIdentifierFormatValidator implements IdentifierFormatValidator {

    static final String DEFAULT_IID_PATTEN= "\\d+[a-z]*";
    static final String DEFAULT_NET_ID_PATTERN = "[a-z][a-z][a-z]\\d+";

    @Override
    public boolean isIIDFormat(String netID) {
        return netID.matches(DEFAULT_IID_PATTEN);
    }

    @Override
    public boolean isNetIDAcceptable(String netID) {
        return netID.matches(DEFAULT_NET_ID_PATTERN);
    }
}
