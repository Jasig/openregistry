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
package org.jasig.openregistry.test.repository;

import org.openregistry.core.repository.DisclosureRecalculationStrategyRepository;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

import java.util.HashMap;
import java.util.HashSet;

public class MockDisclosureRecalculationStrategyRepository  
 implements DisclosureRecalculationStrategyRepository
{
	public DisclosureRecalculationStrategy getDisclosureRecalculationStrategy() {
		
		return new DisclosureRecalculationStrategy() {

			public String getDescription() {
				return "Mock disclosure recalculation strategy, always returns true";
			}

			public String getName() {
				return "Mock";
			}

			public boolean isAddressBuildingPublic(String disclosureCode,
					String addressType, String affiliationType) {
				return true;
			}

			public boolean isAddressLinesPublic(String disclosureCode,
					String addressType, String affiliationType) {
				return true;
			}

			public boolean isAddressRegionPublic(String disclosureCode,
					String addressType, String affiliationType) {
				return true;
			}

			public boolean isEmailPublic(String disclosureCode,
					String addressType, String affiliationType) {
				return true;
			}

			public boolean isPhonePublic(String disclosureCode,
					String addressType, String phoneType, String affiliationType) {
				return true;
			}
			public boolean isUrlPublic(String disclosureCode,
					String addressType, String affiliationType) {
				return true;
			}

            public HashSet<String> getSpecificaddressTypes(String disclosureCode,String affiliationType){
            	HashSet<String> addrTypes = new HashSet<String>();
            	addrTypes.add("HOME");
            	addrTypes.add("CAMPUS");
            	return addrTypes;
            }
            public HashSet<String> getSpecificEmailTypes(String disclosureCode,String affiliationType){
            	HashSet<String> emailTypes = new HashSet<String>();
            	emailTypes.add("HOME");
            	emailTypes.add("CAMPUS");
            	return emailTypes;
            }
            public HashSet<String> getSpecificPhoneTypes(String disclosureCode,String affiliationType){
            	HashSet<String> phoneTypes = new HashSet<String>();
            	phoneTypes.add("HOME");
            	phoneTypes.add("CAMPUS");
            	return phoneTypes;
            }
            public HashSet<String> getSpecificUrlTypes(String disclosureCode,String affiliationType){
            	HashSet<String> urlTypes = new HashSet<String>();
            	urlTypes.add("HOME");
            	urlTypes.add("CAMPUS");
            	return urlTypes;
            }

            public HashMap<String,HashSet<String>> getSpecificPhoneTypesWithAddress(String disclosureCode,
                                                                                    String affiliationType){
                HashSet<String> phoneTypes = new HashSet<String>();
                phoneTypes.add("HOME");
                phoneTypes.add("CAMPUS");

                HashMap<String,HashSet<String>> phAddrTypes = new HashMap<String,HashSet<String>>();
                phAddrTypes.put("HOME",phoneTypes);

                return  phAddrTypes;

            }
			
		};
	}

}
