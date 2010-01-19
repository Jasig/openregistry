package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorRole;

@StaticMetamodel(JpaSorSponsorImpl.class)
public abstract class JpaSorSponsorImpl_ {

	public static volatile SingularAttribute<JpaSorSponsorImpl, Long> id;
	public static volatile SingularAttribute<JpaSorSponsorImpl, Type> sponsorType;
	public static volatile SingularAttribute<JpaSorSponsorImpl, Long> sponsorId;
	public static volatile SetAttribute<JpaSorSponsorImpl, SorRole> roles;

}

