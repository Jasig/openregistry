package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Role;

@StaticMetamodel(JpaPersonImpl.class)
public abstract class JpaPersonImpl_ {

	public static volatile SingularAttribute<JpaPersonImpl, Long> id;
	public static volatile SetAttribute<JpaPersonImpl, JpaNameImpl> names;
	public static volatile ListAttribute<JpaPersonImpl, Role> roles;
	public static volatile SetAttribute<JpaPersonImpl, JpaIdentifierImpl> identifiers;
	public static volatile SingularAttribute<JpaPersonImpl, Date> dateOfBirth;
	public static volatile SingularAttribute<JpaPersonImpl, String> gender;
	public static volatile SingularAttribute<JpaPersonImpl, JpaActivationKeyImpl> activationKey;
	public static volatile SingularAttribute<JpaPersonImpl, JpaContactEmailAddressImpl> emailAddress;
	public static volatile SingularAttribute<JpaPersonImpl, JpaContactPhoneImpl> phoneNumber;

}

