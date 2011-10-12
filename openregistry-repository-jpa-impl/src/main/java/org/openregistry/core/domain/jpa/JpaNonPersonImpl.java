package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.NonPerson;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 8/23/11
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class JpaNonPersonImpl extends Entity implements NonPerson{
    @Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getSponsorId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Type getSponsorType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getAffiliationDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getTerminationDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSponsorId(Long sponsorId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSponsorType(Type sponsorType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAffiliationDate(Date affiliationDate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setTerminationDate(Date terminationDate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
