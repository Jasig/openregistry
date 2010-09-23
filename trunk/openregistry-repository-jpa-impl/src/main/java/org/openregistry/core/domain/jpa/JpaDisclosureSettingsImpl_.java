package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaDisclosureSettingsImpl.class)
public abstract class JpaDisclosureSettingsImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, String> disclosureCode;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Date> lastUpdateDate;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Boolean> withinGracePeriod;

}

