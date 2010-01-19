package org.openregistry.core.domain.jpa.sor;

import java.net.URL;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorUrlImpl.class)
public abstract class JpaSorUrlImpl_ {

	public static volatile SingularAttribute<JpaSorUrlImpl, Long> id;
	public static volatile SingularAttribute<JpaSorUrlImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaSorUrlImpl, URL> url;
	public static volatile SingularAttribute<JpaSorUrlImpl, JpaSorRoleImpl> sorRole;

}

