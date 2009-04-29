package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.Person;
import org.openregistry.core.repository.PersonRepository;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Apr 28, 2009
 * Time: 3:33:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class SponsorConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private PersonRepository personRepository;

    public SponsorConverter() {
       super(Person.class);
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
       return personRepository.findByInternalId(new Long(string));
    }

   @Override
   protected String toString(Object object) throws Exception {
       Person person = (Person) object;
       return toString(person.getId());
   }

}