package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaCampusImpl.class)
public abstract class JpaCampusImpl_ {

	public static volatile SingularAttribute<JpaCampusImpl, Long> id;
	public static volatile SingularAttribute<JpaCampusImpl, String> code;
	public static volatile SingularAttribute<JpaCampusImpl, String> name;
	public static volatile ListAttribute<JpaCampusImpl, JpaRoleInfoImpl> roleInfos;
	public static volatile ListAttribute<JpaCampusImpl, JpaOrganizationalUnitImpl> organizationalUnits;

}

