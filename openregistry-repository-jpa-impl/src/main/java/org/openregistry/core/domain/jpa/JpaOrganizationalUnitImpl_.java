package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaOrganizationalUnitImpl.class)
public abstract class JpaOrganizationalUnitImpl_ {

	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, Long> id;
	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, JpaCampusImpl> campus;
	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, JpaTypeImpl> organizationalUnitType;
	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, String> localCode;
	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, String> name;
	public static volatile SingularAttribute<JpaOrganizationalUnitImpl, JpaOrganizationalUnitImpl> organizationalUnit;
    public static volatile SingularAttribute<JpaOrganizationalUnitImpl, String> RBHS;
    public static volatile SingularAttribute<JpaOrganizationalUnitImpl, String> PHI;
}

