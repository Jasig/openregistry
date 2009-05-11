package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.RoleInfo;
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
public class RoleInfoConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ReferenceRepository referenceRepository=null;

    public RoleInfoConverter(ReferenceRepository referenceRepository) {
       super(RoleInfo.class);
       this.referenceRepository = referenceRepository;
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();

        RoleInfo roleInfo = referenceRepository.getRoleInfoById(new Long(string));
        logger.info("RoleInfoConverter: trying to convert to object: roleInfoID: "+ roleInfo.getId());
        return roleInfo;
    }

   @Override
   protected String toString(Object object) throws Exception {
       RoleInfo roleInfo = (RoleInfo) object;
       logger.info("RoleInfoConverter: converting to string: roleInfoID: "+ roleInfo.getId());
       return roleInfo != null ? String.valueOf(roleInfo.getId()) : " ";
   }

}