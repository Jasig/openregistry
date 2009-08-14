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
package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Address extends Serializable {

    Long getId();

    Type getType();

    String getLine1();

    String getLine2();

    String getLine3();

    Region getRegion();

    Country getCountry();

    String getCity();

    String getPostalCode();

    void setType(Type type);

    void setLine1(String line1);

    void setLine2(String line2);

    void setLine3(String line3);

    void setRegion(Region region);

    void setCountry(Country country);

    void setCity(String city);

    void setPostalCode(String postalCode);

    String getSingleLineAddress();
}
