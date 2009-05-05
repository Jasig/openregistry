package org.openregistry.core.web;

import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
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
public class CountryConverter extends StringToObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ReferenceRepository referenceRepository=null;

    public CountryConverter(ReferenceRepository referenceRepository) {
       super(Country.class);
       this.referenceRepository = referenceRepository;
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();

        for (final Region region : referenceRepository.getRegions()) {
            if (region.getCode().trim().equals(trimmedText) || region.getName().trim().equals(trimmedText)){
                return region;
            }
        }
        Country country = referenceRepository.getCountryById(new Long(string));
        return null;
    }

   @Override
   protected String toString(Object object) throws Exception {
       Country country = (Country) object;
       logger.info("CountryConverter: converting to string"+ country.getName());
       return country != null ? country.getName() : " ";
   }

}