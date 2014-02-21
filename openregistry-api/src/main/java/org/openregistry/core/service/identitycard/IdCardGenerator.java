package org.openregistry.core.service.identitycard;

import org.openregistry.core.domain.Person;

/**
 *  Responsiblity of this class is to generate identity card object
 *  This interface is used when id card generated inside OR, and may not be dependant on any thing from external sor
 * @Author Muhammad Siddique
 */
public interface IdCardGenerator {

    /**
     * this method will generates a new @IdCard and will attach to this person
     * this method
     * @param person
     */

    void addIDCard(Person person);

}
