package org.openregistry.core.service;

import com.sun.tools.corba.se.idl.constExpr.Equal;
import org.jasig.openregistry.test.domain.MockIdentifier;
import org.jasig.openregistry.test.domain.MockIdentifierType;
import org.jasig.openregistry.test.domain.MockPerson;
import org.jasig.openregistry.test.repository.MockPersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openregistry.core.domain.Identifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: msidd
 * Date: 8/25/11
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-defaultIdentifierChangeService-context.xml")
public class DefaultIdentifierChangeServiceTests {
    @Inject
    private DefaultIdentifierChangeService identifierChangeService;
     @Inject
    private MockPerson mockPerson;

    @Inject
    private MockPerson mockPerson2;

    @Inject
    private MockPersonRepository mockPersonRepository;

    @Test
    public void testChangeIdentifier(){

        Identifier id = mockPerson.addIdentifier(new MockIdentifierType("SSN",true),"555");
        id.setPrimary(true);
        id.setDeleted(false);
        assertTrue(identifierChangeService.change(mockPerson.getIdentifiersByType().get("SSN").getFirst(), "999"));
        assertFalse(mockPerson.getPrimaryIdentifiersByType().get("SSN").equals(id));

    }
    @Test
    public void testChangeIdentifierNOP(){

        Identifier id = mockPerson2.addIdentifier(new MockIdentifierType("SSN",true),"555");
        id.setPrimary(true);
        id.setDeleted(false);
        assertFalse(identifierChangeService.change(mockPerson2.getIdentifiersByType().get("SSN").getFirst(), "555"));
        assertTrue( mockPerson2.getPrimaryIdentifiersByType().get("SSN").equals(id));

    }

}
