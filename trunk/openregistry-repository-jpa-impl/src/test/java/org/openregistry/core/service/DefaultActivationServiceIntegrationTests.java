package org.openregistry.core.service;

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.openregistry.core.domain.jpa.JpaIdentifierTypeImpl;
import org.openregistry.core.domain.jpa.JpaIdentifierImpl;
import org.openregistry.core.domain.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Integration test for {@link org.openregistry.core.service.DefaultActivationService} that links up with the JPA
 * repositories.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@ContextConfiguration(locations = {"classpath:test-activationServices-context.xml"})
public final class DefaultActivationServiceIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ActivationService activationService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ReferenceRepository referenceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Person person;

    @Before
    public void setUp() throws Exception {
        final Person person = new JpaPersonImpl();
        person.setDateOfBirth(new Date());
        person.setGender("M");
        this.simpleJdbcTemplate.update("insert into prd_identifier_types(identifier_t, name) values(null, 'NetId')");
        final IdentifierType identifierType = this.referenceRepository.findIdentifierType("NetId");
        final Identifier identifier = person.addIdentifier(identifierType, "test");
        identifier.setDeleted(false);
        identifier.setPrimary(true);
        final Name name = person.addName();
        name.setGiven("Scott");
        this.person = this.personRepository.savePerson(person);
    }

    @After
    public void tearDown() throws Exception {
        this.person = person;
    }

    @Test
    public void testGenerateNewActivationKeyWithPerson() {
        final ActivationKey currentActivationKey = person.getCurrentActivationKey();
        assertEquals(1,countRowsInTable("prc_persons"));
        final String oldActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, person.getId());
        assertEquals(currentActivationKey.asString(), oldActivationKeyString);
        final ActivationKey newActivationKey = this.activationService.generateActivationKey(person);
        // TODO: figure out why we need to flush here.  Is it necessary?
        this.entityManager.flush();
        final String newActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, person.getId());
        assertEquals(newActivationKey.asString(), newActivationKeyString);
    }


    @Test
    public void testGenerateNewActivationKeyWithIdentifiers() {
        final ActivationKey currentActivationKey = person.getCurrentActivationKey();
        assertEquals(1,countRowsInTable("prc_persons"));
        final String oldActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, person.getId());
        assertEquals(currentActivationKey.asString(), oldActivationKeyString);
        final ActivationKey newActivationKey = this.activationService.generateActivationKey("NetId", "test");
        // TODO: figure out why we need to flush here.  Is it necessary?
        this.entityManager.flush();
        final String newActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, person.getId());
        assertEquals(newActivationKey.asString(), newActivationKeyString);
    }


    /*
    @Test
    public void testVerifyActivationKeyWithPerson() {

    }

    @Test
    public void testVerifyActivationKeyWithIdentifiers() {

    }

    @Test
    public void testInvalidateActivationKeyWithPerson() {

    }

    public void testInvalidateActivationKeyWithIdentifiers() {

    }  */

    public void setActivationService(final ActivationService activationService) {
        this.activationService = activationService;
    }

    public void setPersonRepository(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
