package org.openregistry.core.service;

import org.javalid.core.ValidationMessage;

import java.util.List;

/**
 * A container encapsulating results of any number of different Open Registry public service API invocations.
 * Such results are validation errors if any, etc.
 *
 * @since 1.0
 *        TODO: define in more concrete terms what this object encapsulates.
 */
public interface ServiceExecutionResult {

    /**
     * TODO: DOCUMENT ME!
     * @return
     */
    List<ValidationMessage> getErrorList();
}
