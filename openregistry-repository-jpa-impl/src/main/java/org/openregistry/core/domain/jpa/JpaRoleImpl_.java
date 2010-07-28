package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Url;

@StaticMetamodel(JpaRoleImpl.class)
public abstract class JpaRoleImpl_ {

	public static volatile SingularAttribute<JpaRoleImpl, Long> id;
	public static volatile SetAttribute<JpaRoleImpl, Url> urls;
	public static volatile SetAttribute<JpaRoleImpl, EmailAddress> emailAddresses;
	public static volatile SetAttribute<JpaRoleImpl, Phone> phones;
	public static volatile SetAttribute<JpaRoleImpl, Address> addresses;
	public static volatile SingularAttribute<JpaRoleImpl, Long> sponsorId;
	public static volatile SingularAttribute<JpaRoleImpl, Type> sponsorType;
	public static volatile SingularAttribute<JpaRoleImpl, Integer> percentage;
	public static volatile SingularAttribute<JpaRoleImpl, Type> personStatus;
	public static volatile SetAttribute<JpaRoleImpl, Leave> leaves;
	public static volatile SingularAttribute<JpaRoleImpl, JpaRoleInfoImpl> roleInfo;
	public static volatile SingularAttribute<JpaRoleImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaRoleImpl, Date> start;
	public static volatile SingularAttribute<JpaRoleImpl, Date> end;
	public static volatile SingularAttribute<JpaRoleImpl, Type> terminationReason;
	public static volatile SingularAttribute<JpaRoleImpl, Long> sorRoleId;

}

