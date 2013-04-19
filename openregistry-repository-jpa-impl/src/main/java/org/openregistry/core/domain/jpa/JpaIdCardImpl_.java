package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaIdCardImpl.class)
public abstract class JpaIdCardImpl_ {

	public static volatile SingularAttribute<JpaIdCardImpl, Long> id;
	public static volatile SingularAttribute<JpaIdCardImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaIdCardImpl, String> cardNumber;
	public static volatile SingularAttribute<JpaIdCardImpl, String> cardSecurityValue;
	public static volatile SingularAttribute<JpaIdCardImpl, String> barCode;
	public static volatile SingularAttribute<JpaIdCardImpl, String> proximityNumber;
	public static volatile SingularAttribute<JpaIdCardImpl, Date> startDate;
	public static volatile SingularAttribute<JpaIdCardImpl, Date> expirationDate;
	public static volatile SingularAttribute<JpaIdCardImpl, Date> updateDate;

}

