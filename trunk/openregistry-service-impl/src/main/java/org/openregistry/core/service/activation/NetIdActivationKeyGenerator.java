package org.openregistry.core.service.activation;

import org.openregistry.core.service.activation.ActivationKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * NetId Activation Key Generator
 */
@Component
public final class NetIdActivationKeyGenerator implements ActivationKeyGenerator {

    private Random random;

    public NetIdActivationKeyGenerator(){
        random = new Random();
    }

    public String generateNextString(){
        String hash = Long.toHexString(random.nextLong());
        
        // Filter out 0's, o's, 1's, l's
        hash.replaceAll("0","a") ;
        hash.replaceAll("o","b") ;
        hash.replaceAll("O","c") ;
        hash.replaceAll("1","d");
        hash.replaceAll("l","e");

        return hash.substring(0,8);
    }

}