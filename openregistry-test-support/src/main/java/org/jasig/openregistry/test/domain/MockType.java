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

public class MockType implements Type {

	private static final long serialVersionUID = -6238764806318372669L;
	
	private String type;
	private String description;
	private Long id;
	
	public MockType (final String type, final String desc) {
		this.type = type;
		this.description = desc;
		this.id = 1L;
	}

	public String getDataType() {
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getId() {
		return this.id;
	}

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockType mockType = (MockType) o;

        if (description != null ? !description.equals(mockType.description) : mockType.description != null)
            return false;
        if (id != null ? !id.equals(mockType.id) : mockType.id != null) return false;
        if (type != null ? !type.equals(mockType.type) : mockType.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
