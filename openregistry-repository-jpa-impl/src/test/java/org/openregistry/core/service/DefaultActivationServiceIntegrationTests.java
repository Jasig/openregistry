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

    @Test
    public void testGenerateNewActivationKeyWithPerson() {
        final Person person = new JpaPersonImpl();
        person.setDateOfBirth(new Date());
        person.setGender("M");
        final Name name = person.addName();
        name.setGiven("Scott");


        final Person newPerson = this.personRepository.savePerson(person);
        final ActivationKey currentActivationKey = newPerson.getCurrentActivationKey();
        assertEquals(1,countRowsInTable("prc_persons"));
        final String oldActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, newPerson.getId());
        assertEquals(currentActivationKey.asString(), oldActivationKeyString);
        final ActivationKey newActivationKey = this.activationService.generateActivationKey(newPerson);
        // TODO: figure out why we need to flush here.  Is it necessary?
        this.entityManager.flush();
        final String newActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, newPerson.getId());
        assertEquals(newActivationKey.asString(), newActivationKeyString);
    }


    @Test
    public void testGenerateNewActivationKeyWithIdentifiers() {
        final Person person = new JpaPersonImpl();
        person.setDateOfBirth(new Date());
        person.setGender("M");
        executeSqlScript("insert into prd_identifier_types(identifier_t, name) values(null, 'NetId')", false);
        final IdentifierType identifierType = this.referenceRepository.findIdentifierType("NetId");
        final Identifier identifier = person.addIdentifier();
        identifier.setValue("test");
        identifier.setType(identifierType);
        final Name name = person.addName();
        name.setGiven("Scott");

        final Person newPerson = this.personRepository.savePerson(person);
        final ActivationKey currentActivationKey = newPerson.getCurrentActivationKey();
        assertEquals(1,countRowsInTable("prc_persons"));
        final String oldActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, newPerson.getId());
        assertEquals(currentActivationKey.asString(), oldActivationKeyString);
        final ActivationKey newActivationKey = this.activationService.generateActivationKey("NetId", "test");
        // TODO: figure out why we need to flush here.  Is it necessary?
        this.entityManager.flush();
        final String newActivationKeyString = this.simpleJdbcTemplate.queryForObject("select activation_key from prc_persons where id = ?", String.class, newPerson.getId());
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
