package org.hibernate.envers;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DefaultRevisionEntity.class)
public abstract class DefaultRevisionEntity_ {

	public static volatile SingularAttribute<DefaultRevisionEntity, Integer> id;
	public static volatile SingularAttribute<DefaultRevisionEntity, Long> timestamp;

}

