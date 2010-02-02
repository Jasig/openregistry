/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorPhoneImpl.class)
public abstract class JpaSorPhoneImpl_ {

	public static volatile SingularAttribute<JpaSorPhoneImpl, Long> id;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaTypeImpl> addressType;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaTypeImpl> phoneType;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> countryCode;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> areaCode;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> number;
	public static volatile SingularAttribute<JpaSorPhoneImpl, String> extension;
	public static volatile SingularAttribute<JpaSorPhoneImpl, JpaSorRoleImpl> sorRole;

}

