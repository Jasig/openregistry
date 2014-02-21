package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.service.identitycard.IdCardGenerator;

import java.security.SecureRandom;

/**
 * Default Implementation of Id card generator *
 */
public class DefaultIdCardGenerator implements IdCardGenerator {

    private static SecureRandom secureRandom=new SecureRandom();


    private static final char[] RANDOMD_CHARS = "0123456789".toCharArray();
    private int idCardRandomNumberLength=15;
    private int cardSecurityValueRandomNumberLength=6;
    private int bardCodeValueRandomNumberLength=9;
    @Override
    public void addIDCard(Person person) {
        person.addIDCard(generateCardNumber(),generateCardSecurityValue(),generateBarCode());
    }
   private String generateCardNumber(){
        return  constructRandomNumber(idCardRandomNumberLength);
    }
    private String generateCardSecurityValue(){
        return constructRandomNumber(cardSecurityValueRandomNumberLength);
    }
    private String generateBarCode(){
        return constructRandomNumber(bardCodeValueRandomNumberLength);
    }
    private String constructRandomNumber(int randomNumberLength){
        byte[] random = new byte[randomNumberLength];
        secureRandom.nextBytes(random);
        return convertBytesToString(random);

    }
    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % RANDOMD_CHARS.length);
            output[i] = RANDOMD_CHARS[index];
        }

        return new String(output);
    }
}
