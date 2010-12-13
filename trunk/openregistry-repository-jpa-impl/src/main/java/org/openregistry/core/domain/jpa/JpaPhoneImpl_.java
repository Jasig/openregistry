package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaPhoneImpl.class)
public abstract class JpaPhoneImpl_ {

	public static volatile SingularAttribute<JpaPhoneImpl, Long> id;
	public static volatile SingularAttribute<JpaPhoneImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaPhoneImpl, JpaTypeImpl> phoneType;
    public static volatile SingularAttribute<JpaPhoneImpl, Integer> phoneLineOrder;
	public static volatile SingularAttribute<JpaPhoneImpl, String> countryCode;
	public static volatile SingularAttribute<JpaPhoneImpl, String> areaCode;
	public static volatile SingularAttribute<JpaPhoneImpl, String> number;
	public static volatile SingularAttribute<JpaPhoneImpl, String> extension;
	public static volatile SingularAttribute<JpaPhoneImpl, JpaRoleImpl> role;

}

