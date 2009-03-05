package org.openregistry.core.web.propertyeditors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Campus;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Feb 20, 2009
 * Time: 9:16:56 AM
 * To change this template use File | Settings | File Templates.
 */
public final class CampusEditor extends AbstractReferenceRepositoryPropertyEditor {

    public CampusEditor(final ReferenceRepository referenceRepository){
        super(referenceRepository);
    }

    public String getAsText(){
        Campus campus = (Campus)getValue();
        return campus != null ? String.valueOf(campus.getId()) : " ";
    }

    @Override
    public void setAsText(final String text) {
        if (StringUtils.hasText(text)){
            setValue(getReferenceRepository().getCampusById(new Long(text)));
        } else{
            setValue(null);
        }
    }

}
