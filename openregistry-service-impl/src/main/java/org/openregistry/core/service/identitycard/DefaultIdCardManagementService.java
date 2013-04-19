package org.openregistry.core.service.identitycard;

import org.openregistry.core.domain.IdCard;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorDisclosureSettings;
import org.openregistry.core.service.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Named;
import java.util.Date;
import  org.springframework.util.*;

import static java.lang.String.format;

/**
 * Default implementation of ID card management service
 */

@Named("idCardService")
@Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultIdCardManagementService implements  IdCardManagementService {

    @Resource(name="idCardGenerator")
    private IdCardGenerator idCardGenerator = new DefaultIdCardGenerator();
    @Override
    public ServiceExecutionResult<IdCard> generateNewIdCard(Person p) {
        this.idCardGenerator.addIDCard(p);
        return new GeneralServiceExecutionResult<IdCard>(p.getPrimaryIdCard());

    }

    @Override
    public ServiceExecutionResult<IdCard>  assignProximityNumber(Person p,String proximityNumber){
        IdCard card =p.getPrimaryIdCard();
        if(card==null)      throw new IllegalStateException(format("Primary Id card doesn't exist for this person"));
        card.setProximityNumber(proximityNumber);
        return new GeneralServiceExecutionResult<IdCard>(card);

    }

    @Override
    public ServiceExecutionResult<IdCard> expireIdCard(Person p) {
        IdCard card =p.getPrimaryIdCard();
        if(card==null)      throw new IllegalStateException(format("Primary Id card doesn't exist for this person"));
        card.setExpirationDate(new Date()) ;
        return new GeneralServiceExecutionResult<IdCard>(card);
    }
}
