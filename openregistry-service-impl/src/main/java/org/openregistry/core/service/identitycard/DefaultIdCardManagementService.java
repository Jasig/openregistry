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
        return new GeneralServiceExecutionResult<IdCard>(p.getIdCards().iterator().next());

    }

    @Override
    public ServiceExecutionResult<IdCard>  assignProximityNumber(Person p,String proximityNumber){
        p.getIdCards().iterator().next().setProximityNumber(proximityNumber);
        return new GeneralServiceExecutionResult<IdCard>(p.getIdCards().iterator().next());

    }

    @Override
    public ServiceExecutionResult<IdCard> expireIdCard(Person p) {
        p.getIdCards().iterator().next().setExpirationDate(new Date()) ;
        return new GeneralServiceExecutionResult<IdCard>(p.getIdCards().iterator().next());
    }
}
