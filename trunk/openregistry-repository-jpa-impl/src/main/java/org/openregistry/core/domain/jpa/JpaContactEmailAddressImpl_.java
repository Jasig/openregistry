package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaContactEmailAddressImpl.class)
public abstract class JpaContactEmailAddressImpl_ {

    public static volatile SingularAttribute<JpaContactEmailAddressImpl, Long> id;
    public static volatile SingularAttribute<JpaContactEmailAddressImpl, JpaTypeImpl> type;
    public static volatile SingularAttribute<JpaContactEmailAddressImpl, String> address;

}

