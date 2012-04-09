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

import java.util.HashMap;
import java.util.HashSet;


/**
 * Determines individual field-level disclosure settings based on disclosure code
 * @version $Revision$ $Date$
 */
public interface DisclosureRecalculationStrategy {
	
	public String getName();
	public String getDescription();
	
	public boolean isAddressLinesPublic(String disclosureCode, String addressType, String affiliationType);
	public boolean isAddressBuildingPublic(String disclosureCode, String addressType, String affiliationType);
	public boolean isAddressRegionPublic(String disclosureCode, String addressType, String affiliationType);
	public boolean isEmailPublic(String disclosureCode, String addressType, String affiliationType);
	public boolean isPhonePublic(String disclosureCode, String addressType, String phoneType, String affiliationType);
	public boolean isUrlPublic(String disclosureCode, String addressType, String affiliationType);
	public HashSet<String> getSpecificaddressTypes(String disclosureCode,String affiliationType);
	public HashSet<String> getSpecificEmailTypes(String disclosureCode,String affiliationType);
	public HashSet<String> getSpecificPhoneTypes(String disclosureCode,String affiliationType);
	public HashSet<String> getSpecificUrlTypes(String disclosureCode,String affiliationType);
	public HashMap<String,HashSet<String>> getSpecificPhoneTypesWithAddress(String disclosureCode,String affiliationType);
}
