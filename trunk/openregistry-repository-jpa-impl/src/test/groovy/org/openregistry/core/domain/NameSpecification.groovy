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

package org.openregistry.core.domain

import spock.lang.Specification
import org.openregistry.core.domain.jpa.JpaNameImpl
import org.openregistry.core.domain.jpa.sor.JpaSorNameImpl

/**
 * @author Dmitriy Kopylenko
 */
class NameSpecification extends Specification {

    def "sameAs() method behaves as expected"() {
        given: "name1 and name2 instances are set up with the same state"
        def name1 = new JpaNameImpl()
        name1.prefix = 'Prefixsame'
        name1.given = 'Givensame'
        name1.middle = 'Middlesame'
        name1.family = 'Familysame'
        name1.suffix = 'Suffixsame'
        def name2 = new JpaSorNameImpl(prefix:name1.prefix, given:name1.given, middle:name1.middle, family:name1.family, suffix:name1.suffix)

        expect:
        name1.sameAs name2
        !name1.sameAs(otherName)

        where: "at least one of the other name's compared attributes is different or the other name instance is null"
        otherName <<
                [new JpaSorNameImpl(prefix:'prefixSame', given:'givenSame', middle:'middleSame', family:'familySame', suffix:'suffixDiff'),
                 null]
    }
}
