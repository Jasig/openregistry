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

import java.util.regex.Pattern;

import org.openregistry.core.domain.IdentifierType;

/**
 * An identifier type for testing that is not deleted, not private and not modifiable
 * The name and the notifiable property are set by constructor
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockIdentifierType implements IdentifierType {
	
	private String name;
	private boolean notifiable;
	
	public MockIdentifierType(String name, boolean notifiable) {
		this.name = name;
		this.notifiable = notifiable;
	}
	
	@Override
	public String getDescription() {
		return "Description of "+name;
	}

	@Override
	public Pattern getFormatAsPattern() {
		return null;
	}

	@Override
	public String getFormatAsString() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isDeleted() {
		return false;
	}

	@Override
	public boolean isModifiable() {
		return false;
	}

	@Override
	public boolean isNotifiable() {
		return notifiable;
	}

	@Override
	public boolean isPrivate() {
		return false;
	}
}
