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
public class TypeConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ReferenceRepository referenceRepository=null;

    public TypeConverter(ReferenceRepository referenceRepository) {
       super(Type.class);
       this.referenceRepository = referenceRepository;
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();

        Type type = referenceRepository.getTypesById(new Long(string));
        logger.info("TypeConverter: trying to convert to object: typeID: "+ type.getId());
        return type;
    }

   @Override
   protected String toString(Object object) throws Exception {
       Type type = (Type) object;
       logger.info("TypeConverter: converting to string: typeID: "+ type.getId());
       return type != null ? String.valueOf(type.getId()) : " ";
   }

}