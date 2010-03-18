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
package org.jasig.openregistry.test.domain;

import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.sor.SorName;

/**
 * Mock implementation of Sor Name.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public class MockSorName implements SorName {

    private Type type;

    private String prefix;

    private String given;

    private String middle;

    private String family;

    private String suffix;

    private Long id;

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setGiven(final String given) {
        this.given = given;
    }

    @Override
    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    @Override
    public void setFamily(final String family) {
        this.family = family;
    }

    @Override
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getGiven() {
        return this.given;
    }

    @Override
    public String getMiddle() {
        return this.middle;
    }

    @Override
    public String getFamily() {
        return this.family;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public String getFormattedName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLongFormattedName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
