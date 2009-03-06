package org.openregistry.core.web.propertyeditors;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Person;

/**
 * Identifies the sponsor based on his or her identifier.
 *
 * @author Nancy Mond
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SponsorEditor extends AbstractReferenceRepositoryPropertyEditor {

    public SponsorEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        final Person person = (Person) getValue();
        return person != null ? String.valueOf(person.getId()) : " ";
    }

    protected void setAsTextInternal(final String s) {
        setValue(getReferenceRepository().getPersonById(new Long(s)));
    }
}
