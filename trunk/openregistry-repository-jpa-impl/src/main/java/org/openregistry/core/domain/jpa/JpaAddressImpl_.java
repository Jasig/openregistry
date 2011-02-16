package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(JpaAddressImpl.class)
public abstract class JpaAddressImpl_ {

	public static volatile SingularAttribute<JpaAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaAddressImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaAddressImpl, String> line1;
	public static volatile SingularAttribute<JpaAddressImpl, String> line2;
	public static volatile SingularAttribute<JpaAddressImpl, String> line3;
	public static volatile SingularAttribute<JpaAddressImpl, String> bldgNo;
	public static volatile SingularAttribute<JpaAddressImpl, String> roomNo;
	public static volatile SingularAttribute<JpaAddressImpl, JpaRegionImpl> region;
	public static volatile SingularAttribute<JpaAddressImpl, JpaCountryImpl> country;
	public static volatile SingularAttribute<JpaAddressImpl, String> city;
	public static volatile SingularAttribute<JpaAddressImpl, String> postalCode;
    public static volatile SingularAttribute<JpaAddressImpl, Date> updateDate;
	public static volatile SingularAttribute<JpaAddressImpl, JpaRoleImpl> role;

}

