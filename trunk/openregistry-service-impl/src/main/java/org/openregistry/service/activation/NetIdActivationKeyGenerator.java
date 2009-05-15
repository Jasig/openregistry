package org.openregistry.service.activation;

import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.activation.ActivationKeyGenerator;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Name;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Random;
import java.security.SecureRandom;

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