package org.openregistry.core.service;

import org.openregistry.core.domain.*;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Default implementation of the {@link org.openregistry.core.service.ActivationService}.
 *
 * @author Nancy Mond
 * @since 1.0.0
 */
@Service("activationService")
public class DefaultActivationService implements ActivationService {

    @Autowired(required = false)
    private AnnotationValidator<Object> annotationValidator = new AnnotationValidatorImpl(JvConfiguration.JV_CONFIG_FILE_FIELD);

    @Autowired(required=true)
    private PersonRepository personRepository;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ActivationKey generateActivationKey(Person person) {
        //is this enough or do we need to do entity.remove?
        person.removeCurrentActivationKey();
        ActivationKey key = person.generateNewActivationKey(this.getActivationKeyEndDate().getTime());
        return key;
    }

    public ActivationKey generateActivationKey(String identifierType, String identifierValue) throws PersonNotFoundException, IllegalArgumentException  {
        if (identifierType == null || identifierValue ==null) throw new IllegalArgumentException();
        try {
            Person person = findPerson(identifierType, identifierValue);
            return this.generateActivationKey(person);
        } catch (PersonNotFoundException e){
            throw e;
        }
    }

    public void invalidateActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        ActivationKey currentKey = person.getCurrentActivationKey();
        if (currentKey == null || !currentKey.getId().equals(activationKey)) throw new IllegalArgumentException();
        if (!currentKey.isValid()) throw new IllegalStateException();
        person.removeCurrentActivationKey();
    }

    public void invalidateActivationKey(String identifierType, String identifierValue, String activationKey) throws PersonNotFoundException, IllegalArgumentException, IllegalStateException {
        if (identifierType == null || identifierValue == null) throw new IllegalArgumentException();
        Person person = null;
        try {
            person = findPerson(identifierType, identifierValue);
            this.invalidateActivationKey(person, activationKey);
        } catch (PersonNotFoundException e){
            throw e;
        } catch (IllegalArgumentException e){
            throw e;
        } catch (IllegalStateException e){
            throw e;
        }
    }

    public ActivationKey getActivationKey(String identifierType, String identifierValue, String activationKey) throws PersonNotFoundException, IllegalArgumentException {
        if (identifierType == null || identifierValue ==null) throw new IllegalArgumentException();
        try {
            Person person = findPerson(identifierType, identifierValue);
            return this.getActivationKey(person, activationKey);
        } catch (PersonNotFoundException e){
            throw e;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    public ActivationKey getActivationKey(Person person, String activationKey) throws PersonNotFoundException, IllegalArgumentException {
        if (person == null) throw new PersonNotFoundException();
        ActivationKey key = person.getCurrentActivationKey();
        if (key == null || !key.getId().equals(activationKey)) throw new IllegalArgumentException();
        return key;
    }

    protected Calendar getActivationKeyEndDate(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,48);
        return cal;
    }

    protected Person findPerson(String identifierType, String identifierValue) throws PersonNotFoundException {
        Person person = null;
        try {
            person = personRepository.findByIdentifier(identifierType, identifierValue);
            return person;
        } catch (Exception e){
            throw new PersonNotFoundException();
        }
    }
}