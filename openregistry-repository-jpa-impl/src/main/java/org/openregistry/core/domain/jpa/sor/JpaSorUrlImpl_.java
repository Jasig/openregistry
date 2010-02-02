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

import java.net.URL;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openregistry.core.domain.jpa.JpaTypeImpl;

@StaticMetamodel(JpaSorUrlImpl.class)
public abstract class JpaSorUrlImpl_ {

	public static volatile SingularAttribute<JpaSorUrlImpl, Long> id;
	public static volatile SingularAttribute<JpaSorUrlImpl, JpaTypeImpl> type;
	public static volatile SingularAttribute<JpaSorUrlImpl, URL> url;
	public static volatile SingularAttribute<JpaSorUrlImpl, JpaSorRoleImpl> sorRole;

}

