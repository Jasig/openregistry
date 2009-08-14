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

    private PersonRepository personRepository=null;

    public SponsorConverter(PersonRepository personRepository) {
       super(Person.class);
       this.personRepository = personRepository;
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