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

import org.apache.commons.lang.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.DisclosureSettings.PropertyNames;
import org.openregistry.core.domain.jpa.JpaNameImpl;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.identifier.*;
import org.openregistry.core.service.identitycard.IdCardGenerator;
import org.openregistry.core.service.reconciliation.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.*;
import org.springframework.util.StringUtils;

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

    private final DisclosureRecalculationStrategyRepository strategyRepository;

    private final Reconciler reconciler;

    private final IdentifierGenerator identifierGenerator;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Resource(name="sorRoleElector")
    private SorRoleElector sorRoleElector  = new DefaultSorRoleElector();

    @Resource(name="birthDateFieldElector")
    private FieldElector<Date> birthDateFieldElector = new DefaultBirthDateFieldElector();

    @Resource(name="genderFieldElector")
    private FieldElector<String> genderFieldElector = new DefaultGenderFieldElector();

    @Resource(name="preferredNameFieldElector")
    private FieldElector<SorName> preferredNameFieldElector = new DefaultNameFieldSelector();

    @Resource(name="officialNameFieldElector")
    private FieldElector<SorName> officialNameFieldElector = new DefaultNameFieldSelector();

    @Resource(name="personAttributesElector")
    private FieldElector<Map<String,String>> attributesElector = new DefaultAttributesElector();

    private FieldElector<EmailAddress> preferredContactEmailAddressFieldElector = new DefaultPreferredEmailContactFieldSelector();

    private FieldElector<Phone> preferredContactPhoneNumberFieldElector = new DefaultPreferredPhoneContactFieldSelector();

        @Resource(name="disclosureFieldElector")
        private FieldElector<SorDisclosureSettings> disclosureFieldElector = new DefaultDisclosureSettingsFieldElector();

    @Resource(name="ssnFieldElector")
    private FieldElector<String> ssnFieldElector = new DefaultSSNFieldElector();

    private enum RecalculationType {DELETE, ADD, UPDATE}

    @Resource(name = "personFactory")
    private ObjectFactory<Person> personObjectFactory;

    @Autowired(required = false)
    private Map<ReconciliationCriteria, ReconciliationResult> criteriaCache = new EhCacheBackedMapImpl<ReconciliationCriteria, ReconciliationResult>();

    @Autowired(required = false)
    private List<IdentifierAssigner> identifierAssigners = new ArrayList<IdentifierAssigner>();

    @Inject
    private IdentifierChangeService identifierChangeService;

    @Resource (name="idCardGenerator")
    private IdCardGenerator idCardGenerator;

    @Inject
    public DefaultPersonService
    (final PersonRepository personRepository, final ReferenceRepository referenceRepository,
    		final DisclosureRecalculationStrategyRepository strategyRepository,
    		final IdentifierGenerator identifierGenerator, final Reconciler reconciler) {
        this.personRepository = personRepository;
        this.referenceRepository = referenceRepository;
        this.strategyRepository = strategyRepository;
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
    @Transactional(readOnly = true)
    public Person findPersonById(final Long id) {
        return this.personRepository.findByInternalId(id);
    }
    @Transactional(readOnly = true)
    public Person fetchCompleteCalculatedPerson(Long id){
        return this.personRepository.fetchCompleteCalculatedPerson(id);
    }
    @Transactional(readOnly = true)
    public Person findPersonByIdentifier(final String identifierType, final String identifierValue) {
        try {
            return this.personRepository.findByIdentifier(identifierType, identifierValue);
        } catch (final Exception e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSourceIdentifier) {
        try {
            return this.personRepository.findByPersonIdAndSorIdentifier(personId, sorSourceIdentifier);
        } catch (final Exception e) {
            //logger.debug(e.getMessage(), e);
            logger.debug(e.getMessage());
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
    @Transactional(readOnly = true)
    public SorPerson findByIdentifierAndSource(String identifierType, String identifierValue, String sorSource) {
        Person p = this.findPersonByIdentifier(identifierType, identifierValue);
        if(p == null) {
            return null;
        }
        List<SorPerson> sorPeople = this.getSorPersonsFor(p);
        for(SorPerson sorPerson : sorPeople) {
            if(sorSource.equals(sorPerson.getSourceSor())) {
                return sorPerson;
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
   public List<SorPerson> findByIdentifier(String identifierType, String identifierValue) {
       //TODO-Verify if this is the correct behavior
       Person p = this.findPersonByIdentifier(identifierType, identifierValue);
       if(p == null) {
           return null;
       }
       List<SorPerson> sorPeople = this.getSorPersonsFor(p);
       return sorPeople;
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
            Set<Role> rolesToDelete = new HashSet<Role>();

            for (final SorRole sorRole : sorPerson.getRoles()) {
                for (final Role role: person.getRoles()) {
                    if (sorRole.getId().equals(role.getSorRoleId())) {
                        rolesToDelete.add(role);
                    }
                }
            }

            for (final Role role: rolesToDelete) {
                //let sorRoleElector delete the role and add another role if required
                sorRoleElector.removeCalculatedRole(person,role,this.personRepository.getSoRRecordsForPerson(person));
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

       Person p= recalculatePersonBiodemInfo(person, sorPerson, RecalculationType.DELETE, mistake);
        this.personRepository.savePerson(p);
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
        //delete and expire role only if it exist at calculated level
        if(role!=null)
        if (mistake) {


            //let SorRoleElector remove the role
            sorRoleElector.removeCalculatedRole(person,role,this.personRepository.getSoRRecordsForPerson(person));

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

    @PreAuthorize("hasPermission(#sorRole, 'admin')")
    public ServiceExecutionResult<SorRole> validateAndSaveRoleForSorPerson(final SorPerson sorPerson, final SorRole sorRole) {
    	logger.info(" validateAndSaveRoleForSorPerson start");
    	 Assert.notNull(sorPerson, "SorPerson cannot be null.");
        Assert.notNull(sorRole, "SorRole cannot be null.");

        // check if the SoR Role has an ID assigned to it already and assign source sor
        setRoleIdAndSource(sorRole, sorPerson.getSourceSor());

        final Set validationErrors = this.validator.validate(sorRole);

        if (!validationErrors.isEmpty()) {
            //since because of existing design we cannot raise exception, we can only rollback the transaction through code
            //OR-384
           if( TransactionAspectSupport.currentTransactionStatus()!=null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            return new GeneralServiceExecutionResult<SorRole>(validationErrors);
        }

        final SorPerson newSorPerson = this.personRepository.saveSorPerson(sorPerson);
        Person person = this.personRepository.findByInternalId(newSorPerson.getPersonId());
        final SorRole newSorRole = newSorPerson.findSorRoleBySorRoleId(sorRole.getSorId());
        //let sor role elector decide if this new role can be converted to calculated one
        sorRoleElector.addSorRole(newSorRole,person);
        person = recalculatePersonBiodemInfo(person, newSorPerson, RecalculationType.UPDATE, false);
        this.personRepository.savePerson(person);
        logger.info("validateAndSaveRoleForSorPerson end");
        return new GeneralServiceExecutionResult<SorRole>(newSorRole);
    }

    public ServiceExecutionResult<Person> validateAndSavePersonAndRole(final ReconciliationCriteria reconciliationCriteria)   {
    	logger.info(" validateAndSavePersonAndRole start");
        SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        if (sorPerson == null || sorPerson.getRoles() == null)
            throw new IllegalArgumentException("Sor Person not found in provided criteria.");
        SorRole sorRole =  reconciliationCriteria.getSorPerson().getRoles().get(0);
        if  (sorRole == null)
             throw new IllegalArgumentException("Sor Role not found for provided criteria.");

        setRoleIdAndSource(sorRole, sorPerson.getSourceSor());

        final Set validationErrors = this.validator.validate(sorRole);
        
        if (!validationErrors.isEmpty()) {
            //since because of existing design we cannot raise exception, we can only rollback the transaction through code
            //OR-384
            if( TransactionAspectSupport.currentTransactionStatus()!=null){
                 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             }

            return new GeneralServiceExecutionResult<Person>(validationErrors);
        }

        Long personId = sorPerson.getPersonId();
        if (personId == null) {
        	logger.info("calling saveSorPersonAndConvertToCalculatedPerson");
        	   // add new Sor Person and roles, create calculated person
                return new GeneralServiceExecutionResult<Person>(saveSorPersonAndConvertToCalculatedPerson(reconciliationCriteria));
        } else { // Add new Sor Person and role and link to the existing person
                    Person thisPerson = this.personRepository.findByInternalId(personId);
                    try {
                    	logger.info("calling addSorPersonAndLink");
                    	  Person savedPerson = this.addSorPersonAndLink(reconciliationCriteria, thisPerson);
                         return new GeneralServiceExecutionResult<Person>(savedPerson);
                    } catch (SorPersonAlreadyExistsException sorE) {
                         throw new IllegalArgumentException("If a sor Person of the same source already exists, should call the other method to add the role only");
                    }
        }
        
       
    }

    public ServiceExecutionResult<Person> addPerson(final ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException, SorPersonAlreadyExistsException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null");
        logger.info("addPerson start");
    if (reconciliationCriteria.getSorPerson().getSorId() != null && this.findBySorIdentifierAndSource(reconciliationCriteria.getSorPerson().getSourceSor(), reconciliationCriteria.getSorPerson().getSorId()) != null) {
        //throw new IllegalStateException("CANNOT ADD SAME SOR RECORD.");
        throw new SorPersonAlreadyExistsException(this.findBySorIdentifierAndSource(reconciliationCriteria.getSorPerson().getSourceSor(), reconciliationCriteria.getSorPerson().getSorId()));
    }

    final Set validationErrors = this.validator.validate(reconciliationCriteria);

    if (!validationErrors.isEmpty()) {
        Iterator iter = validationErrors.iterator();
        while (iter.hasNext()) {
            logger.info("validation errors: " + iter.next());
        }
        //since because of existing design we cannot raise exception, we can only rollback the transaction through code
        //OR-384
        if( TransactionAspectSupport.currentTransactionStatus()!=null){
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
         }


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
    logger.info("addPerson start");
    throw new ReconciliationException(result);
}

    public ServiceExecutionResult<Person> forceAddPerson(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException, IllegalStateException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        logger.info("forceAddPerson start");
        final ReconciliationResult result = this.criteriaCache.get(reconciliationCriteria);

        if (result == null) {
            throw new IllegalStateException("No ReconciliationResult found for provided criteria.");
        }

        this.criteriaCache.remove(reconciliationCriteria);
        logger.info("forceAddPerson end");
        return new GeneralServiceExecutionResult<Person>(saveSorPersonAndConvertToCalculatedPerson(reconciliationCriteria));
    }

    public ServiceExecutionResult<Person> addPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) throws IllegalArgumentException, IllegalStateException, SorPersonAlreadyExistsException {
        Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null.");
        Assert.notNull(person, "person cannot be null.");
        logger.info(" addPersonAndLink start");
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
        logger.info("addPersonAndLink end");
        throw new IllegalStateException("Person not found in ReconciliationResult.");
    }

    public ServiceExecutionResult<ReconciliationResult> reconcile(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException {
         Assert.notNull(reconciliationCriteria, "reconciliationCriteria cannot be null");
         logger.info("reconcile start");
         final Set validationErrors = this.validator.validate(reconciliationCriteria);

         if (!validationErrors.isEmpty()) {
             Iterator iter = validationErrors.iterator();
             while (iter.hasNext()) {
                 logger.info("validation errors: " + iter.next());
             }
             logger.info("reconcile start");
             //since because of existing design we cannot raise exception, we can only rollback the transaction through code
             //OR-384
             if( TransactionAspectSupport.currentTransactionStatus()!=null){
                  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
              }


             return new GeneralServiceExecutionResult<ReconciliationResult>(validationErrors);
         }

         final ReconciliationResult result = this.reconciler.reconcile(reconciliationCriteria);
         //(reconciliationCriteria, result);
         return new GeneralServiceExecutionResult<ReconciliationResult>(result);
     }

    @PostFilter("hasPermission(filterObject, 'read')")
    public List<PersonMatch> searchForPersonBy(final SearchCriteria searchCriteria) {

        if (StringUtils.hasText(searchCriteria.getIdentifierValue())) {
            final String identifierValue = searchCriteria.getIdentifierValue();
                    final Person person = this.findPersonByIdentifier(searchCriteria.getIdentifierType().getName(), identifierValue);
                    if (person != null)
                        return new ArrayList<PersonMatch>(Arrays.asList(new PersonMatchImpl(person, 100, new ArrayList<FieldMatch>())));
                    else  return new ArrayList<PersonMatch>();
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
            Iterator iter = validationErrors.iterator();
            while (iter.hasNext()) {
                logger.info("validation errors: " + iter.next());
            }
            //since because of existing design we cannot raise exception, we can only rollback the transaction through code
            //OR-384
            if( TransactionAspectSupport.currentTransactionStatus()!=null){
                 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             }


            return new GeneralServiceExecutionResult<SorPerson>(validationErrors);
        }

        //do reconciliationCheck to make sure that modifications do not cause person to reconcile to a different person
        if (!this.reconciler.reconcilesToSamePerson(sorPerson)) {
            throw new IllegalStateException();
        }

        // Iterate over any sorRoles setting sorid and source id if not specified by SoR.
        for (final SorRole sorRole : sorPerson.getRoles()){
            setRoleIdAndSource(sorRole, sorPerson.getSourceSor());
        }
        
        // Save Sor Person
        final SorPerson savedSorPerson = this.personRepository.saveSorPerson(sorPerson);

        Person person = this.findPersonById(savedSorPerson.getPersonId());

        Assert.notNull(person, "person cannot be null.");

        logger.info("Verifying Number of calculated Roles before the calculate: "+ person.getRoles().size());
       
        // Iterate over sorRoles. SorRoles may be new or updated.
        for (final SorRole savedSorRole:savedSorPerson.getRoles()){
            logger.info("DefaultPersonService: savedSorPersonRole Found, savedSorRoleID: "+ savedSorRole.getId());
            logger.info("DefaultPersonService: savedSorPersonRole Found, Role Must be newly added.");
            //let sor role elector decide if this new role can be converted to calculated one
            sorRoleElector.addSorRole(savedSorRole,person);
            logger.info("Verifying Number of calculated Roles after calculate: "+ person.getRoles().size());
        }
        
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, person);
        }

        person = recalculatePersonBiodemInfo(person, savedSorPerson, RecalculationType.UPDATE, false);
        person =this.personRepository.savePerson(person);

        return new GeneralServiceExecutionResult<SorPerson>(savedSorPerson);
    }

    public ServiceExecutionResult<SorRole> updateSorRole(final SorPerson sorPerson, final SorRole sorRole) {
        Assert.notNull(sorPerson, "sorPerson cannot be null.");
        Assert.notNull(sorRole, "sorRole cannot be null.");

        final Set validationErrors = this.validator.validate(sorRole);

        if (!validationErrors.isEmpty()) {
            //since because of existing design we cannot raise exception, we can only rollback the transaction through code
            //OR-384
            if( TransactionAspectSupport.currentTransactionStatus()!=null){
                 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             }


            return new GeneralServiceExecutionResult<SorRole>(validationErrors);
        }

        final SorRole savedSorRole = this.personRepository.saveSorRole(sorRole);

        final Person person = this.personRepository.findByInternalId(sorPerson.getPersonId());
        final Role role = person.findRoleBySoRRoleId(savedSorRole.getId());
        if(role!=null){
           //update calculated role only if that role was previously converted to calculated one by sorRoleElector
          role.recalculate(savedSorRole);
          this.personRepository.savePerson(person);
        }
        //else //do nothing i.e. don't update the calculated role if SorRoleElector Previously decided not to convert this sor role to calculated role

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

         Set <? extends Identifier> oldIdentifiers= fromPerson.getIdentifiers();
        Set<? extends IdCard> oldIdCards =fromPerson.getIdCards();

        this.personRepository.deletePerson(fromPerson);
        logger.info("moveAllSystemOfRecordPerson: Deleted From Person");
        for(Identifier identifier:oldIdentifiers){

            if( toPerson.getIdentifiersByType().get(identifier.getType().getName())==null ){
               Identifier oldIdentifierAttachedTotoPerson= toPerson.addIdentifier(identifier.getType(),identifier.getValue());
            ///if type of this identifier don't exist then add this identifier as primary and not deleted

                oldIdentifierAttachedTotoPerson.setDeleted(false);
                oldIdentifierAttachedTotoPerson.setPrimary(true);
                }
                //and if this exist then add this identifier as deleted and no primary
            else{
                Identifier oldIdentifierAttachedTotoPerson= toPerson.addIdentifier(identifier.getType(),identifier.getValue());
                ///if type of this identifier don't exist then add this identifier as primary and not deleted

                oldIdentifierAttachedTotoPerson.setDeleted(true);
                oldIdentifierAttachedTotoPerson.setPrimary(false);;

                }

        }
        for(IdCard oldIdCard:oldIdCards){
            if(toPerson.getPrimaryIdCard()==null){
                toPerson.addIDCard(oldIdCard);
            }
            else{
               if( oldIdCard.isPrimary())
                   oldIdCard.setPrimary(false);
                toPerson.addIDCard(oldIdCard);
            }
        }


        this.personRepository.savePerson(toPerson);

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
        this.personRepository.savePerson(fromPerson);
        this.personRepository.savePerson(toPerson);
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
         Person savedPerson = recalculatePersonBiodemInfo(this.personObjectFactory.getObject(), movingSorPerson, RecalculationType.ADD, false);
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(movingSorPerson , savedPerson);
        }
        //remove identifiers assigned to new person from old person
        fromPerson.getIdentifiers().removeAll(savedPerson.getIdentifiers());
        updateCalculatedPersonsOnMoveOfSor(savedPerson, fromPerson, movingSorPerson);
        fromPerson =this.personRepository.savePerson(fromPerson);
       savedPerson= this.personRepository.savePerson(savedPerson);
        movingSorPerson.setPersonId(savedPerson.getId());
        this.personRepository.saveSorPerson(movingSorPerson);
        return true;


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
                    sorRoleElector.addSorRole(sorRole,toPerson );
                    rolesToDelete.add(role);
                }
            }
        }
        for (final Role role : rolesToDelete) {
            sorRoleElector.removeCalculatedRole(fromPerson, role, this.personRepository.getSoRRecordsForPerson(fromPerson));
            fromPerson.getRoles().remove(role);
        }

        // TODO recalculate names for person receiving role? Anything else?
        // TODO recalculate names for person losing role? Anything else?
//        this.personRepository.savePerson(fromPerson);
//        this.personRepository.savePerson(toPerson);
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
        logger.info("recalculatePersonBiodemInfo: start");
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
        final Map<String,String> attributes =this.attributesElector.elect(sorPerson,sorPersons,recalculationType == RecalculationType.DELETE);
        final SorDisclosureSettings disclosure = this.disclosureFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
     
        final String  ssn = this.ssnFieldElector.elect(sorPerson, sorPersons, recalculationType == RecalculationType.DELETE);
        Identifier primarySSN=person.getPrimaryIdentifiersByType().get("SSN");
        //check if the elector elcted some ssn and person does have previous ssn assigned to it
        if(!org.apache.commons.lang.StringUtils.isEmpty(ssn) && primarySSN!=null ){
            try{
              this.identifierChangeService.change(person.getPrimaryIdentifiersByType().get("SSN"),ssn);
            }catch (IllegalArgumentException e){
                logger.debug(e.getStackTrace().toString());
            }//all other exception should be propogated

        }
        
        person.setDateOfBirth(birthDate);
        person.setGender(gender);
        person.getPreferredContactEmailAddress().update(emailAddress);
        person.getPreferredContactPhoneNumber().update(phone);
        person.calculateDisclosureSettings(disclosure);
        person.setAttributes(attributes);

        
        String affiliation = "";
        Type affiliationType=null;
        if(disclosure!=null){
        	logger.info("after person.calculateDisclosureSettings, disclosure code : " + disclosure.getDisclosureCode());
        }else{
        	logger.info("Disclosure is null");
        }
       List<SorRole> sorroles = sorPerson.getRoles();
        for(SorRole role:sorroles){
        	if(role!=null){
        		logger.info("Role = " + role.getTitle());
        		if(role.getAffiliationType()!=null){
        			logger.info("Role desc= " + role.getAffiliationType().getDescription());
        			affiliation=role.getAffiliationType().getDescription();
            		
            		if  (person.getDisclosureSettings() != null) {
                    	logger.info("recalculating disclosure setting 1...");
                    	//person.getDisclosureSettings().recalculate(this.strategyRepository.getDisclosureRecalculationStrategy());
                    	person.getDisclosureSettings().recalculate(this.strategyRepository.getDisclosureRecalculationStrategy(),affiliation,referenceRepository);
                    }
            	}
        	}
        }
       
    
        //SSN election is happening in the ssn identifier assigner.
       
        boolean preferred = false;
        boolean official = false;

        // exclude Chosen name and Directory Listing Name from being Official and Preferred.
        for (final Name name : person.getNames()) {
            if (!preferred && name.sameAs(preferredName) && (name.getType().getDescription().equalsIgnoreCase("LEGAL")
                    || name.getType().getDescription().equalsIgnoreCase("FORMAL"))) {
                name.setPreferredName(true);
                preferred = true;
            }

            if (!official && name.sameAs(officialName) && (name.getType().getDescription().equalsIgnoreCase("LEGAL")
                    || name.getType().getDescription().equalsIgnoreCase("FORMAL"))) {
                name.setOfficialName(true);
                official = true;
            }

            if (official && preferred) {
                break;
            }
        }
        logger.info("recalculatePersonBiodemInfo: end");
//        return this.personRepository.savePerson(person);
          return person;
    }

    /**
     * Copy SorNames to Calculated Person
     *
     * @param person
     * @param sorPersons
     */
    protected void copySorNamesToPerson(final Person person, final List<SorPerson> sorPersons) {

        //person.getNames().clear();

        Set<? extends Name> personNames = person.getNames();
        boolean person_has_chosen_name = false;
        for (Iterator<? extends Name> iterator = personNames.iterator(); iterator.hasNext();) {
            Name name =  iterator.next();
            if (!name.getType().getDescription().equals(Type.NameTypes.CHOSEN.name())) {
                iterator.remove();
            } else {
                person_has_chosen_name = true;
            }
        }


        for (final SorPerson sorPerson : sorPersons) {
            for (final SorName sorName : sorPerson.getNames()) {
                boolean alreadyAdded = false;

                for (final Name calculatedName : person.getNames()) {
                    if (calculatedName.sameAs(sorName)
                            && calculatedName.getType().getDescription().equals(
                            sorName.getType().getDescription()))
                    {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded &&
                        (!sorName.getType().getDescription().equals(Type.NameTypes.CHOSEN.name()) ||
                                !person_has_chosen_name)
                ) {
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

        logger.info("Executing new code!!!!!!");

        // Iterate over any sorRoles setting sorid and source id
        for (final SorRole sorRole : reconciliationCriteria.getSorPerson().getRoles()){
            setRoleIdAndSource(sorRole, reconciliationCriteria.getSorPerson().getSourceSor());
        }

        logger.info("Creating sorPerson: person_id: "+ reconciliationCriteria.getSorPerson().getPersonId());
        // Save Sor Person
        final SorPerson sorPerson = this.personRepository.saveSorPerson(reconciliationCriteria.getSorPerson());
         Person savedPerson = recalculatePersonBiodemInfo(this.personObjectFactory.getObject(), sorPerson, RecalculationType.ADD, false);
        savedPerson =this.personRepository.savePerson(savedPerson);

        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, savedPerson);
        }
        idCardGenerator.addIDCard(savedPerson);

        // Create calculated roles.
        for (final SorRole newSorRole : sorPerson.getRoles()){
            //let sor role elector decide if this new role can be converted to calculated one
            sorRoleElector.addSorRole(newSorRole,savedPerson);
        }

        final Person newPerson = this.personRepository.savePerson(savedPerson);

        logger.info("Verifying Number of calculated Roles: "+ newPerson.getRoles().size());

        // Now connect the SorPerson to the actual person
        sorPerson.setPersonId(newPerson.getId());
        this.personRepository.saveSorPerson(sorPerson);
        logger.info("Created sorPerson: person_id: "+ sorPerson.getPersonId());

        return newPerson;
    }

    protected Person addSorPersonAndLink(final ReconciliationCriteria reconciliationCriteria, final Person person) throws SorPersonAlreadyExistsException{
        final SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        final SorPerson registrySorPerson = this.findByPersonIdAndSorIdentifier(person.getId(), sorPerson.getSourceSor());

        if (registrySorPerson != null) {
            //throw new IllegalStateException("Oops! An error occurred. A person already exists from this SoR linked to this ID!");
            throw new SorPersonAlreadyExistsException(registrySorPerson);
        }

        if (!StringUtils.hasText(sorPerson.getSorId())) {
            sorPerson.setSorId(this.identifierGenerator.generateNextString());
        }

        // Iterate over any sorRoles setting sorid and source id
        for (final SorRole sorRole : sorPerson.getRoles()){
            setRoleIdAndSource(sorRole, sorPerson.getSourceSor());
        }

        sorPerson.setPersonId(person.getId());
        final SorPerson savedSorPerson = this.personRepository.saveSorPerson(sorPerson);

        // Create calculated roles.
        for (final SorRole newSorRole : savedSorPerson.getRoles()){
            //let sor role elector decide if this new role can be converted to calculated one
           sorRoleElector.addSorRole(newSorRole,person);

        }

        //loop through all identifiers to see if addition of this new sor person will have a new identifier created at calculated level
        for (final IdentifierAssigner ia : this.identifierAssigners) {
            ia.addIdentifierTo(sorPerson, person);
        }
        Person p =  recalculatePersonBiodemInfo(person, savedSorPerson, RecalculationType.UPDATE, false);
        return this.personRepository.savePerson(p);
    }

    protected Person addNewSorPersonAndLinkWithMatchedCalculatedPerson(final ReconciliationCriteria reconciliationCriteria, final ReconciliationResult result) throws SorPersonAlreadyExistsException{
        Assert.isTrue(result.getMatches().size() == 1, "ReconciliationResult should be 'EXACT' and there should only be one person.  The result is '" + result.getReconciliationType() + "' and the number of people is " + result.getMatches().size() + ".");

        final Person person = result.getMatches().iterator().next().getPerson();
        return addSorPersonAndLink(reconciliationCriteria, person);
    }

    protected void setRoleIdAndSource(SorRole sorRole, String sorSource){
        if (!StringUtils.hasText(sorRole.getSorId())) {
                sorRole.setSorId(this.identifierGenerator.generateNextString());
        }

        if (sorRole.getSystemOfRecord() == null) {
            sorRole.setSystemOfRecord(referenceRepository.findSystemOfRecord(sorSource));
        }
    }

    /**
     * Expose person repository to subclasses
     * @return
     */
    protected PersonRepository getPersonRepository() {
    	return this.personRepository;
    }

    /**
     * Expose disclosure recalculation strategy to sublclasses
     * @return
     */
    protected DisclosureRecalculationStrategyRepository getDisclosureRecalculationStrategyRepository() {
    	return this.strategyRepository;
    }

    /**
     * Expose reference repository to subclasses
     * @return
     */
    protected ReferenceRepository getReferenceRepository() {
    	return this.referenceRepository;
    }

    public void setIdCardGenerator(IdCardGenerator idCardGenerator) {
        this.idCardGenerator = idCardGenerator;
    }


    /**
     * add or update preferred name
     *
     * @param person the Person to update.
     * @param sorPerson the SorPerson to update.
     * @param chosenName the preferred name to add or update.
     * @return Result of updating.
     */
    public boolean addOrUpdateChosenName(Person person, SorPerson sorPerson,
                                            String chosenName){

        logger.info("Preferred Name: " + chosenName);
        // Update SorPerson
        boolean sorHasChosenName = false;
        SorName newChosenSorName = null;
        String lastName = null;
        String middleName = null;
        for (SorName sorName : sorPerson.getNames()) {
            if (sorName.getType().getDescription().equals(Type.NameTypes.CHOSEN.name())) {
                sorHasChosenName = true;
                // update
                sorName.setGiven(chosenName);
                newChosenSorName = sorName;
                logger.info("The SOR person already has Chosen name: update it");
            } else {
                if (sorName.getType().getDescription().equals(Type.NameTypes.FORMAL.name())
                        || sorName.getType().getDescription().equals(Type.NameTypes.LEGAL.name())) {
                    lastName = sorName.getFamily();
                    middleName = sorName.getMiddle();
                }
            }
        }

        logger.info("Found formal/legal lastName: " + lastName + " middle name: " + middleName);
        if (!sorHasChosenName) {
            // add new Chosen name to the SorPerson
            logger.info("The SOR person does not have Chosen name: add it");
            newChosenSorName = sorPerson.addName(referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.CHOSEN));
            newChosenSorName.setGiven(chosenName);
            newChosenSorName.setMiddle(middleName);
            newChosenSorName.setFamily(lastName);
        }

        try {
            this.personRepository.saveSorPerson(sorPerson);
            logger.info("Sor person is saved");
        } catch (Exception e) {
            logger.error("Error in saving the sor person for the Chosen name");
            return false;
        }

        newChosenSorName = null;
        // get the newly saved sor chosen name
        logger.info("to get the saved new sor chosen name");
        for (SorName sorName: sorPerson.getNames()) {
            if (sorName.getType().getDescription().equalsIgnoreCase(Type.NameTypes.CHOSEN.name())) {
                newChosenSorName = sorName;
                break;
            }
        }

        if (newChosenSorName == null) {
            logger.error("Something is wrong. The sor Chosen name has not been saved successfully");
            return false;
        }

        // Update Person
        JpaNameImpl existingChosenName = (JpaNameImpl)person.getChosenName();
        if (existingChosenName != null) { // update
            logger.info("The Person already has Chosen name: update it");
            existingChosenName.setFamily(newChosenSorName.getFamily());
            existingChosenName.setGiven(newChosenSorName.getGiven());
            existingChosenName.setMiddle(newChosenSorName.getMiddle());
            existingChosenName.setSourceNameId(newChosenSorName.getId());
        } else { // add new
            logger.info("The Person does not have Chosen name: Add it");
            person.addName(newChosenSorName);
        }

        try {
            this.personRepository.savePerson(person);
            logger.info("sor person is saved");
        } catch (Exception e) {
            logger.error("Error in saving the Person for the chosen name");
            return false;
        }
        return true;
    }

}
