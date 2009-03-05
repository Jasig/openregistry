package org.openregistry.core.factory.jpa;

import org.openregistry.core.factory.PersonFactory;
import org.openregistry.core.domain.jpa.JpaPersonImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Mar 5, 2009
 * Time: 11:50:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class JpaPersonFactory implements PersonFactory {

    public JpaPersonImpl createPerson(){
        return new JpaPersonImpl();
    }
}
