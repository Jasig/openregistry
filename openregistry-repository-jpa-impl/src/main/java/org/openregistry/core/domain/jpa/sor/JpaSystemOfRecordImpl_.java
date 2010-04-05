package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaSystemOfRecordImpl.class)
public abstract class JpaSystemOfRecordImpl_ {

    public static volatile SingularAttribute<JpaSystemOfRecordImpl, Long> id;
    public static volatile SingularAttribute<JpaSystemOfRecordImpl, String> sorId;

}

