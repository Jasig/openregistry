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

package org.openregistry.core.domain.jpa.sor;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.sor.SystemOfRecord;

import javax.persistence.*;

/**
 * JPA-backed implementation of the SystemOfRecord.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@Entity(name = "systemOfRecord")
@Table(name = "prd_system_of_record")
@Audited
public class JpaSystemOfRecordImpl implements SystemOfRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_system_of_record_seq")
    @SequenceGenerator(name="prd_system_of_record_seq",sequenceName="prd_system_of_record_seq",initialValue=1,allocationSize=50)
    private Long id;

    @Column(name = "sor_id", insertable = true, length = 100, nullable = false, unique = true)
    private String sorId;

    @Override
    public String getSorId() {
        return this.sorId;
    }
}
