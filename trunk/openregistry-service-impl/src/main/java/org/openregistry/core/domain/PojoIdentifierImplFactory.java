package org.openregistry.core.domain;

import org.apache.commons.collections15.Factory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Apr 1, 2009
 * Time: 3:50:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PojoIdentifierImplFactory implements Factory<PojoIdentifierImpl>, Serializable {

    public PojoIdentifierImpl create() {
        return new PojoIdentifierImpl();
    }
}
