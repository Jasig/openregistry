package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorEmailAddressImpl.class)
public abstract class JpaSorEmailAddressImpl_ {

	public static volatile SingularAttribute<JpaSorEmailAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaSorEmailAddressImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaSorEmailAddressImpl, String> address;
	public static volatile SingularAttribute<JpaSorEmailAddressImpl, JpaSorRoleImpl> sorRole;

}

