package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaNameImpl.class)
public abstract class JpaNameImpl_ {

	public static volatile SingularAttribute<JpaNameImpl, Long> id;
	public static volatile SingularAttribute<JpaNameImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaNameImpl, String> prefix;
	public static volatile SingularAttribute<JpaNameImpl, String> given;
	public static volatile SingularAttribute<JpaNameImpl, String> middle;
	public static volatile SingularAttribute<JpaNameImpl, String> family;
	public static volatile SingularAttribute<JpaNameImpl, String> suffix;
	public static volatile SingularAttribute<JpaNameImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaNameImpl, Boolean> officialName;
	public static volatile SingularAttribute<JpaNameImpl, Boolean> preferredName;
	public static volatile SingularAttribute<JpaNameImpl, Long> sourceId;
	public static volatile SingularAttribute<JpaNameImpl, String> givenComparisonValue;
	public static volatile SingularAttribute<JpaNameImpl, String> familyComparisonValue;

}

