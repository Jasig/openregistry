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

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaIdentifierTypeImpl.class)
public abstract class JpaIdentifierTypeImpl_ {

	public static volatile SingularAttribute<JpaIdentifierTypeImpl, Long> id;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, String> name;
	public static volatile ListAttribute<JpaIdentifierTypeImpl, JpaIdentifierImpl> identifiers;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, String> format;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, Boolean> privateIdentifier;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, Boolean> modifiable;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, Boolean> deleted;
	public static volatile SingularAttribute<JpaIdentifierTypeImpl, String> description;

}

