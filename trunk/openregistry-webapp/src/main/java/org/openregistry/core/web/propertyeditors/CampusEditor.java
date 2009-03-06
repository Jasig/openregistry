package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Campus;

/**
 * Converts the numerical identifier for a campus into the actual campus object.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
public final class CampusEditor extends AbstractReferenceRepositoryPropertyEditor {

    public CampusEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        Campus campus = (Campus)getValue();
        return campus != null ? String.valueOf(campus.getId()) : " ";
    }

    protected void setAsTextInternal(final String s) {
        setValue(getReferenceRepository().getCampusById(new Long(s)));
    }

}
