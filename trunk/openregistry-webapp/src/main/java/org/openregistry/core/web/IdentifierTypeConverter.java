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
import org.openregistry.core.domain.*;
import org.openregistry.core.repository.ReferenceRepository;

import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 28, 2009
 * Time: 3:33:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdentifierTypeConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ReferenceRepository referenceRepository=null;

    public IdentifierTypeConverter(ReferenceRepository referenceRepository) {
       super(IdentifierType.class);
       this.referenceRepository = referenceRepository;
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();

        IdentifierType type = referenceRepository.findIdentifierType(string);
        return type;
    }

   @Override
   protected String toString(Object object) throws Exception {
       IdentifierType type = (IdentifierType) object;
       return type != null ? type.getName() : " ";
   }

}