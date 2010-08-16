package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Region;

@StaticMetamodel(JpaCountryImpl.class)
public abstract class JpaCountryImpl_ {

	public static volatile SingularAttribute<JpaCountryImpl, Long> id;
	public static volatile SingularAttribute<JpaCountryImpl, String> code;
	public static volatile SingularAttribute<JpaCountryImpl, String> name;
	public static volatile ListAttribute<JpaCountryImpl, Region> regions;

}

