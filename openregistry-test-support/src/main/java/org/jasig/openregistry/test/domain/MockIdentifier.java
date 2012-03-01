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

import java.util.Date;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;

public class MockIdentifier implements Identifier {

	private Date creationDate;
	private Date notificationDate;
	private Date deletedDate;
	private IdentifierType type;
	private String value;
	private boolean primary;
    private boolean changeable = false;

    public MockIdentifier() {
    }

    public MockIdentifier(final Person person) {
    }

    public MockIdentifier(final Person person, final IdentifierType idType, final String value) {
        this(person);
        this.type = idType;
        this.value = value;
    }

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

    @Override
    public void setCreationDate(Date originalCreationDate) {
        this.creationDate=originalCreationDate;
    }
    @Override
    public void setDeletedDate(Date date) {

        this.deletedDate=date;
    }

    @Override
	public Date getDeletedDate() {
		return deletedDate;
	}

	@Override
	public Date getNotificationDate() {
		return notificationDate;
	}

	@Override
	public IdentifierType getType() {
		return type;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isDeleted() {
		return (deletedDate != null);
	}

	@Override
	public boolean isPrimary() {
		return primary;
	}

	@Override
	public void setDeleted(boolean value) {
		deletedDate = value?new Date():null;

	}
	@Override
	public void setPrimary(boolean value) {
		primary = value;
	}

	@Override
	public void setNotificationDate(Date date) {
		notificationDate = new Date(date.getTime());
	}

    @Override
    public boolean isChangeable() {
        return changeable;
    }

    @Override
    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }
}