package org.openregistry.core.service;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: msidd
 * Date: 8/2/11
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearcCriteriaTests extends TestCase {

    @Test
    public void testCapatalizationFirstName(){
        MutableSearchCriteriaImpl searchCriteria= new MutableSearchCriteriaImpl();
        searchCriteria.setGivenName("given");
        assertEquals("Given",searchCriteria.getGivenName());
    }
    @Test
    public void testCapatalizationLastName(){
        MutableSearchCriteriaImpl searchCriteria= new MutableSearchCriteriaImpl();
        searchCriteria.setFamilyName("family");
        assertEquals("Family",searchCriteria.getFamilyName());
    }

    @Test
    public void testCaptalizationLastNameTwoName(){
        MutableSearchCriteriaImpl searchCriteria= new MutableSearchCriteriaImpl();
        searchCriteria.setFamilyName("family-name");
        assertEquals("Family-Name",searchCriteria.getFamilyName());
    }
    @Test
    public void testCaptalizationName(){
        MutableSearchCriteriaImpl searchCriteria= new MutableSearchCriteriaImpl();
        searchCriteria.setName("family");
        assertEquals("Family",searchCriteria.getName());
    }
}
