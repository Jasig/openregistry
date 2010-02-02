package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorNameImpl.class)
public abstract class JpaSorNameImpl_ {

	public static volatile SingularAttribute<JpaSorNameImpl, Long> id;
	public static volatile SingularAttribute<JpaSorNameImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaSorNameImpl, String> prefix;
	public static volatile SingularAttribute<JpaSorNameImpl, String> given;
	public static volatile SingularAttribute<JpaSorNameImpl, String> middle;
	public static volatile SingularAttribute<JpaSorNameImpl, String> family;
	public static volatile SingularAttribute<JpaSorNameImpl, String> suffix;
	public static volatile SingularAttribute<JpaSorNameImpl, JpaSorPersonImpl> person;

}

