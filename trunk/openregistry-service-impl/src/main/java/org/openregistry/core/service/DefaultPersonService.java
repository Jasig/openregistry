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

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.identifier.*;
import org.openregistry.core.service.reconciliation.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import javax.annotation.*;
import javax.inject.*;
import javax.validation.*;
import java.util.*;

/**
 * Default implementation of the {@link PersonService}.
 *
 * @author Scott Battaglia
 * @author Dmitriy Kopylenko
 * @since 1.0.0
 */
@Named("personService")
public class DefaultPersonService implements PersonService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonRepository personRepository;

    private final ReferenceRepository referenceRepository;

    private final Reconciler reconciler;

    private final IdentifierGenerator identifierGenerator;

    @Autowired(required=false)
    private Map<ReconciliationCriteria,ReconciliationResult> criteriaCache = new EhCacheBackedMapImpl<ReconciliationCriteria, ReconciliationResult>();

    @Autowired(required=false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Autowired(required = false)
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Resource(name = "personFactory")
    private ObjectFactory<Person> personObjectFactory;

    @Inject
    public DefaultPersonService(final PersonRepository personRepository, final ReferenceRepository referenceRepository, final IdentifierGenerator identifierGenerator, final Reconciler reconciler) {
        this.personRepository = personRepository;
        this.referenceRepository = referenceRepository;
        this.identifierGenerator = identifierGenerator;
        this.reconciler = reconciler;
    }

    public void setPersonObjectFactory(final ObjectFactory<Person> objectFactory) {
        this.personObjectFactory = objectFactory;
    }

    public void setCriteriaCache(final Map<ReconciliationCriteria,ReconciliationResult> criteriaCache) {
        this.criteriaCache = criteriaCache;
    }

    public void setIdentifierAssigners(final List<IdentifierAssigner> identifierAssigners) {
        this.identifierAssigners = identifierAssigners;
    }

    public void setValidator(final Validator validator) {
        this.validator = validator;
    }

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
    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSourceIdentifier) {
        try {
          return this.personRepository.findByPersonIdAndSorIdentifier(personId, sorSourceIdentifier);
        } catch (Exception e){
            // TODO we need to log this better.
          return null;
        }
    }

    @Transactional
    public SorPerson findBySorIdentifierAndSource(final String sorSource, final String sorId){
        try {
            return this.personRepository.findBySorIdentifierAndSource(sorSource, sorId);
        } catch (Exception e){
            return null;
        }
    }

    /**
     * This does not explicitly delete the names because its assumed the recalculation will clean it up.
     */
    @Transactional
    public boolean deleteSystemOfRecordPerson(final SorPerson sorPerson, final boolean mistake, final String terminationTypes) {
        Assert.notNull(sorPerson, "sorPerson cannot be null.");
        final String terminationTypeToUse = terminationTypes != null ? terminationTypes : Type.TerminationTypes.UNSPECIFIED.name();

        final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        Assert.notNull(person, "person cannot be null.");

        if (mistake) {
            for (final SorRole sorRole : sorPerson.getRoles()) {
                for (final Iterator<Role> iter  = person.getRoles().iterator(); iter.hasNext();) {
                    final Role role = iter.next();
                    if (sorRole.getId().equals(role.getSorRoleId())) {
                        iter.remove();
                    }
                }
            }

            final Number number = this.personRepository.getCountOfSoRRecordsForPerson(person);

            if (number.intValue() == 1) {
                this.personRepository.deletePerson(person);
            }

            this.personRepository.deleteSorPerson(sorPerson);
            return true;
        }

        //we do this explicitly here because once they're gone we can't re-calculate?  We might move to this to the recalculateCalculatedPerson method.
        final Type terminationReason = this.referenceRepository.findType(Type.DataTypes.TERMINATION, terminationTypeToUse);

        for (final SorRole sorRole : sorPerson.getRoles()) {
            for (final Role role : person.getRoles()) {
                if (!role.isTerminated() && sorRole.getId().equals(role.getSorRoleId())) {
                    role.expireNow(terminationReason, true);
                }
            }
        }

        this.personRepository.deleteSorPerson(sorPerson);
        this.personRepository.savePerson(person);
        return true;
    }


    @Transactional
    public boolean deleteSystemOfRecordPerson(final String sorSource, final String sorId, final boolean mistake, final String terminationTypes) {
        Assert.notNull(sorSource, "sorSource cannot be null.");
        Assert.notNull(sorId, "sorId cannot be null.");
        final SorPerson sorPerson = this.personRepository.findBySorIdentifierAndSource(sorSource, sorId);

        return sorPerson != null && deleteSystemOfRecordPerson(sorPerson, mistake, terminationTypes);
    }

	@Transactional
	public boolean deleteSystemOfRecordRole(SorPerson sorPerson, SorRole sorRole, boolean mistake, final String terminationTypes) throws IllegalArgumentException{
		Assert.notNull(sorRole, "sorRole cannot be null.");
		Assert.notNull(sorPerson, "soPerson cannot be null.");
		final String terminationTypeToUse = terminationTypes != null ? terminationTypes : Type.TerminationTypes.UNSPECIFIED.name();

		final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
		Assert.notNull(person, "person cannot be null.");

		// TODO: Do we need to iterate through all the calculated roles or can we just grab the one role that associates with the SoR Role?
		if(mistake){
			for (final Iterator<Role> iter  = person.getRoles().iterator(); iter.hasNext();) {
				final Role role = iter.next();
				if (sorRole.getId().equals(role.getSorRoleId())) {
					iter.remove();
				}
			}
		} else {
			final Type terminationReason = this.referenceRepository.findType(Type.DataTypes.TERMINATION, terminationTypeToUse);
			for (final Role role : person.getRoles()) {
				if (!role.isTerminated() && sorRole.getId().equals(role.getSorRoleId())) {
					role.expireNow(terminationReason, true);
				}
			}
		}
		this.personRepository.deleteSorRole(sorPerson, sorRole);
		this.personRepository.savePerson(person);
		return true;
	}

    @Transactional
    public ServiceExecutionResult<SorRole> validateAndSaveRoleForSorPerson(final SorPerson sorPerson, final SorRole sorRole) {
        Assert.notNull(sorPerson, "SorPerson cannot be null.");
        Assert.notNull(sorRole, "SorRole cannot be null.");

        // check if the SoR Role has an ID assigned to it already
        if (!StringUtils.hasText(sorRole.getSorId())) {
            sorRole.setSorId(this.identifierGenerator.generateNextString());
        }

        final Set validationErrors = this.validator.validate(sorRole);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<SorRole>(validationErrors);
        }

        sorRole.setSourceSorIdentifier(sorPerson.getSourceSor());

        final SorPerson newSorPerson = this.personRepository.saveSorPerson(sorPerson);
        final Person person = this.personRepository.findByInternalId(newSorPerson.getPersonId());
        final SorRole newSorRole = newSorPerson.findSorRoleBySorRoleId(sorRole.getSorId());
        person.addRole(newSorRole);
        this.personRepository.savePerson(person);

        return new GeneralServiceExecutionResult<SorRole>(newSorRole);
    }

    @Transactional
    public ServiceExecutionResult<Person> addPerson(final ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null");

        if (reconciliationCriteria.getSorPerson().getSorId() != null &&
            this.findBySorIdentifierAndSource(reconciliationCriteria.getSorPerson().getSourceSor(),reconciliationCriteria.getSorPerson().getSorId()) != null){
            throw new IllegalStateException("CANNOT ADD SAME SOR RECORD.");
        }
        // TODO this might fail because it doesn't match.
        final Set validationErrors = this.validator.validate(reconciliationCriteria);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<Person>(validationErrors);
        }

        final ReconciliationResult result = this.reconciler.reconcile(reconciliationCriteria);

        switch (result.getReconciliationType()) {
            case NONE:
                return new GeneralServiceExecutionResult<Person>(magic(reconciliationCriteria));

            case EXACT:
                return new GeneralServiceExecutionResult<Person>(magicUpdate(reconciliationCriteria, result));
        }

        this.criteriaCache.put(reconciliationCriteria, result);
        throw new ReconciliationException(result);
    }

    @Transactional
    public ServiceExecutionResult<Person> forceAddPerson(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException, IllegalStateException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        final ReconciliationResult result = this.criteriaCache.get(reconciliationCriteria);

        if (result == null) {
            throw new IllegalStateException("No ReconciliationResult found for provided criteria.");
        }

        this.criteriaCache.remove(reconciliationCriteria);

        return new GeneralServiceExecutionResult<Person>(magic(reconciliationCriteria));
    }

    public ServiceExecutionResult<Person> addPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) throws IllegalArgumentException, IllegalStateException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        Assert.notNull(person, "person cannot be null.");

        final ReconciliationResult result = this.criteriaCache.get(reconciliationCriteria);

        if (result == null) {
            throw new IllegalStateException("No ReconciliationResult found for provided criteria.");
        }

        for (final PersonMatch personMatch : result.getMatches()) {
            if (personMatch.getPerson().equals(person)) {
                addSorPersonAndLink(reconciliationCriteria, person);
                final Person savedPerson = this.personRepository.findByInternalId(person.getId());
                recalculatePersonBiodemInfo(savedPerson);
                return new GeneralServiceExecutionResult<Person>(savedPerson);
            }
        }

        throw new IllegalStateException("Person not found in ReconciliationResult.");
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

    // TODO Need to update the calculated person. Need to establish rules to do this. OR-59
    protected void recalculatePersonBiodemInfo(final Person person) {
        final List<SorPerson> persons = this.personRepository.getSoRRecordsForPerson(person);
        //* 1. Choosing the appropriate names (and removing any unused names)
        //* 2. Transitioning SorPerson information to Calculated Person (i.e. choosing)


    }

    /**
     * Current workflow for converting an SorPerson into the actual Person.
     *
     * @param reconciliationCriteria the original search criteria.
     * @return the newly saved Person.
     */
    protected Person magic(final ReconciliationCriteria reconciliationCriteria) {
        if (!StringUtils.hasText(reconciliationCriteria.getSorPerson().getSorId())) {
            reconciliationCriteria.getSorPerson().setSorId(this.identifierGenerator.generateNextString());
        }

        // Save Sor Person
        final SorPerson sorPerson = this.personRepository.saveSorPerson(reconciliationCriteria.getSorPerson());

        // Construct actual person from Sor Information
        final Person person = constructPersonFromSorData(sorPerson);

        // Now connect the SorPerson to the actual person
        sorPerson.setPersonId(person.getId());

        return person;
    }

    protected Person constructPersonFromSorData(SorPerson sorPerson){
        // Construct actual person from Sor Information
        final Person person = this.personObjectFactory.getObject();
        person.setDateOfBirth(sorPerson.getDateOfBirth());
        person.setGender(sorPerson.getGender());

        //initialize the name to be both official and preferred.
        final Name name = person.addOfficialName();
        name.setPreferredName(true);

        // There should only be one at this point.
        // TODO generalize this to all names
        final Name sorName = sorPerson.getNames().iterator().next();
        name.setFamily(sorName.getFamily());
        name.setGiven(sorName.getGiven());
        name.setMiddle(sorName.getMiddle());
        name.setPrefix(sorName.getPrefix());
        name.setSuffix(sorName.getSuffix());
        name.setType(sorName.getType());

        // Assign identifiers, including SSN from the SoR Person
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, person);
        }

        return this.personRepository.savePerson(person);
    }

    protected Person addSorPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) {
        final SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        final SorPerson registrySorPerson = this.findByPersonIdAndSorIdentifier(person.getId(), sorPerson.getSourceSor());

        if (registrySorPerson != null) {
            // TODO: replace this with what should happen
            throw new IllegalStateException("THIS SHOULD NOT HAPPEN.");
        }

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }

        sorPerson.setPersonId(person.getId());
        this.personRepository.saveSorPerson(sorPerson);

		return person;
    }

    protected Person magicUpdate(final ReconciliationCriteria reconciliationCriteria, final ReconciliationResult result) {
        Assert.isTrue(result.getMatches().size() == 1, "ReconciliationResult should be 'EXACT' and there should only be one person.  The result is '" + result.getReconciliationType() + "' and the number of people is " + result.getMatches().size() + ".");

        final Person person = result.getMatches().iterator().next().getPerson();
        return addSorPersonAndLink(reconciliationCriteria, person);
	}

    /**
     * Persists an SorPerson on update.
     *
     * @param sorPerson the person to update.
     * @return serviceExecutionResult.
     */
    @Transactional
    public ServiceExecutionResult<SorPerson> updateSorPerson(final SorPerson sorPerson) {

        final Set validationErrors = this.validator.validate(sorPerson);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<SorPerson>(validationErrors);
        }

        //do reconciliationCheck to make sure that modifications do not cause person to reconcile to a different person
        if (!this.reconciler.reconcilesToSamePerson(sorPerson)) throw new IllegalStateException();

        // Save Sor Person
        logger.info("PersonService:updateSorPerson: updating person...");
        SorPerson savedSorPerson = this.personRepository.saveSorPerson(sorPerson);

        final Person person = this.findPersonById(savedSorPerson.getPersonId());
        Assert.notNull(person, "person cannot be null.");

        recalculatePersonBiodemInfo(person);

        return new GeneralServiceExecutionResult<SorPerson>(savedSorPerson);

    }

    /**
     * Persists an SorRole on update.
     *
     * @param role to update.
     * @return serviceExecutionResult.
     */
    @Transactional
    public ServiceExecutionResult<SorRole> updateSorRole(SorRole role) {
        final Set validationErrors = this.validator.validate(role);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<SorRole>(validationErrors);
        }

        logger.info("PersonService:updateSorPerson: updating role...");
        // Save Sor Person
        role = this.personRepository.saveSorRole(role);

        return new GeneralServiceExecutionResult<SorRole>(role);

        // TODO Need to update the calculated role. Need to establish rules to do this. OR-58
    }

    @Transactional
    public boolean removeSorName(SorPerson sorPerson, Long nameId){
        Name name = sorPerson.findNameByNameId(nameId);
        if (name == null) return false;

        // remove name from the set (annotation in set to delete orphans)
        sorPerson.getNames().remove(name);

        // save changes
        this.personRepository.saveSorPerson(sorPerson);
        return true;
    }

    /**
     * Move All Sor Records from one person to another.
     *
     * @param fromPerson person losing sor records.
     * @param toPerson person receiving sor records.
     * @return Result of move. Validation errors if they occurred or the Person receiving sor records.
     */
    @Transactional
    public boolean moveAllSystemOfRecordPerson(Person fromPerson, Person toPerson){
        // get the list of sor person records that will be moving.
        List<SorPerson> sorPersonList =  personRepository.getSoRRecordsForPerson(fromPerson);

        // move each sorRecord
        for (final SorPerson sorPerson : sorPersonList) {
            moveSystemOfRecordPerson(fromPerson, toPerson, sorPerson);
        }

        // TODO Delete from person - need to determine how to deal with names before this can work.
        //this.personRepository.deletePerson(fromPerson);
        logger.info("moveAllSystemOfRecordPerson: Deleted From Person");
        return true;
    }

    /**
     * Move one Sor Record from one person to another.
     *
     * @param fromPerson person losing sor record.
     * @param toPerson person receiving sor record.
     * @return Success or failure.
     */
    @Transactional
    public boolean moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson movingSorPerson){
        movingSorPerson.setPersonId(toPerson.getId());
        updateCalculatedPersonsOnMoveOfSor(toPerson, fromPerson, movingSorPerson);
        this.personRepository.saveSorPerson(movingSorPerson);
        return true;
    }

    /**
     * Move one Sor Record from one person to another where the to person is not in the registry
     *
     * @param fromPerson person losing sor record.
     * @param movingSorPerson record that is moving.
     * @return Success or failure.
     */
    @Transactional
    public boolean moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson movingSorPerson){
        // create the new person in the registry
        Person toPerson = constructPersonFromSorData(movingSorPerson);
        return moveSystemOfRecordPerson(fromPerson, toPerson, movingSorPerson);
    }

    /**
     * Update the calculated person data. This method and updateCalculatedPersonOnDeleteOfSor
     * need to be generalized to handle recalculations.
     *
     * @param toPerson
     * @param fromPerson
     * @param sorPerson
     *
     * Adjust calculated roles...
     * Point prc_role to prc_person receiving role
     * Add the role to the set of roles in receiving prc_person
     * Remove role from prc person losing role
     */
    protected void updateCalculatedPersonsOnMoveOfSor(final Person toPerson, final Person fromPerson, final SorPerson sorPerson) {
        Assert.notNull(toPerson, "toPerson cannot be null");
        Assert.notNull(fromPerson, "fromPerson cannot be null");
        logger.info("UpdateCalculated: recalculating person data for move.");

        final List<Role> rolesToDelete = new ArrayList<Role>();

        final List<SorRole> sorRoles = new ArrayList<SorRole>(sorPerson.getRoles());
        for (final SorRole sorRole : sorRoles) {
            for (final Role role : fromPerson.getRoles()) {
                if (sorRole.getId().equals(role.getSorRoleId())) {
                    toPerson.addRole(sorRole);
                    rolesToDelete.add(role);
                }
            }
        }
        for (final Role role : rolesToDelete){
            fromPerson.getRoles().remove(role);
        }

        // TODO recalculate names for person receiving role? Anything else?
        // TODO recalculate names for person losing role? Anything else?
        this.personRepository.savePerson(fromPerson);
        this.personRepository.savePerson(toPerson);
    }

    public SorPerson hasSorRecordFromSameSource(Person fromPerson, Person toPerson){
        SorPerson sorPerson = null;

        //TODO need to complete this.
        return sorPerson;
    }

    public boolean expireRole(SorRole role){
        role.setEnd(new Date());
        return true;
    }

    public boolean renewRole(SorRole role){
        final Calendar cal = Calendar.getInstance();

        //TODO need to read configuration data for setting the default renewal date for this role type.
        //hard code to 6 months for now.
        cal.add(Calendar.MONTH, 6);
        role.setEnd(cal.getTime());
        return true;
    }

}
