package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaTypeImpl.class)
public abstract class JpaTypeImpl_ {

	public static volatile SingularAttribute<JpaTypeImpl, Long> id;
	public static volatile SingularAttribute<JpaTypeImpl, String> dataType;
	public static volatile SingularAttribute<JpaTypeImpl, String> description;
	public static volatile ListAttribute<JpaTypeImpl, JpaAddressImpl> addresses;
	public static volatile ListAttribute<JpaTypeImpl, JpaNameImpl> names;
	public static volatile ListAttribute<JpaTypeImpl, JpaOrganizationalUnitImpl> organizationalUnits;
	public static volatile ListAttribute<JpaTypeImpl, JpaEmailAddressImpl> emailAddresses;
	public static volatile ListAttribute<JpaTypeImpl, JpaLeaveImpl> leaves;
	public static volatile ListAttribute<JpaTypeImpl, JpaPhoneImpl> phoneTypes;
	public static volatile ListAttribute<JpaTypeImpl, JpaPhoneImpl> phoneAddressTypes;
	public static volatile ListAttribute<JpaTypeImpl, JpaRoleImpl> rolePersonStatuses;
	public static volatile ListAttribute<JpaTypeImpl, JpaRoleInfoImpl> roleInfoAffiliationTypes;
	public static volatile ListAttribute<JpaTypeImpl, JpaUrlImpl> urls;
	public static volatile ListAttribute<JpaTypeImpl, JpaRoleImpl> roleTerminationTypes;

}

