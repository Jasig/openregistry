package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaSponsorImpl.class)
public abstract class JpaSponsorImpl_ {

	public static volatile SingularAttribute<JpaSponsorImpl, Long> id;
	public static volatile SingularAttribute<JpaSponsorImpl, JpaTypeImpl> sponsorType;
	public static volatile SingularAttribute<JpaSponsorImpl, Long> sponsorId;

}

