package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaIdentifierImpl.class)
public abstract class JpaIdentifierImpl_ {

	public static volatile SingularAttribute<JpaIdentifierImpl, Long> id;
	public static volatile SingularAttribute<JpaIdentifierImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaIdentifierImpl, JpaIdentifierTypeImpl> type;
	public static volatile SingularAttribute<JpaIdentifierImpl, String> value;
	public static volatile SingularAttribute<JpaIdentifierImpl, Boolean> primary;
	public static volatile SingularAttribute<JpaIdentifierImpl, Boolean> deleted;
	public static volatile SingularAttribute<JpaIdentifierImpl, Date> creationDate;
	public static volatile SingularAttribute<JpaIdentifierImpl, Date> deletedDate;

}

