package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaDisclosureSettingsForEmailImpl.class)
public abstract class JpaDisclosureSettingsForEmailImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, JpaDisclosureSettingsImpl> disclosureRecord;
	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, JpaTypeImpl> affiliationType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, Boolean> publicInd;
	public static volatile SingularAttribute<JpaDisclosureSettingsForEmailImpl, Date> publicDate;

}

