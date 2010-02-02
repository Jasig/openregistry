package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaActivationKeyImpl.class)
public abstract class JpaActivationKeyImpl_ {

	public static volatile SingularAttribute<JpaActivationKeyImpl, String> value;
	public static volatile SingularAttribute<JpaActivationKeyImpl, Date> end;
	public static volatile SingularAttribute<JpaActivationKeyImpl, Date> start;
	public static volatile SingularAttribute<JpaActivationKeyImpl, String> lock;
	public static volatile SingularAttribute<JpaActivationKeyImpl, Date> lockExpirationDate;

}

