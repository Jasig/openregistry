package org.openregistry.core.domain.jpa;

import java.net.URL;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaUrlImpl.class)
public abstract class JpaUrlImpl_ {

	public static volatile SingularAttribute<JpaUrlImpl, Long> id;
	public static volatile SingularAttribute<JpaUrlImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaUrlImpl, URL> url;
	public static volatile SingularAttribute<JpaUrlImpl, JpaRoleImpl> role;

}

