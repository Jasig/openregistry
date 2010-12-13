package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorPhoneImpl.class)
public abstract class JpaSorPhoneImpl_ {

	public static volatile SingularAttribute<JpaSorPhoneImpl, Long> id;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaTypeImpl> phoneType;
    public static volatile SingularAttribute<JpaSorPhoneImpl, Integer> phoneLineOrder;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> countryCode;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> areaCode;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> number;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> extension;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaSorRoleImpl> sorRole;

}

