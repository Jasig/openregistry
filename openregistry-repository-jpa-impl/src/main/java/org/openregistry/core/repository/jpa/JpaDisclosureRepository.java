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
package org.openregistry.core.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.repository.DisclosureRepository;
import org.springframework.stereotype.Repository;

/**
 * DisclosureRepository implementation with JPA
 * @author llevkovi 
 * @version $Id$
 */
@Repository (value = "disclosureRepository")
public class JpaDisclosureRepository implements DisclosureRepository {

    @PersistenceContext
    private EntityManager entityManager;

	/**
	 * @see org.openregistry.core.repository.DisclosureRepository#saveDisclosureSettings(org.openregistry.core.domain.DisclosureSettings)
	 */
	public DisclosureSettings saveDisclosureSettings(DisclosureSettings disclosure) {
		return this.entityManager.merge(disclosure);
	}
}
