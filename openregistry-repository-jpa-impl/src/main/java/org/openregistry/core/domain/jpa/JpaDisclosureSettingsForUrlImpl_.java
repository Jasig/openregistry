package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaDisclosureSettingsForUrlImpl.class)
public abstract class JpaDisclosureSettingsForUrlImpl_ {

	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, Long> id;
	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, JpaDisclosureSettingsImpl> disclosureRecord;
	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, JpaTypeImpl> affiliationType;
	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, Boolean> publicInd;
	public static volatile SingularAttribute<JpaDisclosureSettingsForUrlImpl, Date> publicDate;

}

