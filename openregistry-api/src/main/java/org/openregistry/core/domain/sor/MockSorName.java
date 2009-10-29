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
package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.MockType;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;

/**
 * Implementation of the Name domain object that conforms to the tables for the Systems of Record
 *
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

public final class MockSorName implements Name {

    private Long id;
    
    private Type type;

    private String prefix;

    private String given;

    private String middle;

    private String family;

    private String suffix;

    public MockSorName() {
        // nothing to do
    }

    public Long getId() {
        return this.id;
    }
    
    public Type getType() {
        return this.type;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getGiven() {
        return this.given;
    }

    public String getMiddle() {
        return this.middle;
    }

    public String getFamily() {
        return this.family;
    }

    public String getSuffix() {
        return this.suffix;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setGiven(final String given) {
        this.given = given;
    }

    public void setMiddle(final String middle) {
        this.middle = middle;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ",");
        construct(builder, "", this.given, "");

        return builder.toString();
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        construct(builder, "", this.middle, " ");
        construct(builder, "", this.family, "");
        construct(builder, ",", this.suffix, "");

        return builder.toString();
    }

    protected void construct(final StringBuilder builder, final String prefix, final String string, final String delimiter) {
        if (string != null) {
            builder.append(prefix);
            builder.append(string);
            builder.append(delimiter);
        }
    }

	public boolean isOfficialName() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPreferredName() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setOfficialName() {
		// TODO Auto-generated method stub

	}

	public void setPreferredName() {
		// TODO Auto-generated method stub

	}
}