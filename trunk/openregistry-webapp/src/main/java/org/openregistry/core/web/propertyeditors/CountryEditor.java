package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.domain.Country;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Converts the numerical identifier for a country into the country object.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class CountryEditor extends AbstractReferenceRepositoryPropertyEditor {

    public CountryEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        final Country country = (Country) getValue();
        return country != null ? country.getName() : " ";
    }

    protected void setAsTextInternal(final String s) {
        setValue(getReferenceRepository().getCountryById(new Long(s)));
    }
}
