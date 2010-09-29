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

package org.openregistry.core.service.identifier;

import org.springframework.jdbc.support.incrementer.*;

/*
 * Class which implements the IdentifierGenerator interface and wraps
 * an org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer.
 * A db-specific implementation is injected at runtime.
 */

public class DBIdentifierGenerator implements IdentifierGenerator {

	private AbstractDataFieldMaxValueIncrementer incrementer;

	public DBIdentifierGenerator(AbstractDataFieldMaxValueIncrementer incrementer) {
		super();
		this.incrementer = incrementer;
	}

	/**
	 * Generates the next value in the generator and returns it as a long.
	 *
	 * @return the next value as a long.
	 */
	@Override
	public long generateNextLong() {
		return incrementer.nextLongValue();
	}

	/**
	 * Generates the next value as a String.
	 *
	 * @return the next value as a string.
	 */
	@Override
	public String generateNextString() {
		return incrementer.nextStringValue();
	}

	/**
	 * Generates the next value as an integer.
	 *
	 * @return the next value as an integer.
	 */
	@Override
	public int generateNextInt() {
		return incrementer.nextIntValue();
	}
}
