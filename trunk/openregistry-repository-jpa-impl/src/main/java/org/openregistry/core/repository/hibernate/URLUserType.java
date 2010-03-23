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

package org.openregistry.core.repository.hibernate;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.Hibernate;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class URLUserType implements UserType {

    static final Logger log = LoggerFactory.getLogger(URLUserType.class);

    public int[] sqlTypes() {
        return new int[] {Types.VARCHAR};
    }

    @SuppressWarnings("unchecked")
    public Class returnedClass() {
        return URL.class;
    }

    public boolean equals(final Object x, final Object y) {
        return (x == y) || (x != null && y != null && (x.equals(y)));
    }

    public Object nullSafeGet(final ResultSet inResultSet, final String[] names, final Object o)  throws SQLException {
        final String val =  (String) Hibernate.STRING.nullSafeGet(inResultSet, names[0]);

        try {
            return new URL(val);
        } catch (final MalformedURLException e) {
            log.error("problem creating URL from " + val);
        }
        return null;
    }

    public void nullSafeSet(final PreparedStatement inPreparedStatement, final Object o, final int i) throws SQLException {
        if (o != null){
            inPreparedStatement.setString(i, o.toString());
        } else {
            inPreparedStatement.setNull(i, Types.VARCHAR);
        }
    }

    public Object deepCopy(final Object o) {
        if (o == null) {
            return null;
        }

        try {
            return new URL(o.toString());
        } catch (final MalformedURLException e) {
            log.error("Problem creating deep copy of URL" + o.toString());
        }
        return null;
    }

    public boolean isMutable() {
        return true;
    }

    public Object assemble(final Serializable cached, final Object owner) {
        return deepCopy(cached);
    }

    public Serializable disassemble(final Object value) {
        return (Serializable) deepCopy(value);
    }

    public Object replace(final Object original, final Object target, final Object owner) {
        return deepCopy(original);
    }

    public int hashCode(final Object x) {
        return x.hashCode();
    }
}