package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Region;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Created by IntelliJ IDEA.
 * User: nmond
 * Date: Apr 28, 2009
 * Time: 3:33:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegionConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ReferenceRepository referenceRepository;

    public RegionConverter() {
       super(Region.class);
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();

        for (final Region region : referenceRepository.getRegions()) {
            if (region.getCode().trim().equals(trimmedText) || region.getName().trim().equals(trimmedText)){
                return region;
            }
        }
        return null;
    }

   @Override
   protected String toString(Object object) throws Exception {
       Region region = (Region) object;
       return region.getName();
   }

}