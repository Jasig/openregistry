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

package org.openregistry.core.domain;

import java.util.Date;

/**
 * A set of settings that regulates which information about the person can be publicly disclosed
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DisclosureSettings {
	
	/**
	 * @return the String code identifying this set of disclosure settings
	 */
	public String getDisclosureCode();
	
	/**
	 * @return true if the person is within his or her grace period, when no
	 * information would be shown until he or she sets the disclosure code 
	 */
	public boolean isWithinGracePeriod();
	
	/**
	 * @return the last date when the disclosure settings were updated
	 */
	public Date getLastUpdateDate();
	
	/**
	 * Set the String code identifying this set of disclosure settings
	 * @param code
	 */
	public void setDisclosureCode(String code);
	
	/**
	 * @param within true if the person is within his or her grace period, when no
	 * information would be shown until he or she sets the disclosure code
	 */
	public void setWithinGracePeriod(boolean within);
	
	/**
	 * Set the last date when the disclosure settings were updated
	 * @param date
	 */
	public void setLastUpdateDate(Date date);
}
