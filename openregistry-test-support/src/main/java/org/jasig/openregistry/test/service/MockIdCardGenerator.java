package org.jasig.openregistry.test.service;

import org.jasig.openregistry.test.domain.MockIdCard;
import org.mockito.Mockito.*;
import org.openregistry.core.domain.IdCard;
import org.openregistry.core.domain.Person;
import org.openregistry.core.service.identitycard.IdCardGenerator;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: msidd
 * Date: 4/16/13
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockIdCardGenerator implements IdCardGenerator {
    @Override
    public void addIDCard(Person person) {
       //DO NOTHING
    }
}
