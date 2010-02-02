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
package org.openregistry.core.domain.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaAddressImpl.class)
public abstract class JpaAddressImpl_ {

	public static volatile SingularAttribute<JpaAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaAddressImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaAddressImpl, String> line1;
	public static volatile SingularAttribute<JpaAddressImpl, String> line2;
	public static volatile SingularAttribute<JpaAddressImpl, String> line3;
	public static volatile SingularAttribute<JpaAddressImpl, JpaRegionImpl> region;
	public static volatile SingularAttribute<JpaAddressImpl, JpaCountryImpl> country;
	public static volatile SingularAttribute<JpaAddressImpl, String> city;
	public static volatile SingularAttribute<JpaAddressImpl, String> postalCode;
	public static volatile SingularAttribute<JpaAddressImpl, JpaRoleImpl> role;

}

