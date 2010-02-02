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

import java.net.URL;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(JpaUrlImpl.class)
public abstract class JpaUrlImpl_ {

	public static volatile SingularAttribute<JpaUrlImpl, Long> id;
	public static volatile SingularAttribute<JpaUrlImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaUrlImpl, URL> url;
	public static volatile SingularAttribute<JpaUrlImpl, JpaRoleImpl> role;

}

