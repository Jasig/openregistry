package org.openregistry.core.domain.validation;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 4/18/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IdentifierFormatValidator {
	public boolean isIIDFormat(String netID);
	public boolean isNetIDAcceptable(String netID);
}
