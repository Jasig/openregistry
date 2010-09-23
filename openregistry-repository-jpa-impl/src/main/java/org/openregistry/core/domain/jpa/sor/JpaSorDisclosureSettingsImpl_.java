package org.openregistry.core.domain.jpa.sor;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaSorDisclosureSettingsImpl.class)
public abstract class JpaSorDisclosureSettingsImpl_ {

	public static volatile SingularAttribute<JpaSorDisclosureSettingsImpl, Long> id;
	public static volatile SingularAttribute<JpaSorDisclosureSettingsImpl, JpaSorPersonImpl> person;
	public static volatile SingularAttribute<JpaSorDisclosureSettingsImpl, String> disclosureCode;
	public static volatile SingularAttribute<JpaSorDisclosureSettingsImpl, Date> lastUpdateDate;
	public static volatile SingularAttribute<JpaSorDisclosureSettingsImpl, Boolean> withinGracePeriod;

}

