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

package org.jasig.openregistry.test.domain;

import org.openregistry.core.domain.AbstractNameImpl;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.internal.Entity;

public class MockName extends AbstractNameImpl {

    private Long id;
    
    private Type type;

    private String prefix;

    private String given;

    private String middle;

    private String family;

    private String suffix;

    private boolean officialName = false;

    private boolean preferredName = false;

    private Long sourceId;

    public MockName() {
    	// nothing else to do
    }

    public MockName(final Type type) {
        this.type = type;
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

    @Override
    public Long getSourceNameId() {
        return this.sourceId;
    }

    @Override
    public void setSourceNameId(final Long sourceId) {
        this.sourceId = sourceId;
    }

    public void setOfficialName(final boolean officialName) {
        this.officialName = officialName;
    }

    public boolean isOfficialName() {
    	return this.officialName;
    }

    public void setPreferredName(final boolean preferredName) {
        this.preferredName = preferredName;
    }

    public boolean isPreferredName() {
    	return this.preferredName;
    }

    public String getFormattedName(){
        final StringBuilder builder = new StringBuilder();

        construct(builder, "", this.family, ",");
        construct(builder, "", this.given, "");

        return builder.toString();
    }

    public String getLongFormattedName(){
        final StringBuilder builder = new StringBuilder();

        if (this.prefix != null) construct(builder, "", this.prefix, " ");
        construct(builder, "", this.given, " ");
        if (this.middle != null) construct(builder, "", this.middle, " ");
        if (this.family != null) construct(builder, "", this.family, " ");
        if (this.suffix != null) construct(builder, ",", this.suffix, "");

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MockName mockName = (MockName) o;

        if (officialName != mockName.officialName) return false;
        if (preferredName != mockName.preferredName) return false;
        if (family != null ? !family.equals(mockName.family) : mockName.family != null) return false;
        if (given != null ? !given.equals(mockName.given) : mockName.given != null) return false;
        if (id != null ? !id.equals(mockName.id) : mockName.id != null) return false;
        if (middle != null ? !middle.equals(mockName.middle) : mockName.middle != null) return false;
        if (prefix != null ? !prefix.equals(mockName.prefix) : mockName.prefix != null) return false;
        if (suffix != null ? !suffix.equals(mockName.suffix) : mockName.suffix != null) return false;
        if (type != null ? !type.equals(mockName.type) : mockName.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (given != null ? given.hashCode() : 0);
        result = 31 * result + (middle != null ? middle.hashCode() : 0);
        result = 31 * result + (family != null ? family.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        result = 31 * result + (officialName ? 1 : 0);
        result = 31 * result + (preferredName ? 1 : 0);
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getComparisonValue() {
        return null;
    }
}