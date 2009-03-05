package org.openregistry.core.web.propertyeditors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Iterator;
import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 5, 2009
 * Time: 9:39:54 AM
 * To change this template use File | Settings | File Templates.
 */
public final class SponsorEditor extends AbstractReferenceRepositoryPropertyEditor {

    public SponsorEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        final Person person = (Person) getValue();
        return person != null ? String.valueOf(person.getId()) : " ";
    }

    @Override 
    public void setAsText(final String text) {
        if (StringUtils.hasText(text)){
            setValue(getReferenceRepository().getPersonById(new Long(text)));
        } else{
            setValue(null);
        }
    }
}
