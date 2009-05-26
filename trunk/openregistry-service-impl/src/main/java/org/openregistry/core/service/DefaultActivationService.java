package org.openregistry.core.service;

import org.openregistry.core.domain.*;
import org.openregistry.core.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.javalid.core.AnnotationValidator;
import org.javalid.core.ValidationMessage;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final String NETID="NETID";

    public ServiceExecutionResult verifyActivationKey(final String type, final String identifierValue, final String activationKey){
        final String serviceName = "ActivationService.verifyActivationKey";

        final List<ValidationError> validationErrors= new ArrayList<ValidationError>();

        logger.info("DefaultActivationService1: verifyActivationKey: type: "+ type + "value: " + identifierValue + "activationKey: "+activationKey);

        //check if netid belongs to a person in the registry
        Person person = null;
        try {
            person = personRepository.findByIdentifier(type, identifierValue.trim());
        } catch (Exception ex) {
            ex.printStackTrace();
            validationErrors.add(new ORValidationError(type ,null, "netIDActivationError"));
            return new GeneralServiceExecutionResult(serviceName, null, validationErrors);
        }

        if (person == null){
            validationErrors.add(new ORValidationError(type, null, "netIDNotValid"));
            return new GeneralServiceExecutionResult(serviceName, null, validationErrors);
        }

        //check if netid already activated.
        //TODO need to support more than one NETID per person
        Identifier personIdentifier = person.pickOutIdentifier(type);
        if (personIdentifier.getActivationKey().getActivitationDate() != null){
            validationErrors.add(new ORValidationError(type, null, "netIDAlreadyActivated"));
            return new GeneralServiceExecutionResult(serviceName, null, validationErrors);
        }

        //check if activation key expired.
        if (personIdentifier.getActivationKey().getExpirationDate().before(new Date())){
            validationErrors.add(new ORValidationError(type, null, "activationKeyExpired"));
            return new GeneralServiceExecutionResult(serviceName, null, validationErrors);
        }

        //check if activation key matches activation key entered.
        if (!personIdentifier.getActivationKey().getValue().trim().equals(activationKey.trim())){
            validationErrors.add(new ORValidationError(type, null, "activationKeyNoMatch"));
            return new GeneralServiceExecutionResult(serviceName, null, validationErrors);
        }

        return new GeneralServiceExecutionResult(serviceName, null);
    }

    @Transactional
    public ServiceExecutionResult activateNetID(String type, Identifier identifier, String password){
         final String serviceName = "ActivationService.activateNetID";

        logger.info("DefaultActivationService: activateNetID: netid: " + identifier.getValue() + " password: " + password);
        // set date activated.
        Person person = personRepository.findByIdentifier(type, identifier.getValue());

        //TODO support more than one netid for a person.
        Identifier personIdentifier = person.pickOutIdentifier(NETID);
        personIdentifier.getActivationKey().setActivationDate(new Date());
        personRepository.savePerson(person);

        //TODO send netid and password to Kerberos.

        return new GeneralServiceExecutionResult(serviceName, identifier);
    }

}