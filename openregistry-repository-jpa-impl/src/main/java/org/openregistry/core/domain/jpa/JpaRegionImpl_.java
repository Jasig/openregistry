package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaRegionImpl.class)
public abstract class JpaRegionImpl_ {

	public static volatile SingularAttribute<JpaRegionImpl, Long> id;
	public static volatile SingularAttribute<JpaRegionImpl, String> name;
	public static volatile SingularAttribute<JpaRegionImpl, String> code;
	public static volatile SingularAttribute<JpaRegionImpl, JpaCountryImpl> country;
	public static volatile ListAttribute<JpaRegionImpl, JpaAddressImpl> addresses;

}

