package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaLeaveImpl.class)
public abstract class JpaLeaveImpl_ {

	public static volatile SingularAttribute<JpaLeaveImpl, Long> id;
	public static volatile SingularAttribute<JpaLeaveImpl, Date> start;
	public static volatile SingularAttribute<JpaLeaveImpl, Date> end;
	public static volatile SingularAttribute<JpaLeaveImpl, JpaTypeImpl> reason;
	public static volatile SingularAttribute<JpaLeaveImpl, JpaRoleImpl> role;

}

