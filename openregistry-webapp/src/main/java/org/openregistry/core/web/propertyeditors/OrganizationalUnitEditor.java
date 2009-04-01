package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.OrganizationalUnit;

/**
 * Converts the numerical identifier for a Organizational Unit into the appropriate object.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class OrganizationalUnitEditor extends AbstractReferenceRepositoryPropertyEditor {

    public OrganizationalUnitEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        OrganizationalUnit organizationalUnit = (OrganizationalUnit) getValue();
        return organizationalUnit != null ? organizationalUnit.getName() : " ";
    }

    protected void setAsTextInternal(final String s) {
        setValue(getReferenceRepository().getOrganizationalUnitById(new Long(s)));
    }
}
