package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaTypeImpl.class)
public abstract class JpaTypeImpl_ {

	public static volatile SingularAttribute<JpaTypeImpl, Long> id;
	public static volatile SingularAttribute<JpaTypeImpl, String> dataType;
	public static volatile SingularAttribute<JpaTypeImpl, String> description;

}

