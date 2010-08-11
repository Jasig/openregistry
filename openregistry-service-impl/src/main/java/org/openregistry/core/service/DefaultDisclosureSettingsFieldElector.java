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

package org.openregistry.core.service;

import java.util.List;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class DefaultDisclosureSettingsFieldElector implements FieldElector<DisclosureSettings> {

	/**
	 * Default implementation simply returns the newest value
	 * @see org.openregistry.core.service.FieldElector#elect(org.openregistry.core.domain.sor.SorPerson, java.util.List, boolean)
	 */
	public DisclosureSettings elect(SorPerson newestPerson,
			List<SorPerson> sorPersons, boolean deletion) {
		
		if (newestPerson != null && newestPerson.getDisclosureSettings() != null) {
			return newestPerson.getDisclosureSettings();
		} else if (sorPersons != null && !sorPersons.isEmpty()) {
				return sorPersons.get(0).getDisclosureSettings();
		} else {
			return null;
		}
	}
}
