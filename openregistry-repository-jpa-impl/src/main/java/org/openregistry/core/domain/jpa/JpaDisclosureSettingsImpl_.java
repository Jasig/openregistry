package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForUrl;

@StaticMetamodel(JpaDisclosureSettingsImpl.class)
public abstract class JpaDisclosureSettingsImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, String> disclosureCode;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Date> lastUpdateDate;
	public static volatile SingularAttribute<JpaDisclosureSettingsImpl, Boolean> withinGracePeriod;
	public static volatile SetAttribute<JpaDisclosureSettingsImpl, DisclosureSettingsForAddress> addressDisclosureSettings;
	public static volatile SetAttribute<JpaDisclosureSettingsImpl, DisclosureSettingsForEmail> emailDisclosureSettings;
	public static volatile SetAttribute<JpaDisclosureSettingsImpl, DisclosureSettingsForPhone> phoneDisclosureSettings;
	public static volatile SetAttribute<JpaDisclosureSettingsImpl, DisclosureSettingsForUrl> urlDisclosureSettings;

}

