package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.Region;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Converts a region id into the appropriate region.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class RegionEditor extends AbstractReferenceRepositoryPropertyEditor {

    public RegionEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);     
    }

    public String getAsText() {
        final Region region = (Region) getValue();
        return region != null ? region.getName() : " ";
    }

    protected void setAsTextInternal(final String s) {
        final String trimmedText = s.trim();
        for (final Region region : getReferenceRepository().getRegions()) {
            if (region.getCode().trim().equals(trimmedText) || region.getName().trim().equals(trimmedText)){
                setValue(region);
                break;
            }
        }
    }
}
