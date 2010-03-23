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

