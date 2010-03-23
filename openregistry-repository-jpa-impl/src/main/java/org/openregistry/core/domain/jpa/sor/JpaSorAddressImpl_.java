/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain.jpa.sor;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaCountryImpl;
import org.openregistry.core.domain.jpa.JpaRegionImpl;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorAddressImpl.class)
public abstract class JpaSorAddressImpl_ {

	public static volatile SingularAttribute<JpaSorAddressImpl, Long> id;
	public static volatile SingularAttribute<JpaSorAddressImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaSorAddressImpl, String> line1;
	public static volatile SingularAttribute<JpaSorAddressImpl, String> line2;
	public static volatile SingularAttribute<JpaSorAddressImpl, String> line3;
	public static volatile SingularAttribute<JpaSorAddressImpl, JpaRegionImpl> region;
	public static volatile SingularAttribute<JpaSorAddressImpl, JpaCountryImpl> country;
	public static volatile SingularAttribute<JpaSorAddressImpl, String> city;
	public static volatile SingularAttribute<JpaSorAddressImpl, String> postalCode;
	public static volatile SingularAttribute<JpaSorAddressImpl, JpaSorRoleImpl> sorRole;

}

