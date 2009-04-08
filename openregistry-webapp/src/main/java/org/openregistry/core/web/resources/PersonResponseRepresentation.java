package org.openregistry.core.web.resources;

import java.io.Serializable;

/**
 * Simple Java Bean encapsulating the representation of persons in the registry
 * for the purpose of exposing it via RESTful resources.
 * <p/>
 * This class is immutable
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class PersonResponseRepresentation implements Serializable {

    private Long rcpId;

    private String netId;

    private String activationGeneratorUri;

    private String activationProcessorUri;

    public PersonResponseRepresentation(Long rcpId, String netId, String activationGeneratorUri,
                                        String activationProcessorUri) {
        this.rcpId = rcpId;
        this.netId = netId;
        this.activationGeneratorUri = activationGeneratorUri;
        this.activationProcessorUri = activationProcessorUri;
    }

    public Long getRcpId() {
        return rcpId;
    }

    public String getNetId() {
        return netId;
    }

    public String getActivationGeneratorUri() {
        return activationGeneratorUri;
    }

    public String getActivationProcessorUri() {
        return activationProcessorUri;
    }
}
