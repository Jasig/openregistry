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

import java.util.Date;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorRoleImpl.class)
public abstract class JpaSorRoleImpl_ {

	public static volatile SingularAttribute<JpaSorRoleImpl, Long> recordId;
	public static volatile SingularAttribute<JpaSorRoleImpl, String> sorId;
	public static volatile ListAttribute<JpaSorRoleImpl, Url> urls;
	public static volatile ListAttribute<JpaSorRoleImpl, EmailAddress> emailAddresses;
	public static volatile ListAttribute<JpaSorRoleImpl, Phone> phones;
	public static volatile ListAttribute<JpaSorRoleImpl, Address> addresses;
	public static volatile SingularAttribute<JpaSorRoleImpl, String> sourceSorIdentifier;
	public static volatile SingularAttribute<JpaSorRoleImpl, JpaSorPersonImpl> person;
	public static volatile SingularAttribute<JpaSorRoleImpl, Integer> percentage;
	public static volatile SingularAttribute<JpaSorRoleImpl, JpaTypeImpl> personStatus;
	public static volatile ListAttribute<JpaSorRoleImpl, Leave> leaves;
	public static volatile SingularAttribute<JpaSorRoleImpl, JpaRoleInfoImpl> roleInfo;
	public static volatile SingularAttribute<JpaSorRoleImpl, JpaSorSponsorImpl> sponsor;
	public static volatile SingularAttribute<JpaSorRoleImpl, Date> start;
	public static volatile SingularAttribute<JpaSorRoleImpl, Date> end;
	public static volatile SingularAttribute<JpaSorRoleImpl, JpaTypeImpl> terminationReason;

}

