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

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Phone extends Serializable {
	
	static final String PHONE_SEP = "-";

    Long getId();
    
    Type getAddressType();

    Type getPhoneType();

    Integer getPhoneLineOrder();

    String getCountryCode();

    String getAreaCode();

    String getNumber();

    String getExtension();

    void setAddressType(Type addressType);

    void setPhoneType(Type phoneType);

    void setPhoneLineOrder(Integer phoneLineOrder);

    void setCountryCode(String countryCode);

    void setAreaCode(String areaCode);

    void setNumber(String number);

    void setExtension(String extension);
}
