package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.sor.JpaSystemOfRecordImpl;

@StaticMetamodel(JpaRoleInfoImpl.class)
public abstract class JpaRoleInfoImpl_ {

	public static volatile SingularAttribute<JpaRoleInfoImpl, Long> id;
	public static volatile SingularAttribute<JpaRoleInfoImpl, String> title;
	public static volatile SingularAttribute<JpaRoleInfoImpl, JpaOrganizationalUnitImpl> organizationalUnit;
	public static volatile SingularAttribute<JpaRoleInfoImpl, JpaCampusImpl> campus;
	public static volatile SingularAttribute<JpaRoleInfoImpl, JpaTypeImpl> affiliationType;
	public static volatile SingularAttribute<JpaRoleInfoImpl, JpaSystemOfRecordImpl> systemOfRecord;
	public static volatile SingularAttribute<JpaRoleInfoImpl, String> code;

}

