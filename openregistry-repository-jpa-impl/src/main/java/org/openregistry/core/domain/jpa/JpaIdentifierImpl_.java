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

package org.openregistry.core.domain.jpa;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaIdentifierImpl.class)
public abstract class JpaIdentifierImpl_ {

	public static volatile SingularAttribute<JpaIdentifierImpl, Long> id;
	public static volatile SingularAttribute<JpaIdentifierImpl, JpaPersonImpl> person;
	public static volatile SingularAttribute<JpaIdentifierImpl, JpaIdentifierTypeImpl> type;
	public static volatile SingularAttribute<JpaIdentifierImpl, String> value;
	public static volatile SingularAttribute<JpaIdentifierImpl, Boolean> primary;
	public static volatile SingularAttribute<JpaIdentifierImpl, Boolean> deleted;
	public static volatile SingularAttribute<JpaIdentifierImpl, Date> creationDate;
	public static volatile SingularAttribute<JpaIdentifierImpl, Date> deletedDate;

}

