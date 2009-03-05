package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.jpa.JpaCountryImpl;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Department;
import org.openregistry.core.domain.Region;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Jan 30, 2009
 * Time: 10:34:12 AM
 * To change this template use File | Settings | File Templates.
 */
public final class RegionEditor extends AbstractReferenceRepositoryPropertyEditor {

    public RegionEditor(ReferenceRepository referenceRepository){
        super(referenceRepository);     
    }

    public String getAsText() {
        final Region region = (Region) getValue();
        return region != null ? region.getName() : " ";
    }

    @Override
    public void setAsText(final String text) {
        final String trimmedText = text.trim();
        setValue(null);
        if (!StringUtils.hasText(trimmedText)) {
            return;
        }

        for (final Region region : getReferenceRepository().getRegions()) {
            if (region.getCode().trim().equals(trimmedText) || region.getName().trim().equals(trimmedText)){
                setValue(region);
                break;
            }
        }
    }
}
