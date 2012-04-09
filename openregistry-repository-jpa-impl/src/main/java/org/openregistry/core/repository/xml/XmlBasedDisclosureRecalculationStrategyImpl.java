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
package org.openregistry.core.repository.xml;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettings.PropertyNames;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

@XmlRootElement(name = "specification")
public class XmlBasedDisclosureRecalculationStrategyImpl implements
		DisclosureRecalculationStrategy {

    @XmlElement(name="id",nillable = false)
    private String id;

    @XmlElement(name="name", nillable = false)
    private String internalName;

    @XmlElement(name="description",nillable = false)
    private String internalDescription;

    @XmlElement(name="defaultFlagName",nillable = false)
    private String defaultFlagName;

    @XmlElement(name="hiddenFlagValue",nillable = false)
    private String hiddenFlagValue;

    @XmlElement(name="asSpecifiedFlagValue",nillable = false)
    private String asSpecifiedFlagValue;

    @XmlElementWrapper(name = "disclosureCodeSpecs", required=true)
    @XmlElement(name="disclosureCodeSpec")
    private HashSet<DisclosureCodeSpecification> specs = new HashSet<DisclosureCodeSpecification>();
   
    private static class DisclosureCodeSpecification {
        @XmlElement(name="code", nillable = false)
    	private String code;
        
        @XmlElement(name="description", nillable = false)
    	private String description;
        
        @XmlElementWrapper(name = "affiliations", required=true)
        @XmlElement(name = "affiliation")
        private HashSet<Affiliation> affiliations = new HashSet<Affiliation>();
        
    	private Affiliation findAffiliation(String affiliation, String defaultFlagName) {
    		Affiliation defaultAff = null;
			for (Affiliation aff: this.affiliations) {
				if (aff.name.equals(affiliation)) {
					return aff;
				} else if (aff.name.equals(defaultFlagName)) {
					defaultAff = aff;
				}
			}
			return defaultAff;
		}
    }
    
    private static class Affiliation {
        @XmlAttribute(name="name")
    	private String name;
        
        @XmlElementWrapper(name = "addressTypes", required=true)
        @XmlElement(name = "addressType")
        private HashSet<TypeWithProperties> addressTypes = new HashSet<TypeWithProperties>();

        @XmlElementWrapper(name = "emailTypes", required=true)
        @XmlElement(name = "emailType")
        private HashSet<TypeWithProperties> emailTypes = new HashSet<TypeWithProperties>();

        @XmlElementWrapper(name = "phoneTypes", required=true)
        @XmlElement(name = "phoneType")
        private HashSet<TypeWithProperties> phoneTypes = new HashSet<TypeWithProperties>();

        @XmlElementWrapper(name = "urlTypes", required=true)
        @XmlElement(name = "urlType")
        private HashSet<TypeWithProperties> urlTypes = new HashSet<TypeWithProperties>();
        
    	private TypeWithProperties findAddressType(String type, String defaultFlagName) {
    		TypeWithProperties defaultType = null;
			for (TypeWithProperties t: this.addressTypes) {
				if (t.name.equals(type)) {
					return t;
				} else if (t.name.equals(defaultFlagName)) {
					defaultType = t;
				}
			}
			return defaultType;
		}
		
		private TypeWithProperties findEmailType(String type, String defaultFlagName) {
			TypeWithProperties defaultType = null;
			for (TypeWithProperties t: this.emailTypes) {
				if (t.name.equals(type)) {
					return t;
				} else if (t.name.equals(defaultFlagName)) {
					defaultType = t;
				}
			}
			return defaultType;
		}
		
		private TypeWithProperties findPhoneType(String type, String defaultFlagName) {
			TypeWithProperties defaultType = null;
			for (TypeWithProperties t: this.phoneTypes) {
				if (t.name.equals(type)) {
					return t;
				} else if (t.name.equals(defaultFlagName)) {
					defaultType = t;
				}
			}
			return defaultType;
		}
		
		private TypeWithProperties findUrlType(String type, String defaultFlagName) {
			TypeWithProperties defaultType = null;
			for (TypeWithProperties t: this.urlTypes) {
				if (t.name.equals(type)) {
					return t;
				} else if (t.name.equals(defaultFlagName)) {
					defaultType = t;
				}
			}
			return defaultType;
		}
		
		private HashSet<TypeWithProperties> getaddressTypes(){
			return this.addressTypes;
		}
		
		private HashSet<TypeWithProperties> getEmailTypes(){
			return this.emailTypes;
			
		}
		
		private HashSet<TypeWithProperties> getPhoneTypes(){
			return this.phoneTypes;
			
		}
		
		private HashSet<TypeWithProperties> getUrlTypes(){
			return this.urlTypes;
			
		}
    }
    
    private static class TypeWithProperties {
        @XmlAttribute(name="name")
    	private String name;
        
        @XmlElementWrapper(name = "properties", required=true)
        @XmlElement(name = "property")
        private HashSet<Property> properties = new HashSet<Property>();   
        
    	private Property findProperty(String propName, String defaultFlagName) {
    		Property defaultProp = null;
			for (Property p: this.properties) {
				if (p.name.equals(propName)) {
					return p;
				} else if (p.name.equals(defaultFlagName)) {
					defaultProp = p;
				}
			}
			return defaultProp;
    	}
    }
    
    private static class Property {
        @XmlAttribute(name="name")
    	private String name;
        
        @XmlAttribute(name="value")
    	private String value;
    }
    
	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#getName()
	 */
	public String getName() {
		return this.internalName;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#getDescription()
	 */
	public String getDescription() {
		return this.internalDescription;
	}
	
	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isAddressBuildingPublic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isAddressBuildingPublic(String disclosureCode,
			String addressType, String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties addr = aff.findAddressType(addressType, defaultFlagName);
			Property prop = addr.findProperty(DisclosureSettings.PropertyNames.BUILDING_IND.name(), defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isAddressLinesPublic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isAddressLinesPublic(String disclosureCode,
			String addressType, String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties addr = aff.findAddressType(addressType, defaultFlagName);
			Property prop = addr.findProperty(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND.name(), defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isAddressRegionPublic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isAddressRegionPublic(String disclosureCode,
			String addressType, String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties addr = aff.findAddressType(addressType, defaultFlagName);
			Property prop = addr.findProperty(DisclosureSettings.PropertyNames.REGION_IND.name(), defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isEmailPublic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isEmailPublic(String disclosureCode, String addressType,
			String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties email = aff.findEmailType(addressType, defaultFlagName);
			Property prop = email.findProperty(defaultFlagName, defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isPhonePublic(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isPhonePublic(String disclosureCode, String addressType,
			String phoneType, String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties phone = aff.findPhoneType(addressType, defaultFlagName);
			Property prop = phone.findProperty(phoneType, defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openregistry.core.service.DisclosureRecalculationStrategy#isUrlPublic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isUrlPublic(String disclosureCode, String addressType,
			String affiliationType) {
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			TypeWithProperties url = aff.findUrlType(addressType, defaultFlagName);
			Property prop = url.findProperty(defaultFlagName, defaultFlagName);
			if (prop.value.equals(this.hiddenFlagValue)) {
				return false;
			}
			if (prop.value.equals(this.asSpecifiedFlagValue)) {
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * Finds the disclosure strategy for the given code
	 * Returns null if it does not exist
	 * @param code
	 * @return
	 */
	private DisclosureCodeSpecification findSpec(String code) {
		for (DisclosureCodeSpecification spec: this.specs) {
			if (spec.code.equals(code)) {
				return spec;
			}
		}
		return null;
	}
	
	public HashSet<String> getSpecificaddressTypes(String disclosureCode, 
			String affiliationType){
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		HashSet<String> addrTypes = new HashSet<String>();
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			
			 for (TypeWithProperties t: aff.addressTypes) {
					 if (!t.name.equals(defaultFlagName)) {
						 addrTypes.add(t.name);
					}
				}
		}
		return addrTypes;
	}
	
	public HashSet<String> getSpecificPhoneTypes(String disclosureCode, 
			String affiliationType){
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		HashSet<String> phoneTypes = new HashSet<String>();
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			
			 for (TypeWithProperties t: aff.phoneTypes) {
					 if (!t.name.equals(defaultFlagName)) {
						 phoneTypes.add(t.name);
					}
				}
		}
		return phoneTypes;
	}
	
	public HashSet<String> getSpecificUrlTypes(String disclosureCode, 
			String affiliationType){
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		HashSet<String> urlTypes = new HashSet<String>();
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			
			 for (TypeWithProperties t: aff.urlTypes) {
					 if (!t.name.equals(defaultFlagName)) {
						 urlTypes.add(t.name);
					}
				}
		}
		return urlTypes;
	}
	
	public HashSet<String> getSpecificEmailTypes(String disclosureCode, 
			String affiliationType){
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		HashSet<String> emailTypes = new HashSet<String>();
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			
			 for (TypeWithProperties t: aff.emailTypes) {
					 if (!t.name.equals(defaultFlagName)) {
						 emailTypes.add(t.name);
					}
				}
		}
		return emailTypes;
	}
	
	
	public HashMap<String,HashSet<String>> getSpecificPhoneTypesWithAddress(String disclosureCode, 
			String affiliationType){
		DisclosureCodeSpecification spec = this.findSpec(disclosureCode);
		HashMap<String,HashSet<String>> addrTypes = new HashMap<String,HashSet<String>>();
		if (spec == null) {
			throw new IllegalStateException("Disclosure code "+disclosureCode+" is not a valid code");
		} else {
			Affiliation aff = spec.findAffiliation(affiliationType, defaultFlagName);
			
			 for (TypeWithProperties t: aff.phoneTypes) {
					 if (!t.name.equals(defaultFlagName)) {
						 HashSet<Property> properties = t.properties;
						HashSet<String> phoneTypes = new HashSet<String>();
						 for (Property p: properties) {
							 if(!p.name.equals(defaultFlagName)){
								 phoneTypes.add(p.name);
								
							 }
						 }
						 addrTypes.put(t.name, phoneTypes);
					}
				}
		}
		return addrTypes;
	}
	
	
		
	}
	
	
	
	
