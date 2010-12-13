package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaContactPhoneImpl.class)
public abstract class JpaContactPhoneImpl_ {

    public static volatile SingularAttribute<JpaContactPhoneImpl, Long> id;
    public static volatile SingularAttribute<JpaContactPhoneImpl, JpaTypeImpl> addressType;
    public static volatile SingularAttribute<JpaContactPhoneImpl, JpaTypeImpl> phoneType;
    public static volatile SingularAttribute<JpaContactPhoneImpl, Integer> phoneLineOrder;
    public static volatile SingularAttribute<JpaContactPhoneImpl, String> countryCode;
    public static volatile SingularAttribute<JpaContactPhoneImpl, String> areaCode;
    public static volatile SingularAttribute<JpaContactPhoneImpl, String> number;
    public static volatile SingularAttribute<JpaContactPhoneImpl, String> extension;

}

