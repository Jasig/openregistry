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

@StaticMetamodel(JpaLeaveImpl.class)
public abstract class JpaLeaveImpl_ {

	public static volatile SingularAttribute<JpaLeaveImpl, Long> id;
	public static volatile SingularAttribute<JpaLeaveImpl, Date> start;
	public static volatile SingularAttribute<JpaLeaveImpl, Date> end;
	public static volatile SingularAttribute<JpaLeaveImpl, JpaTypeImpl> reason;
	public static volatile SingularAttribute<JpaLeaveImpl, JpaRoleImpl> role;

}

