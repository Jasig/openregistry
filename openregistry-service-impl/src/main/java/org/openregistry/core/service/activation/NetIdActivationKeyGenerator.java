package org.openregistry.core.service.activation;

import org.openregistry.core.service.activation.ActivationKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * NetId Activation Key Generator
 */
@Component
public final class NetIdActivationKeyGenerator implements ActivationKeyGenerator {

    public String generateNextString(){

        String key = UUID.randomUUID().toString().substring(0,8);

        // Filter out 0's and 1's
        key.replaceAll("0","m") ;
        key.replaceAll("1","n");

        return key;
    }

}