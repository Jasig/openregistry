package org.openregistry.service;

import org.openregistry.core.service.*;
import org.openregistry.core.service.identifier.IdentifierAssigner;
import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.FieldMatch;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Type.Types;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.service.reconciliation.PersonMatchImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.ValidationMessage;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the {@link PersonService}.
 *
 * @author Scott Battaglia
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 */
@Service("personService")
public class DefaultPersonService implements PersonService {

    @Autowired(required = true)
    private PersonRepository personRepository;

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    @Autowired(required = false)
    private AnnotationValidator<Object> annotationValidator = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    @Autowired(required=true)
    private Reconciler reconciler;

    @Autowired(required=true)
    @Qualifier(value = "person")
    private ObjectFactory<Person> personObjectFactory;

    @Autowired(required=false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Autowired(required=true)
    private IdentifierGenerator identifierGenerator;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }
    
    @Transactional
    public Person findPersonByIdentifier(final String identifierType, final String identifierValue) {
        try {
            return this.personRepository.findByIdentifier(identifierType,identifierValue);
        } catch (final Exception e) {
            return null;
        }
    }

    @Transactional
    public boolean deletePerson(final Person person) {
        try {
            final Number number = this.personRepository.getCountOfSoRRecordsForPerson(person);

            if (number.intValue() == 0) {
                this.personRepository.deletePerson(person);
                return true;
            }
            
            return false;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Transactional
    public boolean deleteSystemOfRecordPerson(final SorPerson sorPerson) {
        try {
          updateCalculatedPersonOnDeleteOfSor(sorPerson);
          this.personRepository.deleteSorPerson(sorPerson);
            return true;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Transactional
    public boolean deleteSystemOfRecordPerson(final String sorSourceIdentifier, final String sorId) {
        try {
          final SorPerson sorPerson = this.personRepository.findBySorIdentifierAndSource(sorSourceIdentifier, sorId);

            if (sorPerson == null) {
                return false;
            }

            updateCalculatedPersonOnDeleteOfSor(sorPerson);
            this.personRepository.deleteSorPerson(sorPerson);
            return true;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    protected void updateCalculatedPersonOnDeleteOfSor(final SorPerson sorPerson) {
        // TODO something should happen here!        
    }

    @Transactional
    public boolean deleteSorRole(final Person person, final Role role, final String terminationReason) {
        try {
            final SorPerson sorPerson = this.personRepository.findSorPersonByPersonIdAndSorRoleId(person.getId(), role.getId());
            if (sorPerson != null) {
                final SorRole sorRole= sorPerson.removeRoleByRoleId(role.getId());

                if (sorRole != null) {
                    removeSorRoleFromDatabase(sorPerson, sorRole, person, role, terminationReason);
                    return true;
                }
            }
            return false;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public SorPerson findSorPersonByIdentifierAndSourceIDentifier(final String identifierType, final String identifierValue, final String sorSourceId) {
        try {
            final Person person = this.personRepository.findByIdentifier(identifierType, identifierValue);

            if (person == null) {
                return null;
            }

            return this.personRepository.findByPersonIdAndSorIdentifier(person.getId(), sorSourceId);
        } catch (final Exception e) {
            return null;
        }
    }

    public boolean deleteSorRole(SorPerson sorPerson, SorRole sorRole, String terminationReason) throws IllegalArgumentException {
        try {
            final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
            if (person == null) {
                return false;
            }

            for (final Role role : person.getRoles()) {
                if (role.getId().equals(sorRole.getRoleId())) {
                    removeSorRoleFromDatabase(sorPerson, sorRole, person, role, terminationReason);
                    return true;
                }
            }

            return false;
        } catch (final RepositoryAccessException e) {
            return false;
        }
    }

    @Transactional
    public ServiceExecutionResult validateAndSaveRoleForPerson(final Person person, final Role role) {
        final String serviceName = "PersonService.validateAndSaveRoleForPerson";
        final List<ValidationError> validationErrors = validateAndConvert(role);

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, role, validationErrors);
        }

        this.personRepository.savePerson(person);
        return new DefaultServiceExecutionResult(serviceName, role);
    }

    @Transactional
    public ServiceExecutionResult addPerson(final PersonSearch personSearch, final ReconciliationResult oldReconciliationResult) {
        logger.info("In personService.addPerson.");
        logger.info("In personService.addPerson: entered following for gender: "+ personSearch.getPerson().getGender());
        final List<ValidationError> validationErrors = validateAndConvert(personSearch);
        final String serviceName = "PersonService.addPerson";

        if (!validationErrors.isEmpty()) {
            return new DefaultServiceExecutionResult(serviceName, personSearch, validationErrors);
        }


        if (oldReconciliationResult == null) {
            final ReconciliationResult result = this.reconciler.reconcile(personSearch);

            if (result.getReconciliationType() == ReconciliationResult.ReconciliationType.NONE) {
                return new DefaultServiceExecutionResult(serviceName, magic(personSearch), result);
            } else if (result.getReconciliationType() == ReconciliationResult.ReconciliationType.EXACT) {
            	return new DefaultServiceExecutionResult(serviceName, magicUpdate(personSearch, result), result);
            }
            logger.info("In personService.addPerson: reconciliation result: "+ result.getReconciliationType().toString());
            // ReconciliationResult.ReconciliationType.MAYBE
            return new DefaultServiceExecutionResult(serviceName, personSearch, result);
        }
        // Here if we were called a second time.  This means even though we found partial matches, this is a new person.
        return new DefaultServiceExecutionResult(serviceName, magic(personSearch));
    }

	@Transactional
    public List<PersonMatch> searchForPersonBy(final SearchCriteria searchCriteria) {
        final List<PersonMatch> personMatches = new ArrayList<PersonMatch>();

        if (StringUtils.hasText(searchCriteria.getIdentifierValue())) {
            try {
                final Person person = this.personRepository.findByIdentifier(searchCriteria.getIdentifierType(), searchCriteria.getIdentifierValue());

                if (person != null) {
                    final PersonMatch p = new PersonMatchImpl(person, 100, new ArrayList<FieldMatch>());
                    personMatches.add(p);
                    return personMatches;
                }
            } catch (final Exception e) {

            }
        }

        final List<Person> persons = this.personRepository.searchByCriteria(searchCriteria);

        for (final Person person : persons) {
            // TODO actually calculate the value for the match
            final PersonMatch p = new PersonMatchImpl(person, 50, new ArrayList<FieldMatch>());
            personMatches.add(p);
        }

        return personMatches;
    }

    /**
     * Removes the SoR record from the database as well as updates the calculated role.
     *
     * @param sorPerson the system of record person
     * @param sorRole the system of record role
     * @param person the calculated person
     * @param role the calculated role
     * @param terminationReason the reason for termination
     */
    protected void removeSorRoleFromDatabase(final SorPerson sorPerson, final SorRole sorRole, final Person person, final Role role, final String terminationReason) {
        this.personRepository.deleteSorRole(sorPerson, sorRole);
        try {
            final Type terminationType = this.referenceRepository.findType(Types.TERMINATION.name(), terminationReason);

            role.setEnd(new Date());
            role.setTerminationReason(terminationType);
            this.personRepository.updateRole(person, role);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Validates the object using the JaValid annotation framework and then converts the errors into the OpenRegistry API for errors.
     *
     * @param object the object to check
     * @return the list of validation errors.  CANNOT be NULL.  Can be empty.
     */
    protected List<ValidationError> validateAndConvert(final Object object) {
        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object, "1", "", true, -1);
//        final List<ValidationMessage> validationMessages = this.annotationValidator.validateObject(object);
        logger.info("In personService.validateAndConvert: size of messages: "+ validationMessages.size());
        return convertToValidationErrors(validationMessages);
    }

    /**
     * Converts the validation errors from JaValid into the OpenRegistry API validation errors.
     *
     * @param messages the JaValid messages to convert.
     * @return the list of validation errors.  CANNOT be null.  CAN be empty.
     */
    protected List<ValidationError> convertToValidationErrors(final List<ValidationMessage> messages) {
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        for (final ValidationMessage message : messages) {
            validationErrors.add(new JaValidValidationError(message));
        }

        return validationErrors;
    }

    /**
     * Current workflow for converting an SorPerson into the actual Person.
     *
     * @param personSearch the original search criteria.
     * @return the newly saved Person.
     */
    protected Person magic(final PersonSearch personSearch) {
        SorPerson sorPerson = personSearch.getPerson();

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }

        // Save Sor Person
        sorPerson = this.personRepository.saveSorPerson(sorPerson);

        // Construct actual person from Sor Information
        Person person = personObjectFactory.getObject();
        person.setDateOfBirth(sorPerson.getDateOfBirth());
        person.setGender(sorPerson.getGender());
        final Name name = person.addOfficialName();

        // There should only be one at this point.
        // TODO generalize this to all names
        final Name sorName = sorPerson.getNames().iterator().next();

        name.setFamily(sorName.getFamily());
        name.setGiven(sorName.getGiven());
        name.setMiddle(sorName.getMiddle());
        name.setPrefix(sorName.getPrefix());
        name.setSuffix(sorName.getSuffix());

        // Assign identifiers, including SSN from the SoR Person
        // TODO set SSN as an Identifier, it should be one of the IdentifierAssigners
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, person);
        }

        // Save into the repository
        person = this.personRepository.savePerson(person);
        
        // Now connect the SorPerson to the actual person
        sorPerson.setPersonId(person.getId());
        
        return person;
    }
    
    protected Person magicUpdate(final PersonSearch personSearch, final ReconciliationResult result) {
    	if (result.getMatches().size() != 1) {
        	throw new IllegalStateException("ReconciliationResult should be 'EXACT' and there should only be one person.  The result is '" + result.getReconciliationType() + "' and the number of people is " + result.getMatches().size() + ".");
        }
        Person person = result.getMatches().iterator().next().getPerson();
        
    	SorPerson sorPerson = personSearch.getPerson();
    	
    	// TODO if this is the same SOR, just update fields, don't save new SOR records

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }
        // Now connect the SorPerson to the actual person
        sorPerson.setPersonId(person.getId());
        // Save Sor Person
        sorPerson = this.personRepository.saveSorPerson(sorPerson);
        
		return person;
	}
}
