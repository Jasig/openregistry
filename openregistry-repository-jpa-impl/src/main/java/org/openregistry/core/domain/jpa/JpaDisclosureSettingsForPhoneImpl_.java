package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaDisclosureSettingsForPhoneImpl.class)
public abstract class JpaDisclosureSettingsForPhoneImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, JpaDisclosureSettingsImpl> disclosureRecord;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, JpaTypeImpl> affiliationType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, JpaTypeImpl> phoneType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, Boolean> publicInd;
	public static volatile SingularAttribute<JpaDisclosureSettingsForPhoneImpl, Date> publicDate;

}

