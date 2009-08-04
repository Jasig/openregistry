package org.openregistry.core.web;

import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.support.MessageSourceAccessor;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.domain.*;
import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.PersonService;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.aspect.OpenRegistryMessageSourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: May 4, 2009
 * Time: 10:52:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PersonAction {

    @Autowired(required=true)
    private PersonService personService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final MessageSourceAccessor msa = OpenRegistryMessageSourceAccessor.getMessageSourceAccessor();

    public String moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson sorPerson) {
        if (personService.findByPersonIdAndSorIdentifier(toPerson.getId(), sorPerson.getSourceSorIdentifier()) != null){
            return msa.getMessage("matchingSorFound");
        }
        if (personService.moveSystemOfRecordPerson(fromPerson, toPerson, sorPerson)){
            return msa.getMessage("splitSuccess");
        } else {
            return msa.getMessage("splitFailure");
        }
    }

    public String moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson sorPerson) {
        if (personService.moveSystemOfRecordPersonToNewPerson(fromPerson, sorPerson)){
            return msa.getMessage("splitSuccess");
        } else {
            return msa.getMessage("splitFailure");
        }
    }

}