package org.openregistry.core.domain.jpa.sor;

import java.util.Date;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.sor.SorRole;

@StaticMetamodel(JpaSorPersonImpl.class)
public abstract class JpaSorPersonImpl_ {

	public static volatile SingularAttribute<JpaSorPersonImpl, Long> recordId;
	public static volatile SingularAttribute<JpaSorPersonImpl, String> sorId;
	public static volatile SingularAttribute<JpaSorPersonImpl, String> sourceSor;
	public static volatile SingularAttribute<JpaSorPersonImpl, Long> personId;
	public static volatile SingularAttribute<JpaSorPersonImpl, Date> dateOfBirth;
	public static volatile SingularAttribute<JpaSorPersonImpl, String> gender;
	public static volatile ListAttribute<JpaSorPersonImpl, Name> names;
	public static volatile SingularAttribute<JpaSorPersonImpl, String> ssn;
	public static volatile ListAttribute<JpaSorPersonImpl, SorRole> roles;

}

