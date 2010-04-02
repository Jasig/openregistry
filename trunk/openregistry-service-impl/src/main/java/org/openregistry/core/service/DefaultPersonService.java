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

import javax.annotation.Resource;
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
@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultPersonService implements PersonService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonRepository personRepository;

    private final ReferenceRepository referenceRepository;

    private final Reconciler reconciler;

    private final IdentifierGenerator identifierGenerator;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private FieldElector<Date> birthDateFieldElector = new DefaultBirthDateFieldElector();

    private FieldElector<String> genderFieldElector = new DefaultGenderFieldElector();

    private FieldElector<SorName> preferredNameFieldElector = new DefaultNameFieldSelector();

    private FieldElector<SorName> officialNameFieldElector = new DefaultNameFieldSelector();

    private FieldElector<EmailAddress> preferredContactEmailAddressFieldElector = new DefaultPreferredEmailContactFieldSelector();

    private FieldElector<Phone> preferredContactPhoneNumberFieldElector = new DefaultPreferredPhoneContactFieldSelector();

    private enum RecalculationType {DELETE, ADD, UPDATE}

    @Resource(name = "personFactory")
    private ObjectFactory<Person> personObjectFactory;

    @Autowired(required = false)
    private Map<ReconciliationCriteria, ReconciliationResult> criteriaCache = new EhCacheBackedMapImpl<ReconciliationCriteria, ReconciliationResult>();

    @Autowired(required = false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Inject
    public DefaultPersonService(final PersonRepository personRepository, final ReferenceRepository referenceRepository, final IdentifierGenerator identifierGenerator, final Reconciler reconciler) {
        this.personRepository = personRepository;
        this.referenceRepository = referenceRepository;
        this.identifierGenerator = identifierGenerator;
        this.reconciler = reconciler;
    }

    public void setPreferredContactEmailAddressFieldElector(final FieldElector<EmailAddress> preferredContactEmailAddressFieldElector) {
        this.preferredContactEmailAddressFieldElector = preferredContactEmailAddressFieldElector;
    }

    public void setPreferredContactPhoneNumberFieldElector(final FieldElector<Phone> preferredContactPhoneNumberFieldElector) {
        this.preferredContactPhoneNumberFieldElector = preferredContactPhoneNumberFieldElector;
    }

    public void setPersonObjectFactory(final ObjectFactory<Person> personObjectFactory) {
        this.personObjectFactory = personObjectFactory;
    }

    public void setCriteriaCache(final Map<ReconciliationCriteria, ReconciliationResult> criteriaCache) {
        this.criteriaCache = criteriaCache;
    }

    public void setIdentifierAssigners(final List<IdentifierAssigner> identifierAssigners) {
        this.identifierAssigners = identifierAssigners;
    }

    public void setValidator(final Validator validator) {
        this.validator = validator;
    }

    public void setBirthDateFieldElector(final FieldElector<Date> birthDateFieldElector) {
        this.birthDateFieldElector = birthDateFieldElector;
    }

    public void setGenderFieldElector(final FieldElector<String> genderFieldElector) {
        this.genderFieldElector = genderFieldElector;
    }

    public void setPreferredNameFieldElector(final FieldElector<SorName> preferredNameFieldElector) {
        this.preferredNameFieldElector = preferredNameFieldElector;
    }

    public void setOfficialNameFieldElector(final FieldElector<SorName> officialNameFieldElector) {
        this.officialNameFieldElector = officialNameFieldElector;
    }

    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }

    public Person findPersonByIdentifier(final String identifierType, final String identifierValue) {
        try {
            return this.personRepository.findByIdentifier(identifierType, identifierValue);
        } catch (final Exception e) {
            return null;
        }
    }

    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSourceIdentifier) {
        try {
            return this.personRepository.findByPersonIdAndSorIdentifier(personId, sorSourceIdentifier);
        } catch (final Exception e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
    }

    public SorPerson findBySorIdentifierAndSource(final String sorSource, final String sorId) {
        try {
            return this.personRepository.findBySorIdentifierAndSource(sorSource, sorId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SorPerson> getSorPersonsFor(final Person person) {
        Assert.notNull(person);
        return this.personRepository.getSoRRecordsForPerson(person);
    }

    @Override
    public List<SorPerson> getSorPersonsFor(final Long personId) {
        return getSorPersonsFor(this.personRepository.findByInternalId(personId));
    }

    /**
     * This does not explicitly delete the names because its assumed the recalculation will clean it up.
     */
    public boolean deleteSystemOfRecordPerson(final SorPerson sorPerson, final boolean mistake, final String terminationTypes) {
        Assert.notNull(sorPerson, "sorPerson cannot be null.");
        final String terminationTypeToUse = terminationTypes != null ? terminationTypes : Type.TerminationTypes.UNSPECIFIED.name();

        final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        Assert.notNull(person, "person cannot be null.");

        if (mistake) {
            for (final SorRole sorRole : sorPerson.getRoles()) {
                for (final Iterator<Role> iter = person.getRoles().iterator(); iter.hasNext();) {
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

        recalculatePersonBiodemInfo(person, sorPerson, RecalculationType.DELETE, mistake);
        return true;
    }

    public boolean deleteSystemOfRecordPerson(final String sorSource, final String sorId, final boolean mistake, final String terminationTypes) {
        Assert.notNull(sorSource, "sorSource cannot be null.");
        Assert.notNull(sorId, "sorId cannot be null.");
        final SorPerson sorPerson = this.personRepository.findBySorIdentifierAndSource(sorSource, sorId);

        return sorPerson != null && deleteSystemOfRecordPerson(sorPerson, mistake, terminationTypes);
    }

    public boolean deleteSystemOfRecordRole(final SorPerson sorPerson, final SorRole sorRole, final boolean mistake, final String terminationTypes) throws IllegalArgumentException {
        Assert.notNull(sorRole, "sorRole cannot be null.");
        Assert.notNull(sorPerson, "soPerson cannot be null.");
        final String terminationTypeToUse = terminationTypes != null ? terminationTypes : Type.TerminationTypes.UNSPECIFIED.name();

        final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        Assert.notNull(person, "person cannot be null.");

        final Role role = person.findRoleBySoRRoleId(sorRole.getId());

        if (mistake) {
            person.getRoles().remove(role);
        } else {
            final Type terminationReason = this.referenceRepository.findType(Type.DataTypes.TERMINATION, terminationTypeToUse);
            if (!role.isTerminated()) {
                role.expireNow(terminationReason, true);
            }
        }

        sorPerson.getRoles().remove(sorRole);
        this.personRepository.saveSorPerson(sorPerson);
        this.personRepository.savePerson(person);
        return true;
    }

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

    public ServiceExecutionResult<Person> addPerson(final ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null");

        if (reconciliationCriteria.getSorPerson().getSorId() != null && this.findBySorIdentifierAndSource(reconciliationCriteria.getSorPerson().getSourceSor(), reconciliationCriteria.getSorPerson().getSorId()) != null) {
            throw new IllegalStateException("CANNOT ADD SAME SOR RECORD.");
        }

        final Set validationErrors = this.validator.validate(reconciliationCriteria);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<Person>(validationErrors);
        }

        final ReconciliationResult result = this.reconciler.reconcile(reconciliationCriteria);

        switch (result.getReconciliationType()) {
            case NONE:
                return new GeneralServiceExecutionResult<Person>(saveSorPersonAndConvertToCalculatedPerson(reconciliationCriteria));

            case EXACT:
                return new GeneralServiceExecutionResult<Person>(addNewSorPersonAndLinkWithMatchedCalculatedPerson(reconciliationCriteria, result));
        }

        this.criteriaCache.put(reconciliationCriteria, result);
        throw new ReconciliationException(result);
    }

    public ServiceExecutionResult<Person> forceAddPerson(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException, IllegalStateException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        final ReconciliationResult result = this.criteriaCache.get(reconciliationCriteria);

        if (result == null) {
            throw new IllegalStateException("No ReconciliationResult found for provided criteria.");
        }

        this.criteriaCache.remove(reconciliationCriteria);

        return new GeneralServiceExecutionResult<Person>(saveSorPersonAndConvertToCalculatedPerson(reconciliationCriteria));
    }

    public ServiceExecutionResult<Person> addPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) throws IllegalArgumentException, IllegalStateException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        Assert.notNull(person, "person cannot be null.");

        final ReconciliationResult result = this.criteriaCache.get(reconciliationCriteria);

        if (result == null) {
            throw new IllegalStateException("No ReconciliationResult found for provided criteria.");
        }

        for (final PersonMatch personMatch : result.getMatches()) {
            if (personMatch.getPerson().getId().equals(person.getId())) {
                addSorPersonAndLink(reconciliationCriteria, person);
                final Person savedPerson = this.personRepository.findByInternalId(person.getId());
                return new GeneralServiceExecutionResult<Person>(savedPerson);
            }
        }

        throw new IllegalStateException("Person not found in ReconciliationResult.");
    }

    public List<PersonMatch> searchForPersonBy(final SearchCriteria searchCriteria) {
        if (StringUtils.hasText(searchCriteria.getIdentifierValue())) {
            final String identifierValue = searchCriteria.getIdentifierValue();
            final List<IdentifierType> identifierTypes = this.referenceRepository.getIdentifierTypes();

            for (final IdentifierType identifierType : identifierTypes) {
                if (identifierType.getFormatAsPattern().matcher(identifierValue).matches()) {
                    final Person person = this.personRepository.findByIdentifier(identifierType.getName(), identifierValue);

                    if (person != null) {
                        return new ArrayList<PersonMatch>(Arrays.asList(new PersonMatchImpl(person, 100, new ArrayList<FieldMatch>())));
                    }
                }
            }

            final List<Person> persons = this.personRepository.findByUnknownIdentifier(identifierValue);
            return createMatches(persons);
        }

        final List<Person> persons = this.personRepository.searchByCriteria(searchCriteria);
        return createMatches(persons);
    }

    /**
     * Persists an SorPerson on update.
     *
     * @param sorPerson the person to update.
     * @return serviceExecutionResult.
     */
    public ServiceExecutionResult<SorPerson> updateSorPerson(final SorPerson sorPerson) {
        final Set validationErrors = this.validator.validate(sorPerson);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<SorPerson>(validationErrors);
        }

        //do reconciliationCheck to make sure that modifications do not cause person to reconcile to a different person
        if (!this.reconciler.reconcilesToSamePerson(sorPerson)) {
            throw new IllegalStateException();
        }

        // Save Sor Person
        final SorPerson savedSorPerson = this.personRepository.saveSorPerson(sorPerson);

        final Person person = this.findPersonById(savedSorPerson.getPersonId());
        Assert.notNull(person, "person cannot be null.");

        recalculatePersonBiodemInfo(person, sorPerson, RecalculationType.UPDATE, false);

        return new GeneralServiceExecutionResult<SorPerson>(savedSorPerson);
    }

    public ServiceExecutionResult<SorRole> updateSorRole(final SorPerson sorPerson, final SorRole sorRole) {
        Assert.notNull(sorPerson, "sorPerson cannot be null.");
        Assert.notNull(sorRole, "sorRole cannot be null.");

        final Set validationErrors = this.validator.validate(sorRole);

        if (!validationErrors.isEmpty()) {
            return new GeneralServiceExecutionResult<SorRole>(validationErrors);
        }

        final SorRole savedSorRole = this.personRepository.saveSorRole(sorRole);

        final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        final Role role = person.findRoleBySoRRoleId(savedSorRole.getId());

        role.recalculate(sorRole);
        this.personRepository.savePerson(person);

        return new GeneralServiceExecutionResult<SorRole>(savedSorRole);
    }

    public boolean removeSorName(SorPerson sorPerson, Long nameId) {
        SorName name = sorPerson.findNameByNameId(nameId);
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
     * @param toPerson   person receiving sor records.
     * @return Result of move. Validation errors if they occurred or the Person receiving sor records.
     */
    public boolean moveAllSystemOfRecordPerson(Person fromPerson, Person toPerson) {
        // get the list of sor person records that will be moving.
        List<SorPerson> sorPersonList = personRepository.getSoRRecordsForPerson(fromPerson);

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
     * @param toPerson   person receiving sor record.
     * @return Success or failure.
     */
    public boolean moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson movingSorPerson) {
        movingSorPerson.setPersonId(toPerson.getId());
        updateCalculatedPersonsOnMoveOfSor(toPerson, fromPerson, movingSorPerson);
        this.personRepository.saveSorPerson(movingSorPerson);
        return true;
    }

    /**
     * Move one Sor Record from one person to another where the to person is not in the registry
     *
     * @param fromPerson      person losing sor record.
     * @param movingSorPerson record that is moving.
     * @return Success or failure.
     */
    public boolean moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson movingSorPerson) {
        // create the new person in the registry
        // Person toPerson = constructPersonFromSorData(movingSorPerson);
        // TODO broke this in order to clean everything else up
        return moveSystemOfRecordPerson(fromPerson, this.personObjectFactory.getObject(), movingSorPerson);
    }

    /**
     * Update the calculated person data. This method and updateCalculatedPersonOnDeleteOfSor
     * need to be generalized to handle recalculations.
     *
     * @param toPerson
     * @param fromPerson
     * @param sorPerson  Adjust calculated roles...
     *                   Point prc_role to prc_person receiving role
     *                   Add the role to the set of roles in receiving prc_person
     *                   Remove role from prc person losing role
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
        for (final Role role : rolesToDelete) {
            fromPerson.getRoles().remove(role);
        }

        // TODO recalculate names for person receiving role? Anything else?
        // TODO recalculate names for person losing role? Anything else?
        this.personRepository.savePerson(fromPerson);
        this.personRepository.savePerson(toPerson);
    }

    public boolean expireRole(SorRole role) {
        role.setEnd(new Date());
        return true;
    }

    public boolean renewRole(SorRole role) {
        final Calendar cal = Calendar.getInstance();

        //TODO need to read configuration data for setting the default renewal date for this role type.
        //hard code to 6 months for now.
        cal.add(Calendar.MONTH, 6);
        role.setEnd(cal.getTime());
        return true;
    }

    protected List<PersonMatch> createMatches(final List<Person> people) {
        final List<PersonMatch> personMatches = new ArrayList<PersonMatch>();
        for (final Person person : people) {
            final PersonMatch p = new PersonMatchImpl(person, 50, new ArrayList<FieldMatch>());
            personMatches.add(p);
        }

        return personMatches;
    }

    protected Person recalculatePersonBiodemInfo(final Person person, final SorPerson sorPerson, final RecalculationType recalculationType, boolean mistake) {
        final List<SorPerson> sorPersons = this.personRepository.getSoRRecordsForPerson(person);

        if (recalculationType == RecalculationType.ADD || (recalculationType == RecalculationType.DELETE && !mistake)) {
            sorPersons.add(sorPerson);
        }

        copySorNamesToPerson(person, sorPersons);

        final Date birthDate = this.birthDateFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        final String gender = this.genderFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        final SorName preferredName = this.preferredNameFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        final SorName officialName = this.officialNameFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        final EmailAddress emailAddress = this.preferredContactEmailAddressFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        final Phone phone = this.preferredContactPhoneNumberFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);

        person.setDateOfBirth(birthDate);
        person.setGender(gender);
        person.getPreferredContactEmailAddress().update(emailAddress);
        person.getPreferredContactPhoneNumber().update(phone);

        boolean preferred = false;
        boolean official = false;

        for (final Name name : person.getNames()) {
            if (!preferred && name.sameAs(preferredName)) {
                name.setPreferredName(true);
                preferred = true;
            }

            if (!official && name.sameAs(officialName)) {
                name.setOfficialName(true);
                official = true;
            }

            if (official && preferred) {
                break;
            }
        }

        return this.personRepository.savePerson(person);
    }

    /**
     * Copy SorNames to Calculated Person
     *
     * @param person
     * @param sorPersons
     */
    protected void copySorNamesToPerson(final Person person, final List<SorPerson> sorPersons) {
        person.getNames().clear();
        
        for (final SorPerson sorPerson : sorPersons) {
            for (final SorName sorName : sorPerson.getNames()) {
                boolean alreadyAdded = false;

                for (final Name calculatedName : person.getNames()) {
                    if (calculatedName.sameAs(sorName)) {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded) {
                    person.addName(sorName);
                }
            }
        }
    }

    /**
     * Current workflow for converting an SorPerson into the actual Person.
     *
     * @param reconciliationCriteria the original search criteria.
     * @return the newly saved Person.
     */
    protected Person saveSorPersonAndConvertToCalculatedPerson(final ReconciliationCriteria reconciliationCriteria) {
        if (!StringUtils.hasText(reconciliationCriteria.getSorPerson().getSorId())) {
            reconciliationCriteria.getSorPerson().setSorId(this.identifierGenerator.generateNextString());
        }

        // Save Sor Person
        final SorPerson sorPerson = this.personRepository.saveSorPerson(reconciliationCriteria.getSorPerson());
        final Person savedPerson = recalculatePersonBiodemInfo(this.personObjectFactory.getObject(), sorPerson, RecalculationType.ADD, false);

        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, savedPerson);
        }

        final Person newPerson = this.personRepository.savePerson(savedPerson);

        // Now connect the SorPerson to the actual person
        sorPerson.setPersonId(newPerson.getId());
        this.personRepository.saveSorPerson(sorPerson);

        return newPerson;
    }

    protected Person addSorPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) {
        final SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        final SorPerson registrySorPerson = this.findByPersonIdAndSorIdentifier(person.getId(), sorPerson.getSourceSor());

        if (registrySorPerson != null) {
            throw new IllegalStateException("Oops! An error occurred. A person already exists from this SoR linked to this ID!");
        }

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }

        sorPerson.setPersonId(person.getId());
        final SorPerson savedSorPerson = this.personRepository.saveSorPerson(sorPerson);
        return recalculatePersonBiodemInfo(person, savedSorPerson, RecalculationType.UPDATE, false);
    }

    protected Person addNewSorPersonAndLinkWithMatchedCalculatedPerson(final ReconciliationCriteria reconciliationCriteria, final ReconciliationResult result) {
        Assert.isTrue(result.getMatches().size() == 1, "ReconciliationResult should be 'EXACT' and there should only be one person.  The result is '" + result.getReconciliationType() + "' and the number of people is " + result.getMatches().size() + ".");

        final Person person = result.getMatches().iterator().next().getPerson();
        return addSorPersonAndLink(reconciliationCriteria, person);
    }


}
