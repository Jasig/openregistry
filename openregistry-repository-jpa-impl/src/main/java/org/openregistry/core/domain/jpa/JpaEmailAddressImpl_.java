package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaEmailAddressImpl.class)
public abstract class JpaEmailAddressImpl_ {

	public static volatile SingularAttribute<JpaEmailAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaEmailAddressImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaEmailAddressImpl, String> address;
	public static volatile SingularAttribute<JpaEmailAddressImpl, JpaRoleImpl> role;

}

