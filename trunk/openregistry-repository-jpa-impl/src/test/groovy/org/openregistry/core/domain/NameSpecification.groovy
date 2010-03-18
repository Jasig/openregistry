package org.openregistry.core.domain

import spock.lang.Specification
import org.openregistry.core.domain.jpa.JpaNameImpl

/**
 * @author Dmitriy Kopylenko
 */
class NameSpecification extends Specification {

    def "sameAs() method behaves as expected"() {
        given: "name1 and name2 instances are set up with the same state"
        def name1 = new JpaNameImpl()
        name1.prefix = 'prefixSame'
        name1.given = 'givenSame'
        name1.middle = 'middleSame'
        name1.family = 'familySame'
        name1.suffix = 'suffixSame'
        def name2 = new JpaNameImpl(prefix:name1.prefix, given:name1.given, middle:name1.middle, family:name1.family, suffix:name1.suffix)

        expect:
        name1.sameAs name2
        !name1.sameAs(otherName)

        where: "at least one of the other name's compared attributes is different or the other name instance is null"
        otherName <<
                [new JpaNameImpl(prefix:'prefixSame', given:'givenSame', middle:'middleSame', family:'familySame', suffix:'suffixDiff'),
                 null]
    }
}
