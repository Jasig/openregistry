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
package org.openregistry.core.service;

import org.junit.Before;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class AbstractIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void dataBaseSetUp() throws Exception {
        this.simpleJdbcTemplate.update("insert into prd_identifier_types(identifier_t, name) values(null, 'NETID')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types(id, data_type, description) values(1, 'NAME', 'FORMAL')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(2, 'TERMINATION', 'UNSPECIFIED')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(3, 'FOO', 'Foo Description')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(4, 'TERMINATION', 'FIRED')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(5, 'SPONSOR', 'PERSON')");
        this.simpleJdbcTemplate.update("insert into prd_campuses(id, code, name) values(1, 'cam', 'Busch')");
        this.simpleJdbcTemplate.update("insert into drd_organizational_units(id, campus_id, organizational_unit_t, code, name) values(1, 1, 3, 'cod', 'Department')");
        this.simpleJdbcTemplate.update("insert into prd_roles(id, title, organizational_unit_id, campus_id, affiliation_t, code) values(1, 'Title', 1, 1, 3, 'Code')");
    }
}
