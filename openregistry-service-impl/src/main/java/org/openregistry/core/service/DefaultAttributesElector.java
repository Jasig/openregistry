package org.openregistry.core.service;

import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorPerson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lame implementation that elects no attributes
 * User: msidd
 * Date: 5/24/13
 * Time: 10:44 AM

 */
public class DefaultAttributesElector implements FieldElector<Map<String,String>> {

    @Override
    public Map<String,String> elect(SorPerson newestPerson, List<SorPerson> sorPersons, final boolean deleted) {

        return new HashMap<String, String>();
    }
}
