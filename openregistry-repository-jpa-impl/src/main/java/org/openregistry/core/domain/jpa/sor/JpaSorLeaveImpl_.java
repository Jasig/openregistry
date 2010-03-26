package org.openregistry.core.domain.jpa.sor;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorLeaveImpl.class)
public abstract class JpaSorLeaveImpl_ {

	public static volatile SingularAttribute<JpaSorLeaveImpl, Long> id;
	public static volatile SingularAttribute<JpaSorLeaveImpl, Date> start;
	public static volatile SingularAttribute<JpaSorLeaveImpl, Date> end;
	public static volatile SingularAttribute<JpaSorLeaveImpl, JpaTypeImpl> reason;
	public static volatile SingularAttribute<JpaSorLeaveImpl, JpaSorRoleImpl> sorRole;

}

