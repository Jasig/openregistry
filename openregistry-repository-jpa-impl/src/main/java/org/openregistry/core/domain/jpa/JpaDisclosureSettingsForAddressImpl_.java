package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaDisclosureSettingsForAddressImpl.class)
public abstract class JpaDisclosureSettingsForAddressImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, JpaDisclosureSettingsImpl> disclosureRecord;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, JpaTypeImpl> affiliationType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Date> addressLinesDate;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Boolean> addressLinesPublic;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Date> addressBuildingDate;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Boolean> addressBuildingPublic;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Date> addressRegionDate;
	public static volatile SingularAttribute<JpaDisclosureSettingsForAddressImpl, Boolean> addressRegionPublic;

}

