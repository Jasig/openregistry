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

import org.openregistry.core.domain.sor.SorDisclosureSettings;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class DefaultDisclosureSettingsFieldElector implements FieldElector<SorDisclosureSettings> {

	/**
	 * Default implementation simply returns the new value
	 * If it is not available, returns the most recent of the available ones
	 * If none are available, returns null
	 * @see org.openregistry.core.service.FieldElector#elect(org.openregistry.core.domain.sor.SorPerson, java.util.List, boolean)
	 */
	public SorDisclosureSettings elect(SorPerson newestPerson,
			List<SorPerson> sorPersons, boolean deletion) {
		
		if (newestPerson != null && newestPerson.getDisclosureSettings() != null) {
			return newestPerson.getDisclosureSettings();
		} else if (sorPersons != null && !sorPersons.isEmpty()) {
			SorDisclosureSettings mostRecent = null;
			
			for (SorPerson sp: sorPersons) {
				if (mostRecent == null || 
					(mostRecent.getLastUpdateDate() != null  
						&& sp.getDisclosureSettings() != null && sp.getDisclosureSettings().getLastUpdateDate() != null
						&& !sp.getDisclosureSettings().getLastUpdateDate().before(mostRecent.getLastUpdateDate()))) {
					mostRecent = (sp.getDisclosureSettings() != null && sp.getDisclosureSettings().getLastUpdateDate() != null) ? sp.getDisclosureSettings() : null;
				}
			}
			return mostRecent;
		} else {
			return null;
		}
	}
}
